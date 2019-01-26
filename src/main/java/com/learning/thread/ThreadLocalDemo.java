package com.learning.thread;

import com.learning.entity.Person;


/**
 * @Author xuetao
 * @Description: 线程局部变量使用, 在同一个线程中设置人员信息，然后使用 threadLocal.get() 获取当前线程所携带信息。
 * 常用于session管理登录信息。
 * @Date 2019-01-10
 * @Version 1.0
 */
public class ThreadLocalDemo {
    private static final ThreadLocal threadLocal = new ThreadLocal();

    public static void main(String[] args) {
        Person person = new Person("zhangshan");
        person.setName(Thread.currentThread().getName());
        threadLocal.set(person);

        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    Person ps = new Person("zhangshan");
                    ps.setName(Thread.currentThread().getName());
                    threadLocal.set(ps);

                    System.out.println("child thread："+  Thread.currentThread().getName() + ((Person) threadLocal.get()).toString());
                }
            });
            thread.start();
        }


        System.out.println("main thread：" + ((Person) threadLocal.get()).toString());
    }
}
