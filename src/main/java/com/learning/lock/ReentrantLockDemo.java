package com.learning.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author xuet
 * @Description ReentrantLock 的使用
 * @Date 1/6/19
 * @Version 1.0
 */
public class ReentrantLockDemo {
    private Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        ReentrantLockDemo reentrantLockDemo = new ReentrantLockDemo();
        reentrantLockDemo.updatePersonScore(2);
    }

    public void updatePersonScore(int score) {
        lock.lock();
        try {
            //此处进行更新操作，加锁是为了同一时间只能有一个请求来更改数据状态
            System.out.println("update person score");
        } catch (Exception e) {

        } finally {
            //操作完成切记释放锁的信息
            lock.unlock();
        }
    }
}
