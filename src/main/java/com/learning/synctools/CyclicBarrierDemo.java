package com.learning.synctools;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import static java.lang.Thread.currentThread;

/**
 * @Author xuet
 * @Description CyclicBarrier回环栅栏
 * @Date 12/10/18
 * @Version 1.0
 */
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
}
