---
title: Volatile关键字解析
date: 2018-11-29 
comments: true 
tags:
- Volatile
categories:  
- Java并发  
---

## 前言
 在介绍`Volatile`之前，先简单了解一下`java`内存模型。在`java`内存模型中规定，简称为`JMM`。
## 主内存和工作内存
### 主内存
存放数据共享的区域，数据可以在线程中共享，包括实例变量，静态变量等。线程在工作时，需要将主存中的数据拷贝到工作内存，线程之间的数据不可以共享，并且不能直接操作主内存或者其他线程工作内存中的数据。
这里所提到的主内存可以简单认为是堆内存，而工作内存可以简单认为是栈内存。

### 工作内存
每个线程有自己的工作内存，各个线程之间不支持数据资源共享，线程使用的变量是从主内存的副本拷贝，线程所有的数据操作必须在工作内存中，无法直接获取主内存的数据，线程之间的变量值传递必须通过主内存来完成。


## Volatile 原理
   `Java`语言提供了一种稍弱的同步机制，即`volatile`变量，确保变量的更新操作通知到其他线程。当线程变量被`volatile`修饰后，编译器会自动识别该变量属于线程共享，因此在读取`volatile`修饰的变量时总是读取最新的值。
   
   在访问`volatile`变量时不会执行加锁操作，因此也就不会使执行线程阻塞，因此`volatile`变量是一种比`sychronized`关键字更轻量级的同步机制。   
   加锁机制即可以确保原子性又可以确保可见性，而`volatile` 只能确保可见性。   

### 内存可见性
   变量在被`volatile`修饰后更改变量的值会立即被写回主存，同时会使其他线程工作内存的旧值失效，新值对于其他线程是可见的，因为`volatile`修饰的变量在每个线程中使用前都会去主内存中获取最新的值。
   虽然`volatile`修饰的值每次都会被线程获取到，但是并不能保证线程并发的安全性。因为忽略了原子性，`volatile`在执行时并不能保证原子性，对变量的操作可能就是很简单的 `i+=1`,但是底层需要多条字节码操作才能完成，在并发情况并不能保证原子性。
### 禁止指令重排序优化
   指令重排序是指CPU在正确处理指令依赖(数据依赖)并且保障程序执行得到正确结果的情况下，调整代码的执行顺序，允许将多条指令不按照程序规定顺序分开发送给各相应电路单元处理。需要注意的是指令重排序不会影响到代码在单线程环境下的执行，只会影响到多线程并发情况下执行的正确性。

### 使用条件
如果让`volatile`保证原子性，必须符合以下两条规则：
1.运算结果并不依赖于变量的当前值，或者能够确保只有一个线程修改变量的值；  
2.变量不需要与其他的状态变量共同参与不变约束；

### 先行发生原则
先行发生（`happens-before`）是判断数据是否存在竞争、线程是否安全的主要依据。如果A操作在B操作之前完成，那么B将观察到A执行的结果。   
`Java` 内存模型中存在着一些天然的先行发生关系：   
1). 程序次序规则：在一个线程内，操作分先后，控制流顺序。  
2). 管程锁定规则：一个`unlock`释放锁的操作先行发生于后面对同一个锁的`lock`加锁操作。   
2). `volatile`变量规则：对一个`volatile`变量的写操作先行发生于后面对这个变量的读操作。   
2). 线程启动规则：`Thread`的`start()`方法先发生于对这个线程的所有操作。   
5). 线程终止规则：线程的所有操作都先行发生于对此线程的终止操作。   
6). 线程中断规则：对线程`interrupt()`方法的调用先行发生于被中断线程的代码检测到中断事件的发生。   
7). 对象终结规则：一个对象的初始化完成先行发生于它的`finalize()` 方法。   
8). 传递性：如果A操作先行发生于B操作，操作B先行发生于C操作，那么可以得出A操作先发生于C操作。

```java
    //volatile 使用
    volatile boolean ready = false;
    @Override
    public void run() {
        System.out.println("Thread start");
        while (ready) {
            System.out.println("Thread is running " + ready);
        }
        System.out.println("Thread end");
    }

    public void setRead(boolean ready) {
        this.ready = ready;
    }

    public static void main(String[] args) throws InterruptedException {
        NoVisibilityDemo noVisibilityDemo = new NoVisibilityDemo();
        noVisibilityDemo.start();
        Thread.sleep(2000);
        noVisibilityDemo.setRead(true);
        System.out.println("ready is " + noVisibilityDemo.ready);
    }
```

