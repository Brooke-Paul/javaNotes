package com.learning.executor;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author xuetao
 * @Description: 3个有5个核心线程的线程池和1个有15个核心线程的线程池有什么区别
 * 执行结果：
 * test1执行时间===9 ms
 * test2执行时间===4 ms
 * <p>
 * 1.多个线程池执行线程时执行时间更少。
 * 2.线程池中线程越少，越不容易产生阻塞。
 * @Date 2019-05-08
 * @Version 1.0
 */
public class ExecutorsDemo {
    public static void main(String[] args) {
        try {
            test1();
            test2();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    public static void test1() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(150);

        long startTime = System.currentTimeMillis();
        ExecutorService executorService2 = Executors.newScheduledThreadPool(15);
//        ExecutorService executorService2 = Executors.newFixedThreadPool(15);
        for (int i = 0; i < 150; i++) {
            executorService2.submit(new Runnable() {
                @Override
                public void run() {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        System.out.println("test1执行时间===" + (System.currentTimeMillis() - startTime) + " ms");
    }

    public static void test2() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(150);
        long startTime = System.currentTimeMillis();
        for (int j = 0; j < 3; j++) {
            ExecutorService executorService1 = Executors.newScheduledThreadPool(5);
//            ExecutorService executorService1 = Executors.newFixedThreadPool(5);
            for (int i = 0; i < 50; i++) {
                executorService1.submit(new Runnable() {
                    @Override
                    public void run() {
                        countDownLatch.countDown();
                    }
                });
            }
        }
        countDownLatch.await();
        System.out.println("test2执行时间===" + (System.currentTimeMillis() - startTime) + " ms");
    }
}
