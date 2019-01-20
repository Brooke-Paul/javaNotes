package com.learning.thread;

/**
 * @Author xuetao
 * @Description: 线程间共享资源导致死锁
 * @Date 2019-01-20
 * @Version 1.0
 */
public class DeadLock {

    private static Object object1 = new Object();
    private static Object object2 = new Object();

    public static void main(String[] args) {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (object1) {
                    System.out.println("对象1先获取锁");
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (object2) {
                        System.out.println("对象2后获取锁");
                    }
                }
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (object2) {
                    System.out.println("对象2先获取锁");
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (object1) {
                        System.out.println("对象1后获取锁");
                    }
                }
            }
        });
        thread1.start();
        thread2.start();
    }
}