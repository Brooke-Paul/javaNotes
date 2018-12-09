package com.learning.synctools;

import java.util.concurrent.Semaphore;

/**
 * @Author xuet
 * @Description Semaphore 信号量
 * @Date 12/9/18
 * @Version 1.0
 */
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
                        }
                    }

                }
            };
            thread.start();
        }
    }
}
