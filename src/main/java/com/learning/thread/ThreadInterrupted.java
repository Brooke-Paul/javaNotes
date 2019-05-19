package com.learning.thread;

import static java.lang.Thread.interrupted;

/**
 * @Author xuetao
 * @Description: 线程中断与恢复
 * @Date 2019-05-19
 * @Version 1.0
 */
public class ThreadInterrupted {


    public static void main(String[] args) {
        Interrupted interrupted = new Interrupted();
        Thread thread = new Thread(interrupted);
        try {
            thread.start();
            Thread.sleep(10);
            thread.interrupt();
        } catch (InterruptedException e) {
            System.out.println("thread state is update");
        }
    }
}


class Interrupted implements Runnable {

    @Override
    public void run() {
        //静态interrupted() 方法 或者 Thread.currentThread().isInterrupted() 判断状态
        while (!interrupted()) {
            System.out.println(Thread.currentThread().getName() + "===线程被中断");
        }
    }
}



