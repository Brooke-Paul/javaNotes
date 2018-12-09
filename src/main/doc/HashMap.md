---
title: HashMap的源码分析
date: 2018-09-13 07:01:34 
categories:  
- JAVA 集合  
tags:
- HashMap 
comments: true 
---

 ## HashMap 简介
 
`HashMap` 是一个散列表， 它存储的内容是键值对`（key -  value）`映射。   
`HashMap` 继承于 `AbstractMap`， 实现了`Map`， `Cloneable`，`Serializable` 接口。  
`HashMap` 不是线程安全的， 不适用于多线程中， 此外，`HashMap`中的映射不是有序的。  
`HashMap` 里面是一个数组，然后数组中每个元素是一个单项链表。  
图中`Entry` 包含四个属性，`key`，`value`，`hash` 值和用于单项链表的`next`。  

重要参数`capacity` 和 `loadFactor`。      
`capacity` 当前数组容量，始终保持 2^n，默认值为 `1<<4 `，最大为 `1<<16`, 可以扩容，扩容后数组大小为当前的 2 倍。  
`loadFactor` 负载因子，默认为 0.75。  
`threshold` 扩容的阈值，等于 `capacity * loadFactor`。
```java
    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;
    static final int MAXIMUM_CAPACITY = 1 << 30;
    static final float DEFAULT_LOAD_FACTOR = 0.75f;
    
```
`HashMap` 的 `put` 方法：

```java
 public V put(K key, V value) {
        return putVal(hash(key), key, value, false, true);
}
//第三个参数 onlyIfAbsent 如果是 true，那么只有在不存在该 key 时才会进行 put 操作
final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
            boolean evict) {
     Node<K,V>[] tab; Node<K,V> p; int n, i;
     // 第一次 resize 和后续的扩容有些不一样，因为这次是数组从 null 初始化到默认的 16 或自定义的初始容量
     if ((tab = table) == null || (n = tab.length) == 0)
         n = (tab = resize()).length;
     if ((p = tab[i = (n - 1) & hash]) == null) 
         tab[i] = newNode(hash, key, value, null);    // 找到具体的数组下标，如果此位置没有值，那么直接初始化一下 Node 并放置在这个位置
     else {
         Node<K,V> e; K k;
         if (p.hash == hash &&
             ((k = p.key) == key || (key != null && key.equals(k))))
             e = p; //首先，判断该位置的第一个数据和我们要插入的数据，key 是不是"相等"，如果是，取出这个节点
         else if (p instanceof TreeNode)
             e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value); // 如果该节点是代表红黑树的节点，调用红黑树的插值方法
         else {
              // 到这里，说明数组该位置上是一个链表
             for (int binCount = 0; ; ++binCount) {
                 if ((e = p.next) == null) {
                     p.next = newNode(hash, key, value, null);
                      // TREEIFY_THRESHOLD 为 8，所以，如果新插入的值是链表中的第 9 个
                      // 会触发下面的 treeifyBin，也就是将链表转换为红黑树
                     if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
                         treeifyBin(tab, hash);
                     break;
                 }
                 if (e.hash == hash &&
                     ((k = e.key) == key || (key != null && key.equals(k))))
                     break;
                 p = e;
             }
         }
          // e!=null 说明存在旧值的key与要插入的key"相等"
                 // 对于我们分析的put操作，下面这个 if 其实就是进行 "值覆盖"，然后返回旧值
         if (e != null) { // existing mapping for key
             V oldValue = e.value;
             if (!onlyIfAbsent || oldValue == null)
                 e.value = value;
             afterNodeAccess(e);
             return oldValue;
         }
     }
     ++modCount;
     // 如果 HashMap 由于新插入这个值导致 size 已经超过了阈值，需要进行扩容
     if (++size > threshold)
         resize();
     afterNodeInsertion(evict);
     return null;
}
接下来看 hashmap 的 resize 方法
final Node<K,V>[] resize() {
    Node<K,V>[] oldTab = table;
    int oldCap = (oldTab == null) ? 0 : oldTab.length;
    int oldThr = threshold;
    int newCap, newThr = 0;
    if (oldCap > 0) {
        if (oldCap >= MAXIMUM_CAPACITY) {
            threshold = Integer.MAX_VALUE;
            return oldTab;
        }
        else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY &&
                 oldCap >= DEFAULT_INITIAL_CAPACITY)
            newThr = oldThr << 1; // double threshold
    }
    else if (oldThr > 0) // 对应使用 new HashMap(int initialCapacity) 初始化后，第一次 put 的时候
        newCap = oldThr;
    else {              // 对应使用 new HashMap() 初始化后，第一次 put 的时候
        newCap = DEFAULT_INITIAL_CAPACITY;
        newThr = (int)(DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);
    }
    // 用新的数组大小初始化新的数组
    if (newThr == 0) {
        float ft = (float)newCap * loadFactor;
        newThr = (newCap < MAXIMUM_CAPACITY && ft < (float)MAXIMUM_CAPACITY ?
                  (int)ft : Integer.MAX_VALUE);
    }
    threshold = newThr;
    @SuppressWarnings({"rawtypes","unchecked"})
        Node<K,V>[] newTab = (Node<K,V>[])new Node[newCap];
    table = newTab; // 如果是初始化数组，到这里就结束了，返回 newTab 即可
    if (oldTab != null) {
        for (int j = 0; j < oldCap; ++j) {
            Node<K,V> e;
            if ((e = oldTab[j]) != null) {
                oldTab[j] = null;
                if (e.next == null)
                    newTab[e.hash & (newCap - 1)] = e;
                else if (e instanceof TreeNode)
                    ((TreeNode<K,V>)e).split(this, newTab, j, oldCap);
                else { // preserve order
                    Node<K,V> loHead = null, loTail = null;
                    Node<K,V> hiHead = null, hiTail = null;
                    Node<K,V> next;
                    do {
                        next = e.next;
                        if ((e.hash & oldCap) == 0) {
                            if (loTail == null)
                                loHead = e;
                            else
                                loTail.next = e;
                            loTail = e;
                        }
                        else {
                            if (hiTail == null)
                                hiHead = e;
                            else
                                hiTail.next = e;
                            hiTail = e;
                        }
                    } while ((e = next) != null);
                    if (loTail != null) {
                        loTail.next = null;
                        newTab[j] = loHead;
                    }
                    if (hiTail != null) {
                        hiTail.next = null;
                        newTab[j + oldCap] = hiHead;
                    }
                }
            }
        }
    }
    return newTab;
}
```
`HashMap` 的 `get`方法
1.计算   `key` 的 `hash` 值，根据 `hash` 值找到对应数组下标: `hash & (length-1) ` 
2.判断数组该位置处的元素是否刚好就是我们要找的，如果不是，走第三步  
3.判断该元素类型是否是 `TreeNode`，如果是，用红黑树的方法取数据，如果不是，走第四步  
4.遍历链表，直到找到相等`(==或equals)的 key`  

```java
public V get(Object key) {
        Node<K,V> e;
        return (e = getNode(hash(key), key)) == null ? null : e.value;
}

final Node<K,V> getNode(int hash, Object key) {
    Node<K,V>[] tab; Node<K,V> first, e; int n; K k;
    if ((tab = table) != null && (n = tab.length) > 0 &&
        (first = tab[(n - 1) & hash]) != null) { 1
        if (first.hash == hash && ((k = first.key) == key || (key != null && key.equals(k)))) 2
            return first;
        if ((e = first.next) != null) {
            if (first instanceof TreeNode) 3
                return ((TreeNode<K,V>)first).getTreeNode(hash, key);
            do { 4
                if (e.hash == hash &&
                    ((k = e.key) == key || (key != null && key.equals(k))))
                    return e;
            } while ((e = e.next) != null);
        }
    }
    return null;
}


```
  


