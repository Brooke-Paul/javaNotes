package com.learning.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Author xuet
 * @Description 新建一个线程池测试带返回值的demo
 * @Date 12/24/18
 * @Version 1.0
 */
public class ThreadPoolExecutorTask {
    public static void main(String[] args) {
        int nThreads = 5;
        ExecutorService executorService = new ThreadPoolExecutor(nThreads, nThreads, 60L, TimeUnit.SECONDS, new LinkedBlockingDeque<>());
        try {
            List<Future> futures = new ArrayList<>();
            for (int i = 0; i < 10; i++) {

                Future future = executorService.submit(new TaskResult());
                futures.add(future);
            }
            for (int j = 0; j < futures.size(); j++) {
                System.out.println(futures.get(j).get());
            }
        } catch (Exception e) {

        } finally {
            executorService.shutdown();
        }
    }
}

class TaskResult implements Callable {
    @Override
    public Object call() throws Exception {
        return Thread.currentThread().getName() + ": 执行完毕";
    }
}
