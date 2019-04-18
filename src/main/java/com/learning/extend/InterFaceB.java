package com.learning.extend;

/**
 * InterFaceB extends InterFaceA
 */
public interface InterFaceB extends InterFaceA {

    default void consoleB() {
        System.out.println("InterFaceB");
    }

    /**
     * 跑
     */
    void running();
}
