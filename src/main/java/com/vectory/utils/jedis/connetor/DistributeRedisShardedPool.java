package com.vectory.utils.jedis.connetor;

import com.vectory.utils.PropertiesUtil;
import redis.clients.jedis.*;
import redis.clients.jedis.util.Hashing;
import redis.clients.jedis.util.Sharded;

import java.util.ArrayList;
import java.util.List;

public class DistributeRedisShardedPool {

    private static ShardedJedisPool shardedJedisPool;

    private static Integer maxTotal = Integer.parseInt(PropertiesUtil.getProperty("redis.max.total", "20")); // 最大连接数
    private static Integer maxIdle = Integer.parseInt(PropertiesUtil.getProperty("redis.max.idle", "10")); // 空闲状态下最大的redis实例个数
    private static Integer minIdle = Integer.parseInt(PropertiesUtil.getProperty("redis.min.idle", "2")); // 空闲状态下最小的redis实例个数
    private static boolean testOnBorrow = Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.borrow", "true")); // 从池中拿取实例时是否验证，如果赋值为true，则拿到的redis一定是可用的
    private static boolean testOnReturn = Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.return", "true")); // 放回池中时...
    private static boolean blockWhenExhausted = Boolean.parseBoolean(PropertiesUtil.getProperty("redis.block.when.exhausted", "true")); // 连接耗尽时是否阻塞

    private static String ip1 = PropertiesUtil.getProperty("redis.1.ip");
    private static Integer port1 = Integer.parseInt(PropertiesUtil.getProperty("redis.1.port", "6379"));
    private static String ip2 = PropertiesUtil.getProperty("redis.2.ip");
    private static Integer port2 = Integer.parseInt(PropertiesUtil.getProperty("redis.2.port", "6380"));

    private static void initPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMinIdle(minIdle);
        config.setMaxIdle(maxIdle);
        config.setTestOnBorrow(testOnBorrow);
        config.setTestOnReturn(testOnReturn);
        config.setBlockWhenExhausted(blockWhenExhausted);

        JedisShardInfo redis1 = new JedisShardInfo(ip1, port1, 1000*2);
        JedisShardInfo redis2 = new JedisShardInfo(ip2, port2, 1000*2);
        List<JedisShardInfo> jedisShardInfoList = new ArrayList<>();
        jedisShardInfoList.add(redis1);
        jedisShardInfoList.add(redis2);

        shardedJedisPool = new ShardedJedisPool(config, jedisShardInfoList, Hashing.MURMUR_HASH, Sharded.DEFAULT_KEY_TAG_PATTERN);
    }

    static {
        initPool();
    }

    public static ShardedJedis getShardedJedis() {
        return shardedJedisPool.getResource();
    }

    public static void returnShardedJedis(ShardedJedis shardedJedis) {
        shardedJedis.close();
    }
}
