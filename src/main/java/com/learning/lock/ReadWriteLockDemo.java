package com.learning.lock;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author xuet
 * @Description 读写锁
 * 在10000个线程的情况下执行map的读与写，在第一种未使用锁的情况能够清晰的发现读写会存在不一致情况，在第二种使用锁的情况能够正常读写数据
 * @Date 1/7/19
 * @Version 1.0
 */
public class ReadWriteLockDemo {
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    /**
     * 读锁
     */
    private final Lock readLock = readWriteLock.readLock();
    /**
     * 写锁
     */
    private final Lock writeLock = readWriteLock.writeLock();

    private Map map = new ConcurrentHashMap();


    public static void main(String[] args) {
        ReadWriteLockDemo readWriteLockDemo = new ReadWriteLockDemo();
        String key = "name";
        String value = "Mrs.Li";
        readWriteLockDemo.wirte(key, value);
        System.out.println("value is " + (String) readWriteLockDemo.read(key));
        for (int i = 0; i < 10000; i++) {
            int finalI = i;


            //方式一： 在10000个线程下不使用锁的情况设置map的值并获取
            Thread thread1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    if (finalI % 3 == 0) {
                        String value = "Mrs.Li" + finalI;
                        readWriteLockDemo.wirte(key, value);
                        System.out.println("write value is " + value);
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("read value is " + (String) readWriteLockDemo.read(key));
                    } else {
                        System.out.println("read value is " + (String) readWriteLockDemo.read(key));
                    }
                }
            });
            thread1.start();
            //方式一执行结果如下：
            //write value is Mrs.Li9723
            //read value is Mrs.Li9720
            //read value is Mrs.Li9720
            //read value is Mrs.Li9723
            //read value is Mrs.Li9723
            //write value is Mrs.Li9726
            //read value is Mrs.Li9726


            //方式二：在10000个线程下使用锁的情况设置map的值并获取
            Thread thread2 = new Thread(new Runnable() {
                @Override
                public void run() {
                    if (finalI % 3 == 0) {
                        String value = "Mrs.Li" + finalI;
                        readWriteLockDemo.wirteWithLock(key, value);
                        System.out.println("write value is " + value);
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("read value is " + (String) readWriteLockDemo.readWithLock(key));
                    } else {
                        System.out.println("read value is " + (String) readWriteLockDemo.readWithLock(key));
                    }
                }
            });
//            thread2.start();
            //方式二执行结果如下：
            //read value is Mrs.Li9723
            //read value is Mrs.Li9723
            //read value is Mrs.Li9723
            //read value is Mrs.Li9723
            //read value is Mrs.Li9723
            //write value is Mrs.Li9723
            //read value is Mrs.Li9723
        }


    }

    public Object readWithLock(String key) {
        try {
            readLock.lock();
            return map.get(key);
        } catch (Exception e) {

        } finally {
            readLock.unlock();
        }
        return null;
    }

    public void wirteWithLock(String key, String value) {
        try {
            writeLock.lock();
            map.put(key, value);
        } catch (Exception e) {

        } finally {
            writeLock.unlock();
        }
    }


    public Object read(String key) {
        return map.get(key);
    }

    public void wirte(String key, String value) {
        map.put(key, value);
    }
}
