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
        Integer e = 127;
        Integer f = 127;

        Integer g = 128;
        Integer h = 128;

        int m = 1;
        int n = 2;
        int u = 3;
        Long o = 3L;


        System.out.println(a == m);
        //e == f 在 -128 ~ 127 区间返回 true
        System.out.println(e == f);
        // 说明：var=?在-128至127之间的赋值，Integer对象是在IntegerCache.cache产生，会复用已有对象，
        // 这个区间内的Integer值可以直接使用==进行判断，但是这个区间之外的所有数据，
        // 都会在堆上产生，并不会复用已有对象，这是一个大坑，推荐使用equals方法进行判断。

        //e == f 不在 -128 ~ 127 区间返回 false
        System.out.println(g == h);
        System.out.println(c.equals(d));
        System.out.println(e.equals(f));
        System.out.println(c == (a + b));
        System.out.println(c.equals(a + b));
        System.out.println(o == a + b);
        System.out.println(o == m + n);
        System.out.println(o.equals(a + b));

    }
}
