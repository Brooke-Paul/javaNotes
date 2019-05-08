package com.learning.lock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @Author xuetao
 * @Description: 简单实现自旋锁，-XX:PreBlockSpin
 * @Date 2019-05-08
 * @Version 1.0
 */
public class SimpleSpinLockDemo {

    private AtomicReference<Thread> atomicReference = new AtomicReference<Thread>();

    private static int count = 0;

    public void lock() {
        Thread thread = Thread.currentThread();
        while (!atomicReference.compareAndSet(null, thread)) {
            System.out.println(thread.getName() + "尝试获取锁");
        }
        System.out.println(thread.getName() + "已经获取了锁 ");
    }


    public void unLock() {
        Thread thread = Thread.currentThread();
        atomicReference.set(null);
        System.out.println(thread.getName() + "已经释放了锁" + count);
    }


    public static void main(String[] args) throws InterruptedException {
        int nThreads = Runtime.getRuntime().availableProcessors() << 1;
        System.out.println("线程数量" + nThreads);
        ExecutorService executorService = Executors.newFixedThreadPool(nThreads);
        CountDownLatch countDownLatch = new CountDownLatch(40000);
        SimpleSpinLockDemo simpleSpinningLock = new SimpleSpinLockDemo();
        for (int i = 0; i < 40000; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    simpleSpinningLock.lock();
                    ++count;
                    simpleSpinningLock.unLock();
                    countDownLatch.countDown();
                }
            });

        }
        countDownLatch.await();
        System.out.println(count);
    }

}
