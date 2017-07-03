package com.lanwei.haq.comm.util;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * redis缓存工具类
 *
 * @author liuxinyun
 * @date 2017/01/07 14:33
 */
public class JedisUtil {

    private static final Logger logger = LoggerFactory.getLogger(JedisUtil.class);
    private final static String OK = "OK";

    // jedis 连接池
    private final JedisPool jedisPool;
    // 默认DB
    private int index = 0;

    /**
     * 此工具类不能直接new,需要默认的带参数jedisPool的构造器,可以通过spring注入的方式实例化
     */
    public JedisUtil() {
        throw new RuntimeException("实例化JedisUtil需要参数");
    }

    /**
     * spring 注入的时候会用到，需要传入一个JedisPool,默认选择DB0
     *
     * @param jedisPool
     */
    public JedisUtil(JedisPool jedisPool) {
        if (null == jedisPool) {
            throw new NullPointerException("参数[jedisPool]不能为空");
        }
        this.jedisPool = jedisPool;
        logger.info("[JedisUtil]实例化成功,使用db{}", index);
    }

    /**
     * spring 注入的时候会用到，
     *
     * @param jedisPool
     * @param index     选择的db
     */
    public JedisUtil(JedisPool jedisPool, int index) {
        if (null == jedisPool) {
            throw new NullPointerException("参数[jedisPool]不能为空");
        }
        if (index < 0) {
            throw new IllegalArgumentException("参数[index]必须大于等于0");
        }
        this.jedisPool = jedisPool;
        this.index = index;
        logger.info("[JedisUtil]实例化成功,使用db{}", index);
    }

    /**
     * 从pool里面获取资源
     */
    private Jedis getResource() {
        Jedis jedis = jedisPool.getResource();
        // 设置默认的DB
        jedis.select(0);
        return jedis;
    }

    /**
     * redis新增key-value，如果缓存里面有则覆盖，没有则新增
     *
     * @param key
     * @param value
     * @return
     */
    public boolean add(String key, String value) {
        Jedis jedis = null;
        boolean b = false;
        try {
            jedis = getResource();
            b = OK.equals(jedis.set(key, value));
        } catch (Exception e) {
            logger.error("[JedisUtil]存储key:{}-value:{}异常", key, value, e);
        } finally {
            if (null != jedis)
                jedis.close();
        }
        return b;
    }

    /**
     * redis新增一个临时的key-value，如果缓存里面有则覆盖，没有则新增，有效期的seconds，单位秒
     *
     * @param key
     * @param value
     * @param seconds 有效时间，单位：秒
     * @return
     */
    public boolean add(String key, String value, int seconds) {
        Jedis jedis = null;
        boolean b = false;
        try {
            jedis = getResource();
            b = OK.equals(jedis.setex(key, seconds, value));
        } catch (Exception e) {
            logger.error("[JedisUtil]存储key:{}-value:{}异常", key, value, e);
        } finally {
            if (null != jedis)
                jedis.close();
        }
        return b;
    }


    /**
     * redis新增key-value，如果缓存里面有则覆盖，没有则新增，Object会通过fastjson转换为String
     * 有效期为seconds秒
     *
     * @param key
     * @param value
     * @param seconds 有效期，单位秒
     * @return
     */
    public boolean add(String key, Object value, int seconds) {
        return add(key, JSON.toJSONString(value), seconds);
    }

    /**
     * redis新增key-value，如果缓存里面有则覆盖，没有则新增，Object会通过fastjson转换为String
     *
     * @param key
     * @param value
     * @return
     */
    public boolean add(String key, Object value) {
        return add(key, JSON.toJSONString(value));
    }

    /**
     * 获取key的值
     *
     * @param key
     * @return
     */
    public String get(String key) {
        Jedis jedis = null;
        String value = null;
        try {
            jedis = getResource();
            value = jedis.get(key);
        } catch (Exception e) {
            logger.error("[JedisUtil]获取key:{}异常", key, e);
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
        return value;
    }

    /**
     * 获取key的值
     *
     * @param key
     * @return
     */
    public <T> T get(String key, Class<T> clazz) {
        String value = get(key);
        if (StringUtils.isNotBlank(value)) {
            try {
                return JSON.parseObject(value, clazz);
            } catch (Exception e) {
                logger.error("[JedisUtil]获取key:{}异常，转换对象为{}失败", key, clazz, e);
            }
        }
        return null;
    }

    /**
     * 获取key的值,返回为list
     *
     * @param key
     * @return
     */
    public <T> List<T> lget(String key, Class<T> clazz) {
        String value = get(key);
        if (StringUtils.isNotBlank(value)) {
            try {
                return JSON.parseArray(value, clazz);
            } catch (Exception e) {
                logger.error("[JedisUtil]获取key:{}异常，转换List失败", key, e);
            }
        }
        return new ArrayList<>();
    }

    /**
     * 删除key，返回被删除的数量
     *
     * @param key
     * @return
     */
    public Long del(String key) {
        String[] keys = {key};
        return del(keys);
    }

    /**
     * 删除一串key,返回被删除的数量
     *
     * @param key
     * @return
     */
    public Long del(String[] key) {
        Jedis jedis = null;
        long count = 0;
        try {
            jedis = getResource();
            count = jedis.del(key);
        } catch (Exception e) {
            logger.error("[JedisUtil]删除key：{}异常", Arrays.toString(key), e);
        } finally {
            if (null != jedis)
                jedis.close();
        }
        return count;
    }

}
