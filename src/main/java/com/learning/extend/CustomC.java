package com.learning.extend;


/**
 * @Author xuetao
 * @Description: 类可以单继承，接口可以多实现
 * @Date 2019-04-18
 * @Version 1.0
 */
public class CustomC extends AbstractB implements InterFaceB {

    public static void main(String[] args) {
        CustomC c = new CustomC();
        c.consoleA();
        c.consoleB();
        c.eat();
        c.say();
        c.draw();
        c.dance();
        c.sing();
        c.running();
        c.sleep();
        c.swim();
        c.climb();
    }

    @Override
    public void say() {
        System.out.println("CustomC say");
    }

    @Override
    public void eat() {
        System.out.println("CustomC eat");
    }

    @Override
    public void sleep() {
        System.out.println("CustomC sleep");
    }
}
