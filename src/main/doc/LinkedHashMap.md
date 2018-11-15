---
title: LinkedHashMap的源码分析
date: 2018-09-15 07:01:34  
comments: true 
categories:  
- java  
tags:
- LinkedHashMap  
---

 ## LinkedHashMap 简介
 `LinkedHashMap` 继承了 `HashMap`, 实现了`map`接口。    
 所以 `LinkedHashMap` 包含了 `HashMap` 的所有功能， 它是一个关联数组，线程不安全，并且`key`值，`value`值允许为空。    
`LinkedHashMap` 与 `HashMap` 不同之处是它支持写入顺序排序，访问顺序排序。  
 
 ### 顺序展示效果示例代码
 ```java
public static void main(String[] args) {
    Map map = new LinkedHashMap();
    map.put("1", 1);
    map.put("2", 2);
    map.put("3", 3);
    map.put("4", 4);
    map.put("5", 5);
    Iterator iterator = map.entrySet().iterator();
    while (iterator.hasNext()) {
        Map.Entry entry = (Map.Entry) iterator.next();
        System.out.println(entry.getKey() + "::" + entry.getValue());
    }
    System.out.println("accessOrder  设置为 true");
    Map mapOrder = new LinkedHashMap(15, 0.75f, true);
    mapOrder.put("1", 1);
    mapOrder.put("2", 2);
    mapOrder.put("3", 3);
    mapOrder.put("4", 4);
    mapOrder.put("5", 5);
    mapOrder.get("1");
    Iterator iterator1 = mapOrder.entrySet().iterator();
    while (iterator1.hasNext()) {
        Map.Entry entry = (Map.Entry) iterator1.next();
        System.out.println(entry.getKey() + "::" + entry.getValue());
    }
}
返回的结果如下：  
写入顺序排序
1::1
2::2
3::3
4::4
5::5
accessOrder  设置为 true
访问顺序排序
2::2
3::3
4::4
5::5
1::1
```
带着疑问剖析源码，`LinkedHashMap`的构造函数有五种：  
```java
    //默认是false，则迭代时输出的顺序是插入节点的顺序。若为true，则输出的顺序是按照访问节点的顺序。
    final boolean accessOrder;
    //指定初始化时的容量，和扩容的加载因子
    public LinkedHashMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
        accessOrder = false;
    }
    //指定初始化时的容量
    public LinkedHashMap(int initialCapacity) {
        super(initialCapacity);
        accessOrder = false;
    }

    public LinkedHashMap() {
        super();
        accessOrder = false;
    }
    //利用另一个Map 来构建
    public LinkedHashMap(Map<? extends K, ? extends V> m) {
        super();
        accessOrder = false;
        putMapEntries(m, false);
    }
    //指定初始化时的容量，和扩容的加载因子，以及迭代输出节点的顺序
    public LinkedHashMap(int initialCapacity,
                         float loadFactor,
                         boolean accessOrder) {
        super(initialCapacity, loadFactor);
        this.accessOrder = accessOrder;
    }
```
构造函数和`HashMap`相比，就是增加了一个`accessOrder`参数。用于控制迭代时的节点顺序。    

###  LinkedHashMap的添加
在`LinkedHashMap`中没有重写`HashMap`的`put`方法，但是重写了`HashMap`中的`newNode` 方法，  
在每次构建新节点时，通过`linkNodeLast(p)`，将新节点链接在内部双向链表的尾部。  
源码如下：    
```java
Node<K,V> newNode(int hash, K key, V value, Node<K,V> e) {
    //在构建新节点时，构建的是`LinkedHashMap.Entry` 不再是`Node`.
    LinkedHashMap.Entry<K,V> p =
        new LinkedHashMap.Entry<K,V>(hash, key, value, e);
    linkNodeLast(p);
    return p;
}
//每次插入数据是插入到尾节点，并且把方插入的节点的before设置为last
private void linkNodeLast(LinkedHashMap.Entry<K,V> p) {
    LinkedHashMap.Entry<K,V> last = tail;
    tail = p;
    if (last == null)
        head = p;
    else {
        p.before = last;
        last.after = p;
    }
}
```

###  LinkedHashMap的删除
`LinkedHashMap`也没有重写`remove()`方法，因为它的删除逻辑和`HashMap`并无区别。   
重点在于`LinkedHashMap` 重写了 `afterNodeRemoval`方法。  
```java
void afterNodeRemoval(Node<K,V> e) { // unlink
        LinkedHashMap.Entry<K,V> p =
            (LinkedHashMap.Entry<K,V>)e, b = p.before, a = p.after;
        p.before = p.after = null;//待删除节点 p 的前置后置节点都置空
        if (b == null)
            head = a;
        else
            b.after = a; //重新关联b的after节点
        if (a == null)
            tail = b;
        else
            a.before = b; //重新关联a的before节点
    }
```
###  LinkedHashMap的查找
`LinkedHashMap` 重写了 `get()` 和 `getOrDefault()` 方法。      
对比`HashMap`的实现可以发现只是增加了在成员变量(构造函数时赋值)accessOrder = true 的情况下会调用 `afterNodeAccess()` 方法。    
```java
public V get(Object key) {
    Node<K,V> e;
    if ((e = getNode(hash(key), key)) == null)
        return null;
    if (accessOrder)
        afterNodeAccess(e);
    return e.value;
}
public V getOrDefault(Object key, V defaultValue) {
   Node<K,V> e;
   if ((e = getNode(hash(key), key)) == null)
       return defaultValue;
   if (accessOrder)
       afterNodeAccess(e);
   return e.value;
}
//此方法的目的是将不是尾部的元素并且accessOrder = true的节点e移动至内部的双向链表的尾部
void afterNodeAccess(Node<K,V> e) { // move node to last
    LinkedHashMap.Entry<K,V> last;
    if (accessOrder && (last = tail) != e) {
        LinkedHashMap.Entry<K,V> p =
            (LinkedHashMap.Entry<K,V>)e, b = p.before, a = p.after;
        p.after = null; //将p的设置为尾节点
        if (b == null) // 如果p的前置节点为null，则p以前是头节点，所以更新现在的头节点为p的后置节点a
            head = a;
        else //否则更新b的后置节点为a
            b.after = a;
        if (a != null) // 如果p的后置节点不为null， 则更新a的前置节点为b
            a.before = b;
        else  // 如果p的后置节点为null，则p就是尾节点，更新last 引用为b
            last = b;
        if (last == null) //如果尾节点为空，则链表中只有一个节点
            head = p;
        else {  // 否则更新更新p的前置节点为原尾节点last，last的后置节点为p
            p.before = last;
            last.after = p;
        }
        tail = p;//尾节点的引用赋值成p
        ++modCount;
    }
}
```



