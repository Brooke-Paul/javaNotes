package com.learning.entity;

import java.io.Serializable;

/**
 * @Author xuet
 * @Description
 * @Date 2018/11/8
 * @Version 1.0
 */
public class Person implements Serializable {

    private String id;
    private String name;
    private int age;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
    public Person() {
    }
    public Person(String name) {
        this.name = name;
    }
}
