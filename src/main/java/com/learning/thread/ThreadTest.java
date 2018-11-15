package com.learning.thread;

import java.util.concurrent.*;

/**
 * 实现多线程的三种方式
 */

public class ThreadTest extends Thread {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
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

        //使用ExecutorService、Callable、Future实现有返回结果的多线程，如果调用返回Future对象的get()方法，会阻塞直到计算完成
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        CallableDemo callableDemo1 = new CallableDemo();
        CallableDemo callableDemo2 = new CallableDemo();
        Future future1 = executorService.submit(callableDemo1);
        Future future2 = executorService.submit(callableDemo2);
        future1.get();
        future2.get();
        executorService.shutdown();
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


class CallableDemo implements Callable {
    @Override
    public Object call() throws Exception {

        for (int i = 0; i < 5; i ++) {
            System.out.println("Callable,线程:::" + Thread.currentThread().getName() + " 正在执行");
        }
        return "Callable,线程:::" + Thread.currentThread().getName() + " 正在执行";
    }
}
