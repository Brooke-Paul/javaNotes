package com.learning.collection;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @Author xuetao
 * @Description: 集合HashSet
 * @Date 2019-04-15
 * @Version 1.0
 */
public class HashSetDemo {

    private static Set<String> set = new HashSet<String>();

    public static void main(String[] args) {
        set.add(null);
        foreachSet();
        set.add(null);
        foreachSet();
        set.add(null);
        foreachSet();
        set.add(null);
        foreachSet();

        set.add("1");
        foreachSet();
        set.add("2");
        foreachSet();


    }


    public static void foreachSet() {
        Iterator iterator = set.iterator();


        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            System.out.println("foreachSet====" + key);
        }
    }

}
