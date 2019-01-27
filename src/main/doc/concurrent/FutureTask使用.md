---
title: FutureTask 配合 ConcurrentHashMap 使用
date: 2018-12-11
comments: true 
tags:
- FutureTask  
- ConcurrentHashMap
categories:  
- Java并发
---

## FutureTask介绍
`FutureTask`可用于异步获取执行结果或取消执行任务的场景。通过传入`Runnable`或者`Callable`的任务给`FutureTask`，直接调用其`run`方法或者放入线程池执行，之后可以在外部通过`FutureTask`的`get`方法异步获取执行结果。   

## ConcurrentHashMap 见文章 [ConcurrentHashMap的源码分析](https://www.uuuup.vip/2018/09/12/ConcurrentHashMap/)


## 本章节利用 ConcurrentHashMap 与 FutureTask 构建在多线程环境中高效的获取数据结果缓存


例如： 在多线程环境中对比 通过(`ConcurrentHashMap` 与 `FutureTask`) 和 通过 `Map` 获取人员积分 （此处积分用随机数代替）

```java
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

测试结果：（多次执行任务发现使用map时获取的值在多线程中不一致，而concurrentHashMap中获取的值不变）
        使用map人员积分为：648
        使用concurrentHashMap人员积分为：622
        使用map人员积分为：650
        使用concurrentHashMap人员积分为：622
        使用map人员积分为：650
        使用concurrentHashMap人员积分为：622
        使用map人员积分为：650
        使用concurrentHashMap人员积分为：622
        使用map人员积分为：650
```
## 小结
 `FutureTask`在高并发环境下确保任务只执行一次，配合`concurrentHashMap`使用，支持高并发的同时确保线程安全性。
