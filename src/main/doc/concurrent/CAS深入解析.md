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

## 乐观锁
乐观锁的核心算法就是采用的`CAS`, `CAS`具有原子性。乐观锁避免了悲观锁独占的现象，并且提高了并发性能。但是，乐观锁也有自己的不足：   
1). 乐观锁只能保证一个共享变量的原子操作。     
2). 长时间自旋操作可能导致线程开销太大。   
3). `CAS`的核心是通过比较内存值与预期值是否一样判断内存值是否被改动过，但是这个逻辑有些不严谨。比如原来内存值为A，
后来被某个线程改为B，最后又被改成了A，则`CAS`认为内存值未被改变过，这种场景对依赖过程值的情景运算结果影响很大，那有什么解决办法呢？
解决的思路就是给每个替换时加上版本号。