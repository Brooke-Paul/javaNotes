# Redis 实现秒杀功能


## 场景一： 10000个用户同时在线秒杀20部iponeX， 超出秒杀的数量全部失败。

使用命令：`decr` 或者 `incr` 都支持原子操作。  `decr` 表示 自减一， `incr` 表示自增一。   
伪代码如下：
```java
   /**
     * 设置用户key
     */
    static final String userNum = "userNum";
    /**
     * 设置iphonex 20部，抢完为止
     */
    static final String key = "iphonex";
    //初始设置iponex数量
    jedis.set(key, "20");
    //初始设置能够获取ipone的人数
    jedis.set(userNum, "20");
    
    jedis.watch(SecKillTest.key);
    //判断当前用户数是否超限制
    if ((jedis.decr(SecKillTest.userNum)) >= 0) {
        //开启事务
        Transaction transaction = jedis.multi();
        //自减操作
        transaction.decr(SecKillTest.key);

        List list = transaction.exec();
        if (list != null) {
            System.out.println(name + "抢到一部iphone");
            jedis.sadd(successUser, name);
        } else {
//                    System.out.println(name + "抢手机失败");
            jedis.sadd(failUser, name);
        }
    } else {
//                System.out.println(name + "抢手机失败");
        jedis.sadd(failUser, name);
    }
    

```