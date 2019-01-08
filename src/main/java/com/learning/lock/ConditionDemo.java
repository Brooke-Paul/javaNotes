package com.learning.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author xuet
 * @Description Condition的简单使用方式阻塞等待线程被唤醒，然后执行signal唤醒等待的线程
 * @Date 1/8/19
 * @Version 1.0
 */
public class ConditionDemo {
    public static void main(String[] args) {
        ReentrantLock reentrantLock = new ReentrantLock();
        Condition condition = reentrantLock.newCondition();

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    reentrantLock.lock();
                    System.out.println(Thread.currentThread().getName() + "等待被唤醒");
                    condition.await();
                    System.out.println(Thread.currentThread().getName() + "成功被唤醒");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    reentrantLock.unlock();
                }
            }
        });
        thread1.start();
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    reentrantLock.lock();
                    System.out.println(Thread.currentThread().getName() + "开始执行唤醒方法");
                    condition.signal();
                    System.out.println(Thread.currentThread().getName() + "已经执行唤醒方法");
                } finally {
                    reentrantLock.unlock();
                }
            }
        });
        thread2.start();
    }
}
