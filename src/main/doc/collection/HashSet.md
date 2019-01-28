---
title: HashSet的源码分析
date: 2018-09-14 07:01:34 
comments: true  
categories:  
- JAVA 集合  
tags:
- HashSet  
---
## HashSet 简介
`HashSet` 是一个没有重复元素的集合。  
它是由 `HashMap` 实现的， 无序且允许空值。  
`HashSet` 是非线程同步的，没有提供数据访问保护，在多线程环境中容易导致多个线程更改数据后造成数据脏读。  

`HashSet` 的添加方法
```java
//map是一个HashMap<E, Object>对象，HashSet是由一个HashMap实例支持的
private transient HashMap<E,Object> map;
//PRESENT是一个static final Object对象，用来作为HashMap中的value值。
private static final Object PRESENT = new Object();
//熟悉hashmap的实现方式的话很容易理解put方法，对象作为key值，不允许重复，value使用 PRESENT。   
public boolean add(E e) {
    return map.put(e, PRESENT)==null;
}
```
`HashSet` 的查找方法
```java
//如果此set包含指定元素，则返回 true
public boolean contains(Object o) {
    return map.containsKey(o);
}
```
`HashSet` 的清空与移除方法

```java
//如果指定元素存在于此set中，则将其移除
public boolean remove(Object o) {
    return map.remove(o)==PRESENT;
}

//从此set中移除所有元素
public void clear() {
    map.clear();
}
```
`HashSet` 的遍历方式
```java
Set set = new HashSet();
set.add("!");
set.add("a");
set.add("1");
Iterator iterator = set.iterator();
//通过keyset的方式获取key值集合的迭代器
while (iterator.hasNext()) {
    String key = (String) iterator.next();
    System.out.println(key);
}
```
