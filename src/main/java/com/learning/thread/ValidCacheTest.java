package com.learning.thread;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * @Author xuet
 * @Description 利用ConcurrentHashMap 与 FutureTask 构建多线程下计算结果安全性与准确性(获取人员积分)
 * @Date 12/11/18
 * @Version 1.0
 */
public class ValidCacheTest {

    private static final ConcurrentHashMap<String, Future<Integer>> concurrentHashMap = new ConcurrentHashMap<String, Future<Integer>>();
    private static final Map map = new HashMap();

    public static void main(String[] args) throws Exception {
        ValidCacheTest validCacheTest = new ValidCacheTest();
        String personID = "123456";
        for (int i = 0; i < 5000; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Object o1 = validCacheTest.compute(personID);
                        Object o2 = validCacheTest.computeMap(personID);

                        System.out.println("使用concurrentHashMap人员积分为：" + (Integer) o1);
                        System.out.println("使用map人员积分为：" + (Integer) o2);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
        }

    }


    public Object compute(String personID) throws Exception {
        Future future = concurrentHashMap.get(personID);
        if (future == null) {
            Callable callable = new Callable() {
                @Override
                public Object call() throws Exception {
                    // 从数据库通过人员ID获取积分，这里暂用随机数
                    return new Random().nextInt(1000);
                }
            };
            //FutureTask 表示一个计算过程，如果计算完成，立即返回计算结果，否则一直阻塞，直到计算结果出来并返回。
            FutureTask futureTask = new FutureTask(callable);
            //putIfAbsent在放入数据时，如果存在重复的key，那么putIfAbsent不会放入值，线程安全
            future = concurrentHashMap.putIfAbsent(personID, futureTask);
            if (future == null) {
                future = futureTask;
                futureTask.run();
            }
        }
        return future.get();
    }


    public Object computeMap(String personID) throws Exception {
        Object object = map.get(personID);
        if (object == null) {
            Object o = new Random().nextInt(1000);
            //putIfAbsent在放入数据时，如果存在重复的key，那么putIfAbsent不会放入值，线程不安全
            object = map.putIfAbsent(personID, o);
            if (object == null) {
                object = o;
            }
        }
        return object;
    }
}