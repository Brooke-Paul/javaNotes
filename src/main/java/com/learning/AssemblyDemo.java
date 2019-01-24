package com.learning;

/**
 * @Author xuetao
 * @Description: java 自动拆装箱测试
 * @Date 2019-01-24
 * @Version 1.0
 */
public class AssemblyDemo {

    public static void main(String[] args) {
        Integer a = 1;
        Integer b = 2;
        Integer c = 3;
        Integer d = 3;
        Integer e = 321;
        Integer f = 321;

        int m = 1;
        int n = 2;
        int u = 3;
        Long o = 3L;


        System.out.println(a == m);
        //Interger 之间对比只能使用equals
        System.out.println(c.equals(d));
        System.out.println(e.equals(f));
        System.out.println(c == (a + b));
        System.out.println(c.equals(a + b));
        System.out.println(o == a + b);
        System.out.println(o == m + n);
        System.out.println(o.equals(a + b));

    }
}
