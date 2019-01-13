---
title: CAS深入解析
date: 2019-01-13
comments: true 
tags:
- CAS
categories:  
- Java并发
---
## CAS
`CAS（Compare and swap）`就是比较和替换， 是一种通过硬件实现并发安全的技术。`CAS` 是 `JAVA` 并发包的实现基础，包含了三个操作数，
需要读写的内存位置V，进行比较的值A，以及写入的新值。当且仅当V的值等于A时，`CAS` 才会通过原子方式用新值替换旧值。
模拟`CAS` 原理
```java
public class CASDemo {

    private int value;

    public synchronized int get() {
        return value;
    }
    旧值与传入相同，更改新值
    public synchronized int compareAndSwap(int oldValue, int newValue) {
        if (this.value == oldValue) {
            this.value = newValue;
        }
        return oldValue;
    }
}
```