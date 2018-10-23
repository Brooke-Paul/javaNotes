package com.learning.thread;


/**
 *
 */
public class ThreadTest extends Thread {
    public static void main(String[] args) {
        //继承Thread类实现多线程
        ThreadTest threadTest = new ThreadTest();
        threadTest.start();
        ThreadTest threadTest1 = new ThreadTest();
        threadTest1.start();

        //实现Runnable接口实现多线程
        RunnableTest runnableTest = new RunnableTest();
        Thread threadTest2 = new Thread(runnableTest);
        Thread threadTest3 = new Thread(runnableTest);
        threadTest2.start();
        threadTest3.start();
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i ++) {
            System.out.println("Thread,线程:::" + Thread.currentThread().getName() + " 正在执行");
        }
    }
}

class RunnableTest implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 5; i ++) {
            System.out.println("Runnable,线程:::" + Thread.currentThread().getName() + " 正在执行");
        }
    }
}
