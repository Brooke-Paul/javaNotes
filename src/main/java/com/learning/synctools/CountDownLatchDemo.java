package com.learning.synctools;

import java.util.concurrent.CountDownLatch;

/**
 * @Author xuet
 * @Description CountDownLatch 同步倒数计数器
 * @Date 12/6/18
 * @Version 1.0
 */
public class CountDownLatchDemo {

    public static void main(String[] args) {

        CountDownLatch countDownLatchStart = new CountDownLatch(1);
        CountDownLatch countDownLatchEnd = new CountDownLatch(5);
        for (int i = 0; i < 5; i++) {
            new Test(countDownLatchStart, countDownLatchEnd).start();
        }

        try {

            System.out.println("countDownLatchStart start...");
            countDownLatchStart.countDown();
            System.out.println("countDownLatchStart end...");
            countDownLatchEnd.await();
        } catch (InterruptedException e) {
        }
        System.out.println("countDownLatchEnd end...");
    }
}

class Test extends Thread {

    CountDownLatch countDownLatchStart;
    CountDownLatch countDownLatchEnd;

    public Test(CountDownLatch countDownLatchStart, CountDownLatch countDownLatchEnd) {
        this.countDownLatchStart = countDownLatchStart;
        this.countDownLatchEnd = countDownLatchEnd;
    }

    @Override
    public void run() {
        try {
            countDownLatchStart.await();
            System.out.println(Thread.currentThread().getName() + " thread start....");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " thread end....");
        countDownLatchEnd.countDown();
    }
}
