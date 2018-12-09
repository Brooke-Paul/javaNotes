---
title: java同步工具之Semaphore
date: 2018-12-07
categories:  
- java 并发  
tags:
- Semaphore 
---

## java并发之同步工具 Semaphore  

### Semaphore信号量
`Semaphore` 用于控制线程并发数，`Semaphore` 可以控制同时访问资源的线程个数，实现一个文件允许访问的并发数。
`Semaphore`维护了一个许可集，当调用`acquire()` 方法获取到许可时即可进入，无法获取许可的线程阻塞到有许可（或者直到连接中断或者连接超时）。
而 `release()` 表示释放一个许可。可以把`Semaphore`看成是一种共享锁。`Semaphore`允许同一时间多个线程同时访问临界区。

### Semaphore类的用法

常用方法：   
`public void acquire()`  获取许可。

`public boolean tryAcquire()`  尝试获取许可。

`public boolean tryAcquire(long timeout, TimeUnit unit)` 在指定的时间内尝试地获取1个许可。

`public void release()`  释放许可。该方法一般调用于finally块中。
`int availablePermits()` 返回此信号量中当前可用的许可数。


例如： 在多线程中展示信号量的可用许可，为了准确测试当前许可数量，需要同步锁住`semaphore` 对象进行测试。
```java
public class SemaphoreDemo {

    public static void main(String[] args) {
        int permits = 4;
        Semaphore semaphore = new Semaphore(permits);

        for (int i = 0; i < permits; i++) {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    synchronized (semaphore) {
                        try {
                            System.out.println("当前可用许可 " + (semaphore.availablePermits()));
                            semaphore.acquire();
                            super.run();
                            System.out.println(currentThread().getName() + " 获取一个许可");
                            Thread.sleep(1000);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {
                            System.out.println("当前可用许可 " + (semaphore.availablePermits()));
                            System.out.println(currentThread().getName() + " 释放一个许可");
                            semaphore.release();
                            System.out.println("当前可用许可 " + (semaphore.availablePermits()));
                        }
                    }

                }
            };
            thread.start();
        }
    }
}

输出结果：
当前可用许可 4
Thread-0 获取一个许可
当前可用许可 3
Thread-0 释放一个许可
当前可用许可 4
Thread-3 获取一个许可
当前可用许可 3
Thread-3 释放一个许可
当前可用许可 4
Thread-2 获取一个许可
当前可用许可 3
Thread-2 释放一个许可
当前可用许可 4
Thread-1 获取一个许可
当前可用许可 3
Thread-1 释放一个许可
```




