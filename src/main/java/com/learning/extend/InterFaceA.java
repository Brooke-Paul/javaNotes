package com.learning.extend;

/**
 * 接口A
 */
public interface InterFaceA {


    /**
     * 说
     */
    void say();

    /**
     * 吃
     */
    void eat();

    /**
     * 睡
     */
    void sleep();

    default void consoleA() {
        System.out.println("InterFaceA");
    }
}
