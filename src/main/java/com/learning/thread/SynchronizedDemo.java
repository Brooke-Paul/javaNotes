package com.learning.thread;

/**
 * @Author xuetao
 * @Description: Synchronized 同步 的几种方式
 * @Date 2019-05-04
 * @Version 1.0
 */
public class SynchronizedDemo implements Runnable {

    private static int count;


    public int getCount() {
        return count;
    }


    /**
     * Synchronized 修饰代码块 对象锁
     *
     * @return
     */
    public void printCount1() throws InterruptedException {
        synchronized (this) {
            System.out.println(Thread.currentThread().getName());
            Thread.sleep(4000);
            count++;
        }
        System.out.println("printCount1: " + count);
    }

    /**
     * Synchronized 修饰方法 对象锁
     *
     * @return
     */
    public synchronized void printCount2() throws InterruptedException {
        System.out.println(Thread.currentThread().getName());
        Thread.sleep(4000);
        count++;
        System.out.println("printCount2: " + count);
    }

    /**
     * Synchronized 修饰静态方法 类锁
     *
     * @return
     */
    public static synchronized void printCount3() throws InterruptedException {
        System.out.println(Thread.currentThread().getName());
        Thread.sleep(4000);
        count++;
        System.out.println("printCount3: " + count);
    }

    /**
     * Synchronized 修饰一个类 类锁
     *
     * @return
     */
    public void printCount4() throws InterruptedException {
        synchronized (SynchronizedDemo.class) {
            System.out.println(Thread.currentThread().getName());
            Thread.sleep(4000);
            count++;
            System.out.println("printCount4: " + count);
        }
    }

    public static void main(String[] args) {
        SynchronizedDemo synchronizedDemo1 = new SynchronizedDemo();
        SynchronizedDemo synchronizedDemo2 = new SynchronizedDemo();
        //相同对象实例 方法会被阻塞
//        Thread thread1 = new Thread(synchronizedDemo1);
//        Thread thread2 = new Thread(synchronizedDemo1);

        //不同对象实例 方法不会被阻塞
        Thread thread1 = new Thread(synchronizedDemo1);
        Thread thread2 = new Thread(synchronizedDemo2);

        thread1.start();
        thread2.start();


    }

    @Override
    public void run() {
        try {
            //对象锁 synchronized修饰普通方法,即为对象锁,多个线程访问同一个对象实例的这个方法时,方法会阻塞,并且只有一个线程执行完,另一个线程才会执行
            printCount1();


            //类锁 synchronized 不管多少个对象实例，都会同步
//            printCount4();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
