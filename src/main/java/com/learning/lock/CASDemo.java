package com.learning.lock;

/**
 * @Author xuetao
 * @Description: 模拟cas操作
 * @Date 2019-01-13
 * @Version 1.0
 */
public class CASDemo {

    private int value;

    public synchronized int get() {
        return value;
    }

    public synchronized int compareAndSwap(int oldValue, int newValue) {
        if (this.value == oldValue) {
            this.value = newValue;
        }
        return oldValue;
    }
}
