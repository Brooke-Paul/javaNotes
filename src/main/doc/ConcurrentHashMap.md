---
title: ConcurrentHashMap的源码分析
date: 2018-09-12 07:01:34 
comments: true  
tags:
- ConcurrentHashMap
categories:  
- JAVA 集合  
---

 ## ConcurrentHashMap 简介
 正是由于`HashMap` 不是线程安全的，所以大佬给我们带来了 线程安全的`ConcurrentHashMap`。  
 本文的分析的源码是JDK8的版本，与JDK7的版本有很大的差异，java7中 `ConcurrentHashMap` 由 `Segment` 数组、`HashEntry` 组成，和 `HashMap` 一样，仍然是数组加链表。
 它摒弃了`Segment`（锁段）的概念，而是启用了一种全新的方式实现,利用`CAS`算法。它沿用了与它同时期的`HashMap`版本的思想，底层依然由“数组”+链表+红黑树的方式思想，
 但是为了做到并发，又增加了很多辅助的类，例如`TreeBin`，`Traverser`等对象内部类。
```java
hashmap 的 Node 类

static class Node<K,V> implements Map.Entry<K,V> {
        final int hash;
        final K key;
        V value;
        Node<K,V> next;

        Node(int hash, K key, V value, Node<K,V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }
}
concurrentHashMap 的 Node 类

static class Node<K,V> implements Map.Entry<K,V> {
        final int hash;
        final K key;
        volatile V val;
        volatile Node<K,V> next;

        Node(int hash, K key, V val, Node<K,V> next) {
            this.hash = hash;
            this.key = key;
            this.val = val;
            this.next = next;
        }
}
```
其中的`concurrentHashMap`的 `val next` 都用了 `volatile` 修饰，保证了元素可见性。 

###  concurrentHashMap的添加方法

```java
final V putVal(K key, V value, boolean onlyIfAbsent) {
    if (key == null || value == null) throw new NullPointerException();
    int hash = spread(key.hashCode());  (1). 根据 key 计算出 hashcode 。
    int binCount = 0;
    for (Node<K,V>[] tab = table;;) {
        Node<K,V> f; int n, i, fh;
        if (tab == null || (n = tab.length) == 0) (2). 判断是否需要进行初始化。
            tab = initTable();
        else if ((f = tabAt(tab, i = (n - 1) & hash)) == null) { (3). 根据key的hash值和容器的容量减一后进行与运算定位。如果为空就尝试使用cas插入数据
            if (casTabAt(tab, i, null,
                         new Node<K,V>(hash, key, value, null)))
                break;                   // no lock when adding to empty bin
        }
        else if ((fh = f.hash) == MOVED) (4). 如果当前位置的 hashcode == MOVED == -1,则需要进行扩容。
            tab = helpTransfer(tab, f); 
        else {
            V oldVal = null;
            synchronized (f) { (5). 如果都不满足，则利用 synchronized 锁写入数据。
                if (tabAt(tab, i) == f) {
                    if (fh >= 0) { //链表节点
                        binCount = 1;
                        for (Node<K,V> e = f;; ++binCount) {
                            K ek;
                            if (e.hash == hash &&
                                ((ek = e.key) == key ||
                                 (ek != null && key.equals(ek)))) {
                                oldVal = e.val;
                                if (!onlyIfAbsent)
                                    e.val = value;
                                break;
                            }
                            Node<K,V> pred = e;
                            if ((e = e.next) == null) {
                                pred.next = new Node<K,V>(hash, key,
                                                          value, null);
                                break;
                            }
                        }
                    }
                    else if (f instanceof TreeBin) { //树节点，与HashMap不同的是，它并没有把TreeNode直接放入红黑树，而是利用了TreeBin这个小容器来封装所有的TreeNode.
                        Node<K,V> p;
                        binCount = 2;
                        if ((p = ((TreeBin<K,V>)f).putTreeVal(hash, key,
                                                       value)) != null) {
                            oldVal = p.val;
                            if (!onlyIfAbsent)
                                p.val = value;
                        }
                    }
                }
            }
            if (binCount != 0) {
                if (binCount >= TREEIFY_THRESHOLD)
                    treeifyBin(tab, i);  (6). 如果数量大于 TREEIFY_THRESHOLD 则要转换为红黑树。
                if (oldVal != null)
                    return oldVal;
                break;
            }
        }
    }
    addCount(1L, binCount);
    return null;
}

```

`concurrentHashMap` 定义了三个原子操作，用于对指定位置的节点进行操作。正是这些原子操作保证了`ConcurrentHashMap`的线程安全。  

```java

@SuppressWarnings("unchecked")
此函数返回table数组中下标为i的结点，可以看到是通过Unsafe对象通过反射获取的，getObjectVolatile的第二项参数为下标为i的偏移地址。
static final <K,V> Node<K,V> tabAt(Node<K,V>[] tab, int i) {
    return (Node<K,V>)U.getObjectVolatile(tab, ((long)i << ASHIFT) + ABASE);
}

此函数用于比较tab数组下标为i的结点是否为c，若为c，则用v交换操作。否则，不进行交换操作。
static final <K,V> boolean casTabAt(Node<K,V>[] tab, int i,
                                    Node<K,V> c, Node<K,V> v) {
    return U.compareAndSwapObject(tab, ((long)i << ASHIFT) + ABASE, c, v);
}
利用volatile方法设置节点位置的值
static final <K,V> void setTabAt(Node<K,V>[] tab, int i, Node<K,V> v) {
    U.putObjectVolatile(tab, ((long)i << ASHIFT) + ABASE, v);
}

```
###  concurrentHashMap的获取方法

```java
public V get(Object key) {
        Node<K,V>[] tab; Node<K,V> e, p; int n, eh; K ek;
        int h = spread(key.hashCode());
        if ((tab = table) != null && (n = tab.length) > 0 &&
            (e = tabAt(tab, (n - 1) & h)) != null) { (1). 根据计算出来的 hashcode 寻址，如果就在桶上那么直接返回值。
            if ((eh = e.hash) == h) {
                if ((ek = e.key) == key || (ek != null && key.equals(ek)))
                    return e.val;
            }
            else if (eh < 0) (2). 如果是红黑树那就按照树的方式获取值。
                return (p = e.find(h, key)) != null ? p.val : null;
            while ((e = e.next) != null) { (3). 都不满足那就按照链表的方式遍历获取值。
                if (e.hash == h &&
                    ((ek = e.key) == key || (ek != null && key.equals(ek))))
                    return e.val;
            }
        }
        return null;
    }
```


 
