package com.vectory.utils;

import com.google.common.collect.Lists;
import com.vectory.utils.jedis.SingleRedisPool;
import redis.clients.jedis.Jedis;

import java.util.List;

public class JedisUtil {

    /**
     * 根据 key-value 添加一条数据
     * 如果 key 已存在，则覆盖
     * @param key key
     * @param value value
     * @return ok
     */
    public static String set(String key, String value){
        Jedis jedis = null;
        String result;

        try {
            jedis = SingleRedisPool.getJedis();
            result = jedis.set(key, value);
            return result;
        } catch (Exception e) {
            return null;
        } finally {
            if(jedis != null) {
                SingleRedisPool.returnJedis(jedis);
            }
        }
    }

    /**
     * 根据 key-value 添加一条数据
     * 如果 key 已存在，则不添加
     * @param key key
     * @param value value
     * @return 1-成功 0-未生效
     */
    public static Long setOnlyKeyNotExist(String key, String value){
        Jedis jedis = null;
        Long result;

        try {
            jedis = SingleRedisPool.getJedis();
            result = jedis.setnx(key, value);
            return result;
        } catch (Exception e) {
            return null;
        } finally {
            if(jedis != null) {
                SingleRedisPool.returnJedis(jedis);
            }
        }
    }

    /**
     * 根据 key-value 批量添加
     * @param keyValue key-value
     * @return ok
     */
    public static String setBatch(String... keyValue){
        Jedis jedis = null;
        String result;

        try {
            jedis = SingleRedisPool.getJedis();
            result = jedis.mset(keyValue);
            return result;
        } catch (Exception e) {
            return null;
        } finally {
            if(jedis != null) {
                SingleRedisPool.returnJedis(jedis);
            }
        }
    }

    /**
     * 根据 key-value 添加一条数据，并设置有效期 单位为秒
     * @param key key
     * @param value value
     * @param exTime exTime
     * @return ok
     */
    public static String setEx(String key, String value, int exTime){
        Jedis jedis = null;
        String result;

        try {
            jedis = SingleRedisPool.getJedis();
            result = jedis.setex(key, exTime,value);
            return result;
        } catch (Exception e) {
            return null;
        } finally {
            if(jedis != null) {
                SingleRedisPool.returnJedis(jedis);
            }
        }
    }

    /**
     * 设置key的有效期 单位为秒
     * @param key key
     * @param exTime value
     * @return 1-成功 0-未生效
     */
    public static Long expire(String key, int exTime){
        Jedis jedis = null;
        Long result;

        try {
            jedis = SingleRedisPool.getJedis();
            result = jedis.expire(key, exTime);
            return result;
        } catch (Exception e) {
            return null;
        } finally {
            if(jedis != null) {
                SingleRedisPool.returnJedis(jedis);
            }
        }
    }

    /**
     * 根据 key 获取 value
     * @param key key
     * @return value or null
     */
    public static String get(String key){
        Jedis jedis = null;
        String result;

        try {
            jedis = SingleRedisPool.getJedis();
            result = jedis.get(key);
            return result;
        } catch (Exception e) {
            return null;
        } finally {
            if(jedis != null) {
                SingleRedisPool.returnJedis(jedis);
            }
        }
    }

    /**
     * 根据 key 获取 value
     * @param keys keys
     * @return values or null
     */
    public static List<String> getBatch(String... keys){
        Jedis jedis = null;
        List<String> result = Lists.newArrayList();

        try {
            jedis = SingleRedisPool.getJedis();
            result = jedis.mget(keys);
            return result;
        } catch (Exception e) {
            return null;
        } finally {
            if(jedis != null) {
                SingleRedisPool.returnJedis(jedis);
            }
        }
    }

    /**
     * 根据 key 删除一条数据
     * @param key key
     * @return 1-成功 0-未生效
     */
    public static Long del(String key){
        Jedis jedis = null;
        Long result;

        try {
            jedis = SingleRedisPool.getJedis();
            result = jedis.del(key);
            return result;
        } catch (Exception e) {
            return null;
        } finally {
            if(jedis != null) {
                SingleRedisPool.returnJedis(jedis);
            }
        }
    }
}
