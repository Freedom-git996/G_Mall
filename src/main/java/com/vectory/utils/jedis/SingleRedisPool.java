package com.vectory.utils.jedis;

import com.vectory.utils.PropertiesUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class SingleRedisPool {

    private static JedisPool jedisPool;
    private static Integer maxTotal = Integer.parseInt(PropertiesUtil.getProperty("redis.max.total", "20")); // 最大连接数
    private static Integer maxIdle = Integer.parseInt(PropertiesUtil.getProperty("redis.max.idle", "10")); // 空闲状态下最大的redis实例个数
    private static Integer minIdle = Integer.parseInt(PropertiesUtil.getProperty("redis.min.idle", "2")); // 空闲状态下最小的redis实例个数
    private static boolean testOnBorrow = Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.borrow", "true")); // 从池中拿取实例时是否验证，如果赋值为true，则拿到的redis一定是可用的
    private static boolean testOnReturn = Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.return", "true")); // 放回池中时...
    private static boolean blockWhenExhausted = Boolean.parseBoolean(PropertiesUtil.getProperty("redis.block.when.exhausted", "true")); // 连接耗尽时是否阻塞
    private static String ip = PropertiesUtil.getProperty("redis.ip");
    private static Integer port = Integer.parseInt(PropertiesUtil.getProperty("redis.port", "6379"));

    private static void initPool() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(maxTotal);
        jedisPoolConfig.setMinIdle(minIdle);
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setTestOnBorrow(testOnBorrow);
        jedisPoolConfig.setTestOnReturn(testOnReturn);
        jedisPoolConfig.setBlockWhenExhausted(blockWhenExhausted);
        jedisPool = new JedisPool(jedisPoolConfig, ip, port, 1000*2);
    }

    static {
        initPool();
    }

    public static Jedis getJedis() {
        return jedisPool.getResource();
    }

    public static void returnJedis(Jedis jedis) {
        jedis.close();
    }
}
