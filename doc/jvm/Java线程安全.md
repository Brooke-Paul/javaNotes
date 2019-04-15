---
title: Java线程安全
date: 2019-01-27
comments: true 
tags:
- JVM
categories:  
- Java虚拟机
---

## Java操作共享数据

`Java`操作共享数据分为五类。不可变，绝对线程安全，相对线程安全，线程兼容和线程对立。   

### 不可变

不可变的对象一定是线程安全的，只要一个不可变对象被正确的构造出来（没有发生this引用逃逸的情况)，那么外部访问的可见状态永远不会改变。   

```java
    /**
     * 第一种：被final修饰的基本类型值不能改变
     */
    private final int number = 5;
    private final String message = "hello";

    /**
     * 第二种：被final修饰的对象, 对象不能改变，但是对象中的变量值可以改变
     */
    private final Person person = new Person("zhangshan");

    public static void main(String[] args) {

        ThreadSafetyDemo threadSafetyDemo = new ThreadSafetyDemo();
        System.out.println("不可变对象int：" + threadSafetyDemo.number);
        System.out.println("不可变对象string：" + threadSafetyDemo.message);


        System.out.println("不可变对象person：" + threadSafetyDemo.person);
        threadSafetyDemo.person.setName("lishi");

        //Cannot assign a value to final variable 'person'
//        threadSafetyDemo.person = new Person();
        System.out.println("不可变对象person：" + threadSafetyDemo.person);
    }
```

这里分析一下this引用逃逸，当线程RunablTest1还未初始化demo对象时，线程RunablTest2尝试获取未初始化demo对象的变量，代码如下：   
```java
    private int i = 0;
    static ThreadSafetyDemo demo;
    public ThreadSafetyDemo() {
        i = 1;
        System.out.println("hello");
    }
    private static class RunablTest1 implements Runnable {
        @Override
        public void run() {
            demo = new ThreadSafetyDemo();
        }

    }

    private static class RunablTest2 implements Runnable {
        @Override
        public void run() {
            try {
                System.out.println(demo.i);
            } catch (NullPointerException e) {
                System.out.println("发生空指针错误：普通变量j未被初始化");
            }
            try {
                System.out.println(demo.i);
            } catch (NullPointerException e) {
                System.out.println("发生空指针错误：final变量i未被初始化");
            }
        }

    }

    public static void main(String[] args) {
        new Thread(new RunablTest2()).start();
        new Thread(new RunablTest1()).start();
    }
    输出结果：
    发生空指针错误：普通变量j未被初始化
    发生空指针错误：final变量i未被初始化
    hello
    
```

### 绝对线程安全
绝对线程安全类定义是非常严格的，要实现一个绝对线程安全的类通常需要付出很大的、甚至有时候是不切实际的代价。   

### 相对线程安全
相对的线程安全就是所讲的线程安全，在大部分线程安全类都属于这种类型，例如`Vector`，`HashTable`等。   

### 线程兼容
线程兼容是指对象本身不是线程安全的，但可以通过同步手段保证对象在并发的同时安全的使用。   

### 线程对立
线程对立是指无论调用端是否采取了同步措施，都无法在多线程环境下同时持有一个线程对象。      






