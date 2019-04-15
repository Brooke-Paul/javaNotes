package com.learning.collection;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @Author xuetao
 * @Description: 集合HashMap
 * @Date 2019-04-15
 * @Version 1.0
 */
public class HashMapDemo {
    private static Map map = new HashMap();

    public static void main(String[] args) {

        map.put("!", "2");


        foreachMap();
        map.put("A", "2");
        foreachMap();
        putKeyNull();
        foreachMap();

        putValueNull();
        foreachMap();
        putKeyValueNull();
        foreachMap();


        putKeyResize();

    }




    /**
     * 允许空的key值
     */
    public static void putKeyNull() {
        map.put(null, "1");
        System.out.println();
    }

    /**
     * 允许空的value值
     */
    public static void putValueNull() {
        map.put("1", null);
        System.out.println();
    }

    /**
     * 同时允许空的key value值
     */
    public static void putKeyValueNull() {
        map.put(null, null);
        System.out.println();
    }

    /**
     *
     */
    public static void putKeyResize() {
        map.put("5", "1");
        map.put("6", "1");
        map.put("7", "1");
        map.put("8", "1");
        map.put("9", "1");
        System.out.println();
    }



    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }


    public static void foreachMap() {

        Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
        System.out.println();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            String key = entry.getKey();
            String value = (String) entry.getValue();
            System.out.println("entrySet====key====" + key + "====value====" + value + "====hash====" + hash(key));
        }
        System.out.println();
    }
}
