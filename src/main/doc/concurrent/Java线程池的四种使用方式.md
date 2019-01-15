---
title: Java线程池的四种使用方式
date: 2018-12-15
comments: true 
tags:
- 线程池
categories:  
- Java并发  
---
## Executor框架

`Executor` 中常用的四种静态方法：   
        `1.newSingleThreadExecutor`是一个单线程的Executor，使用唯一的线程来执行任务。   
        `2.newFixedThreadPool`是一个固定长度的线程池，每当提交任务时就会创建线程，直到线程池最大数量，线程池的规模不再变化。   
        `3.newCachedThreadPool`是一个可缓存的线程池，线程池的规模不存在任何限制。 如果当前线程池的资源有空闲，那么将回收空闲资源。如果资源增加时需要线程，则向线程池添加线程。   
        `4.newScheduledThreadPool`是一个固定长度的线程池，并且以延迟的方式来执行任务。   

```java
public class ExecutorTask {
    public static void main(String[] args) {

        // 1.newSingleThreadExecutor是一个单线程的Executor，使用唯一的线程来执行任务。
        ExecutorService executorService1 = Executors.newSingleThreadExecutor();
        executorService1.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("newSingleThreadExecutor is done");
            }
        });

        executorService1.shutdown();
        // 2.newFixedThreadPool是一个固定长度的线程池，每当提交任务时就会创建线程，直到线程池最大数量，线程池的规模不再变化。
        ExecutorService executorService2 = Executors.newFixedThreadPool(10);
        executorService2.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("newFixedThreadPool is done");
            }
        });
        executorService2.shutdown();
        // 3.newCachedThreadPool是一个可缓存的线程池，线程池的规模不存在任何限制。 如果当前线程池的资源有空闲，那么将回收空闲资源。如果资源增加时需要线程，则向线程池添加线程。
        ExecutorService executorService3 = Executors.newCachedThreadPool();
        executorService3.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("newCachedThreadPool is done");
            }
        });
        executorService3.shutdown();
        // 4.newScheduledThreadPool是一个固定长度的线程池，并且以延迟的方式来执行任务。
        ExecutorService executorService4 = Executors.newScheduledThreadPool(10);
        executorService4.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("newScheduledThreadPool is done");
            }
        });
        executorService4.shutdown();
    }
}

```