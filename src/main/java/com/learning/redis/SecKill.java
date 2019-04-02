package com.learning.redis;


import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author xuetao
 * @Description: redis实现多线程秒杀
 * @Date 2019-04-02
 * @Version 1.0
 */
public class SecKill implements Runnable {

    String successUser = "successUser";
    String failUser = "failUser";
    Jedis jedis = new Jedis("127.0.0.1", 6379);

    private String userName;

    public SecKill(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public void run() {
        try {
            //监视一个(或多个) key ，如果在事务执行之前这个(或这些) key 被其他命令所改动，那么事务将被打断。
            jedis.watch(SecKillTest.key);
            System.out.println(jedis.get(SecKillTest.userNum));
            if ((jedis.incr(SecKillTest.userNum)) <= 20) {

                //开启事务
                Transaction transaction = jedis.multi();
                //自增
                transaction.incr(SecKillTest.key);

                List list = transaction.exec();
                if (list != null) {
                    System.out.println(userName + "抢到一部iphone");
                    jedis.sadd(successUser, userName);
                } else {
                    System.out.println(userName + "抢手机失败");
                    jedis.sadd(failUser, userName);
                }
            } else {
                System.out.println(userName + "抢手机失败");
                jedis.sadd(failUser, userName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
    }
}
