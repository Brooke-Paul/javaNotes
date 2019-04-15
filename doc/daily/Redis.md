# Redis 实现秒杀功能

## 首先了解一下Redis的基本数据结构

`Redis`常用数据类型结构：   
- `String` 字符串类型
   
   简单的`key-value`类型，`value` 可以是字符串，也可以是数字。 `key`的最大长度为512M，`value`的最大长度为512M   
   常用命令有 `set`， `get`， `incr`，`incrby`，`decr`，`decrby`等。         
   `String`类型是最常用的一种数据类型，采用 `key-value`存储。  
   使用场景： 粉丝计数，关注计数。
- `List` 列表类型
    
  `key`的最大长度为512M， list的元素个数最多2^32-1个。   
   常用命令有`lpush`， `rpush`， `lpop`， `rpop`， `lrange`， `blpop`， `brpop`等。   
   使用场景：我的粉丝列表，关注列表等。   
   当然`List` 结构还可以用作队列（先进先出 `lpush` - `rpop` 或者 `rpush` - `lpop`)，以及栈（先进后出 `lpush` - `lpop` 或者 `rpush` - `rpop`)。 

- `Hash` 类型（类似`HashMap`)
  
  `key`的最大长度为512M， list的元素个数最多2^32-1个。      
   常用命令有 `hget`,`hset`,`hgetall`， `hmset`， `hmget`。   
   使用场景： 用户信息更改部分信息，比如姓名，生日字段。  
   
- `Set` 集合
   
  `key`的最大长度为512M， list的元素个数最多2^32-1个。      
   常用命令有 `sadd`, `spop`, `smembers`, `srem`
- `Sorted Set` 有序集合

  `key`的最大长度为512M， list的元素个数最多2^32-1个。   
   常用命令有 `zadd`, `zrange`, `zrevrange`, `zrem`
   




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