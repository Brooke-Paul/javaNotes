package com.learning.redis;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import redis.clients.jedis.Jedis;

import java.util.concurrent.*;

/**
 * @Author xuetao
 * @Description: redis实现多线程秒杀
 * 10000个人在100个线程下秒杀20个苹果手机(待完善，目前进入的快即能获取，后续得优化为离散随机)
 * @Date 2019-04-02
 * @Version 1.0
 */
public class SecKillTest {
    /**
     * 线程数量
     */
    private static final int threadNum = 10000;

    /**
     * 设置用户key
     */
    static final String userNum = "userNum";
    /**
     *
     */
    static final String key = "iphonex";


    public static void main(String[] args) {


        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("redisFactory").build();
        ExecutorService executor = new ThreadPoolExecutor(20, 20,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(), threadFactory);

        final Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.set(key, "0");
        jedis.set(userNum, "0");
        jedis.del("successUser");
        jedis.del("failUser");
        jedis.close();
        for (int i = 0; i < threadNum; i++) {
            executor.execute(new SecKill("zhangshan" + i));
        }
        executor.shutdown();
    }
}
