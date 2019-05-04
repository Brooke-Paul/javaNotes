package com.learning.thread;

/**
 * @Author xuetao
 * @Description: Synchronized 同步 的几种方式
 * @Date 2019-05-04
 * @Version 1.0
 */
public class SynchronizedDemo {

    private static int count;


    public int getCount() {
        return count;
    }


    /**
     * Synchronized 修饰代码块
     *
     * @return
     */
    public void printCount1() {
        synchronized (this) {
            count++;
        }
        System.out.println(count);
    }

    /**
     * Synchronized 修饰方法
     *
     * @return
     */
    public synchronized void printCount2() {
        count++;
        System.out.println(count);
    }

    /**
     * Synchronized 修饰静态方法
     *
     * @return
     */
    public static synchronized void printCount3() {
        count++;
        System.out.println(count);
    }

    /**
     * Synchronized 修饰一个类
     *
     * @return
     */
    public void printCount4() {
        synchronized (SynchronizedDemo.class) {
            count++;
            System.out.println(count);
        }
    }

    public static void main(String[] args) {
        SynchronizedDemo synchronizedDemo = new SynchronizedDemo();
        synchronizedDemo.printCount1();
        synchronizedDemo.printCount2();
        printCount3();
        synchronizedDemo.printCount4();

    }
}
