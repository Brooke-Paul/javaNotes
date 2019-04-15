---
title: ThreadLocal的源码分析
date: 2019-01-10
comments: true 
tags:
- ThreadLocal
categories:  
- Java并发  
---

## ThreadLocal的介绍
`ThreadLocal`用于保存某个线程中共享变量。在同一个线程中，共享变量之间的访问时隔离的，无法跨线程访问。因此`ThreadLocal`可以用作人员信息的保存，以及展示。

## ThreadLocal提供的主要方法

### `ThreadLocal.set()` 用于当前线程信息保存
```java
    public void set(T value) {
        Thread t = Thread.currentThread();
        ThreadLocalMap map = getMap(t);
        if (map != null)
            map.set(this, value);
        else
            createMap(t, value);
    }
```
在`Thread` 中 `threadLocals` 作为变量，用于存储 `ThreadLocalMap`。 `set` 方法获取当前线程后首先调用 `getMap`。

```java
/* ThreadLocal values pertaining to this thread. This map is maintained
 * by the ThreadLocal class. */
ThreadLocal.ThreadLocalMap threadLocals = null;

ThreadLocalMap getMap(Thread t) {
    return t.threadLocals;
}
```
如果当前线程的 `getMap` 获取的 `t.threadLocals` 为空，则调用 `createMap` 方法，`createMap` 的实现方式如下：

```java
void createMap(Thread t, T firstValue) {
    t.threadLocals = new ThreadLocalMap(this, firstValue);
}
构造 一个 ThreadLocalMap 对象 并将 key， value 保存到Entry数组中。
ThreadLocalMap(ThreadLocal<?> firstKey, Object firstValue) {
    table = new Entry[INITIAL_CAPACITY];
    int i = firstKey.threadLocalHashCode & (INITIAL_CAPACITY - 1);
    table[i] = new Entry(firstKey, firstValue);
    size = 1;
    setThreshold(INITIAL_CAPACITY);
}
```
如果当前线程的 `getMap` 获取的 `t.threadLocals` 不为空，则将`value`保存到 `ThreadLocalMap`。

```java
static class ThreadLocalMap {

    Entry 继承自 WeakReference， 当对象不再使用时会被回收，避免内存泄漏。
	static class Entry extends WeakReference<ThreadLocal<?>> {
	    /** The value associated with this ThreadLocal. */
	    Object value;
	
	    Entry(ThreadLocal<?> k, Object v) {
	        super(k);
	        value = v;
	    }
	}
}

```
### `ThreadLocal.get()` 用于当前线程信息获取
```java
 public T get() {
    Thread t = Thread.currentThread(); 获取当前线程，
    ThreadLocalMap map = getMap(t); 获取当前线程本地变量
    if (map != null) {
        ThreadLocalMap.Entry e = map.getEntry(this);
        if (e != null) {
            @SuppressWarnings("unchecked")
            T result = (T)e.value;
            return result;
        }
    }
    return setInitialValue();
}
```
如果 本地线程变量不为空，从`getEntry` 中获取 `Entry` 对象   

```java
 private Entry getEntry(ThreadLocal<?> key) {
    int i = key.threadLocalHashCode & (table.length - 1);
    Entry e = table[i];
    if (e != null && e.get() == key)
        return e;
    else
        return getEntryAfterMiss(key, i, e);
}
```
否则调用 `setInitialValue` 方法。

```java
给一个空的value值， 之后的逻辑与 `ThreadLocal` 的 `set` 方法一致，获取当前线程本地变量，如果不为空重新设值，为空创建新的 `ThreadLocalMap`对象。      
private T setInitialValue() {
     T value = initialValue();
     Thread t = Thread.currentThread();
     ThreadLocalMap map = getMap(t);
     if (map != null)
         map.set(this, value);
     else
         createMap(t, value);
     return value;
 }
```
### `ThreadLocal.remove()` 用于当前线程移除本地变量
```java
public void remove() {
      ThreadLocalMap m = getMap(Thread.currentThread());
      if (m != null)
          m.remove(this);
  }
```
 





