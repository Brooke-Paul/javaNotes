package com.learning.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author xuet
 * @Description 使用显示条件变量实现有界缓存
 * @Date 1/9/19
 * @Version 1.0
 */
public class ConditionBoundedBufferDemo {
    private final Lock lock = new ReentrantLock();
    /**
     * 队列非空
     */
    private final Condition notEmpty = lock.newCondition();
    /**
     * 队列未满
     */
    private final Condition notFull = lock.newCondition();


    private static String[] array = new String[5];

    private static int head, tail, count;

    public static void main(String[] args) {
        ConditionBoundedBufferDemo conditionBoundedBufferDemo = new ConditionBoundedBufferDemo();
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        conditionBoundedBufferDemo.put(finalI + "");
                        Thread.sleep(100);
                        String value = conditionBoundedBufferDemo.take();
                        System.out.println("take value is:::" + value);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
        }
    }

    public void put(String value) throws InterruptedException {
        lock.lock();
        try {
            while (count == array.length) {
                System.out.println("队列已满，等待被唤醒");
                notFull.await();
            }
            array[tail] = value;
            if (++tail == array.length) {
                tail = 0;
            }
            ++count;
            notEmpty.signal();
            System.out.println("队列数据已插入，等待被消费");
        } finally {
            lock.unlock();
        }

    }


    public String take() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0) {
                System.out.println("队列已空，等待数据写入");
                notEmpty.await();
            }
            String value = array[head];
            array[head] = null;

            if (++head == array.length) {
                head = 0;
            }
            --count;
            notFull.signal();
            System.out.println("队列数据已取出，等待数据写入");
            return value;
        } finally {
            lock.unlock();
        }

    }
}
