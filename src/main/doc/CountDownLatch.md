---
title: java同步工具之CountDownLatch
date: 2018-12-07
categories:  
- java 并发  
tags:
- CountDownLatch 
---
## java并发之同步工具 CountDownLatch  
### CountDownLatch 概念
`CountDownLatch` 是一种灵活的闭锁实现，它可以使一个或多个线程等待一组事件发生。闭锁状态包括一个计数器，该计数器初始化为一个正整数，表示需要等待的事件数量。
`countDown` 方法递减计数器，表示事件发生。而`await`方法等待计数器达到零，这表示所有需要等待的事件都已经发生，可以执行自定义的操作。如果计数器的值非零，那么`await`会一直阻塞直到计数器为零，或者等待中的线程中断，或者等待超时。


### `CountDownLatch`类的用法
构造方法：  
`CountDownLatch(int count)` 构造方法参数指定了计数的次数。

方法：   
`void await()`  使当前线程在锁存器倒计数至0之前一直等待，除非线程被中断。  
`boolean await(long timeout, TimeUnit unit)`  使当前线程在锁存器倒计数至0之前一直等待，除非线程被中断或超出了指定的等待时间。  
`void countDown()`  计数减1。当计数为0，则释放所有等待的线程。   
`long getCount()`  返回当前计数。   
`String toString()`  返回标识此锁存器及其状态的字符串。 

例如：使用了两个闭锁，分别表示起始门和结束门，当起始门的值为零时触发结束门的`countDown`，能使主线程高效的等待所有工作执行完成。
```java
public class CountDownLatchDemo {

    public static void main(String[] args) throws InterruptedException {
        int nThreads = 5;
        CountDownLatch countDownLatchStart = new CountDownLatch(1);
        CountDownLatch countDownLatchEnd = new CountDownLatch(nThreads);
        for (int i = 0; i < nThreads; i++) {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    synchronized (countDownLatchEnd) {
                        try {
                            System.out.println(currentThread().getName() + " countDownLatchStart start...");
                            super.run();
                            countDownLatchStart.await();
                            System.out.println(currentThread().getName() + " countDownLatchStart end...");
                        } catch (Exception e) {

                        } finally {
                            countDownLatchEnd.countDown();
                            System.out.println("current countDown count is..." + countDownLatchEnd.getCount());
                        }
                    }
                }
            };
            thread.start();
        }
        long startTime = System.currentTimeMillis();
        countDownLatchStart.countDown();

        countDownLatchEnd.await();
        long endTime = System.currentTimeMillis();

        System.out.println("countDownLatchEnd end...cost(" + (endTime - startTime) + ")");
    }
}
//运行结果：
Thread-0 countDownLatchStart start...
Thread-0 countDownLatchStart end...
current countDown count is...4
Thread-4 countDownLatchStart start...
Thread-4 countDownLatchStart end...
current countDown count is...3
Thread-3 countDownLatchStart start...
Thread-3 countDownLatchStart end...
current countDown count is...2
Thread-2 countDownLatchStart start...
Thread-2 countDownLatchStart end...
current countDown count is...1
Thread-1 countDownLatchStart start...
Thread-1 countDownLatchStart end...
current countDown count is...0
countDownLatchEnd end...cost(2)
```

### CountDownLatch的不足
`CountDownLatch`是一次性的，计数器的值只能在构造方法中初始化一次，之后没有任何机制再次对其设置值，当`CountDownLatch`使用完毕后，它不能再次被使用。


  
