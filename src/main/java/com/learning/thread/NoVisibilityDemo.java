package com.learning.thread;


import com.learning.entity.Person;

public class NoVisibilityDemo extends Thread {
    volatile boolean ready = false;

    @Override
    public void run() {
        System.out.println("Thread start");
        while (ready) {
            System.out.println("Thread is running " + ready);
        }
        System.out.println("Thread end");
    }

    public void setRead(boolean ready) {
        this.ready = ready;
    }

    public static void main(String[] args) throws InterruptedException {
        NoVisibilityDemo noVisibilityDemo = new NoVisibilityDemo();
        noVisibilityDemo.start();
        Thread.sleep(2000);
        noVisibilityDemo.setRead(true);
        System.out.println("ready is " + noVisibilityDemo.ready);
    }

}
