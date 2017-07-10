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

public final class RedisUtil {

    private static final Logger log = LoggerFactory.getLogger(RedisUtil.class);

    /**
     * redis服务器地址
     */
    private final static String ADDR = "172.16.1.12";
    /**
     * redis端口号
     */
    private final static int PORT = 6379;
    /**
     * 访问密码
     */
    private final static String AUTH = "haqteam";
    /**
     * 可用连接实例的最大数目，默认值为8。
     * 如果赋值-1，表示不限制。
     * 如果pool已经分配了MAX_TOTAL个jedis实例，则此时pool的状态为exhausted(耗尽)
     */
    private static int MAX_TOTAL = 1024;
    /**
     * 最大空闲实例，默认值为8
     */
    private static int MAX_IDLE = 200;
    /**
     * 等待可用连接的最大时间，单位毫秒。
     * 如果超过等待时间，直接抛出JedisConnectionException。
     * 默认-1，表示永不超时。
     */
    private static int MAX_WAIT = 10000;
    private static int TIMEOUT = 10000;
    /**
     * 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
     */
    private static boolean TEST_ON_BORROW = true;

    private static JedisPool jedisPool = null;

    private synchronized static JedisPool getJedisPool(){
        if (jedisPool != null){
            return jedisPool;
        }
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(MAX_TOTAL);
            config.setMaxIdle(MAX_IDLE);
            config.setMaxWaitMillis(MAX_WAIT);
            config.setTestOnBorrow(TEST_ON_BORROW);
            jedisPool = new JedisPool(config, ADDR, PORT, TIMEOUT, AUTH);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return jedisPool;
    }

    /**
     * 获取Jedis实例
     * @return
     */
    public synchronized static Jedis getJedis(int index){
        try {
            Jedis jedis = getJedisPool().getResource();
            jedis.select(index);
            return jedis;
        }catch (Exception e){
            log.error(e.getMessage());
            return null;
        }
    }

    /**
     * 释放jedis资源
     * @param jedis
     */
    public static void close(final Jedis jedis){
        if (jedis != null){
            jedis.close();
        }
    }

    public static void main(String[] args) {
        Jedis jedis = getJedis(1);
        Set<String> set = jedis.keys( "http*");
        Iterator<String> it = set.iterator();
        while(it.hasNext()){
            String keyStr = it.next();
            System.out.println(keyStr);
            jedis.del(keyStr);
        }
        close(jedis);
    }

}
