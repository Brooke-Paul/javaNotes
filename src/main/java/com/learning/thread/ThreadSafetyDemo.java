package com.learning.thread;

import com.learning.entity.Person;

/**
 * @Author xuetao
 * @Description: Java中线程安全的几种方式
 * @Date 2019-01-27
 * @Version 1.0
 */
public class ThreadSafetyDemo {
    /**
     * 第一种：被final修饰的基本类型值不能改变
     */
    private final int number = 5;
    private final String message = "hello";

    /**
     * 第二种：被final修饰的对象, 对象不能改变，但是对象中的变量值可以改变
     */
    private final Person person = new Person("zhangshan");

    public static void main(String[] args) {

        ThreadSafetyDemo threadSafetyDemo = new ThreadSafetyDemo();
        System.out.println("不可变对象int：" + threadSafetyDemo.number);
        System.out.println("不可变对象string：" + threadSafetyDemo.message);


        System.out.println("不可变对象person：" + threadSafetyDemo.person);
        threadSafetyDemo.person.setName("lishi");

        //Cannot assign a value to final variable 'person'
//        threadSafetyDemo.person = new Person();
        System.out.println("不可变对象person：" + threadSafetyDemo.person);
    }


}
