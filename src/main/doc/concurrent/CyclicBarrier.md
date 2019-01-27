---
title: Java同步工具之CyclicBarrier
date: 2018-12-10
comments: true 
tags:
- CyclicBarrier
categories:  
- Java并发
---

## Java并发之同步工具 CyclicBarrier  

### CyclicBarrier循环屏障
`CyclicBarrier`用于让一组线程运行并互相等待，直到共同到达一个公共屏障点 (`common barrier point`，又被称为同步点)，被屏障拦截的所有线程就会继续执行。

`CyclicBarrier`与`CountDownLatch`的功能非常类似。但一个`CyclicBarrier`实例在释放等待线程后可以继续使用。让下一批线程在屏障点等待。但`CountDownLatch`实例只能被使用一次。所以`CyclicBarrier`被称为循环 的 `barrier`。


### CyclicBarrier类的用法

构造方法

`CyclicBarrier(int parties)` 创建`CyclicBarrier`对象，`parties` 表示屏障拦截的线程数量。

`CyclicBarrier(int parties, Runnable barrierAction)` 创建 `CyclicBarrier`对象，该构造方法提供了一个`Runnable`参数，在一组线程中的最后一个线程到达之后，执行`Runnable`中的程序，再之后释放正在等待的线程。`Runnable`在屏障点上只运行一次。

方法

`int await()` 通知`CyclicBarrier`实例，当前线程已经到达屏障点，然后当前线程将被阻塞。

`int await(long timeout, TimeUnit unit)`指定当前线程被阻塞的时间。

`int getNumberWaiting()`返回当前在屏障处等待的线程数。

`int getParties()`返回`CyclicBarrier`的需要拦截的线程数。

`boolean isBroken()` 查询此屏障是否处于损坏状态。

`void reset()` 将屏障重置为其初始状态。

例如：所有的线程都到达共同屏障点后执行数据，可以用于分类数据求总和
```java
public class CyclicBarrierDemo {

    public static void main(String[] args) {
        int parties = 5;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(parties, new Runnable() {
            @Override
            public void run() {
                System.out.println(currentThread().getName() + "所有线程执行完毕，结束");
            }
        });

        for (int i = 0; i < parties; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(currentThread().getName() + "准备执行");
                    try {
                        Thread.sleep(1000);
                        System.out.println(currentThread().getName() + " 开始执行，等待别的线程执行完毕");
                        cyclicBarrier.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    } finally {
                        System.out.println(currentThread().getName() + " 执行完毕，等待别的线程执行完毕");
                    }
                }
            });
            thread.start();
        }
    }
运行结果：
Thread-0准备执行
Thread-4准备执行
Thread-1准备执行
Thread-3准备执行
Thread-2准备执行
Thread-4 开始执行，等待别的线程执行完毕
Thread-3 开始执行，等待别的线程执行完毕
Thread-0 开始执行，等待别的线程执行完毕
Thread-2 开始执行，等待别的线程执行完毕
Thread-1 开始执行，等待别的线程执行完毕
Thread-3所有线程执行完毕，结束
Thread-3 执行完毕，等待别的线程执行完毕
Thread-0 执行完毕，等待别的线程执行完毕
Thread-4 执行完毕，等待别的线程执行完毕
Thread-1 执行完毕，等待别的线程执行完毕
Thread-2 执行完毕，等待别的线程执行完毕
```