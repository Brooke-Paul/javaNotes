package com.learning.synctools;

import java.util.concurrent.CountDownLatch;

/**
 * @Author xuet
 * @Description CountDownLatch 同步倒数计数器
 * @Date 12/6/18
 * @Version 1.0
 */
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
