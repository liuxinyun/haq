package com.lanwei.haq.comm.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Iterator;
import java.util.Set;

/**
 * @作者：刘新运
 * @日期：2017/6/6 22:32
 * @描述：类
 */

public class RedisUtil {

    private static final Logger log = LoggerFactory.getLogger(RedisUtil.class);

    //jedis连接池
    private final JedisPool jedisPool;

    public RedisUtil(JedisPool jedisPool) {
        if (null == jedisPool) {
            throw new NullPointerException("参数[jedisPool]不能为空");
        }
        this.jedisPool = jedisPool;
        log.info("[JedisUtil]实例化成功");
    }

    /**
     * 获取Jedis实例
     * @return
     */
    public Jedis getJedis(int index){
        try {
            Jedis jedis = jedisPool.getResource();
            jedis.select(index);
            return jedis;
        }catch (Exception e){
            log.error("getJedis failed for "+e.getMessage(), e);
            return null;
        }
    }

    /**
     * 释放jedis资源
     * @param jedis
     */
    public void close(final Jedis jedis){
        if (jedis != null){
            jedis.close();
        }
    }


}
