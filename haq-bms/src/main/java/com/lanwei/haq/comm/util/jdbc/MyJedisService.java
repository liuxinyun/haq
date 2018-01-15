package com.lanwei.haq.comm.util.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.*;
import redis.clients.jedis.params.sortedset.ZAddParams;
import redis.clients.jedis.params.sortedset.ZIncrByParams;

import java.util.*;

/**
 * @author liuxinyun
 */
public class MyJedisService {
    private final Logger logger = LoggerFactory.getLogger(MyJedisService.class);

    private JedisPool jedisPool;
    
    public MyJedisService(JedisPool jedisPool){
        this.jedisPool=jedisPool;
    }

    public void closeJedis(Jedis jedis) {
        if(jedis != null) {
            jedis.close();
        }
    }

    private void dealWithJedisException(Exception e) {
        logger.error("JedisException:",e);
    }
    
    public String hget(String key, String field) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hget(key, field);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            if (jedis != null) {
                closeJedis(jedis);
            }
        }
        return null;
    }
    public Map<String, String> hgetAll(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hgetAll(key);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            if (jedis != null) {
                closeJedis(jedis);
            }
        }
        return new HashMap<String, String>();
    }
    public List<String> hmget(String key,String... fields) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hmget(key, fields);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            if (jedis != null) {
                closeJedis(jedis);
            }
        }
        return new LinkedList<String>();
    }

    /**
     * 设置某个不存在的key以及过期时间,成功返回true
     *
     * @param key
     * @param value
     */
    public boolean setnx(String key, String value, int seconds) {
        boolean ret = setnx(key, value) == 1;
        if(ret){
            expire(key, seconds);
        }
        return ret;
    }

    public Long del(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.del(key);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            if (jedis != null) {
                closeJedis(jedis);
            }
        }
        return 0L;
    }

    public Long del(String... keys) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.del(keys);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            if (jedis != null) {
                closeJedis(jedis);
            }
        }
        return 0L;
    }

    /**
     * 设置某个key的过期时间
     *
     * @param key
     * @param seconds
     */
    public boolean expire(String key, int seconds) {
        Jedis jedis = jedisPool.getResource();
        try {
            Long setRet = jedis.expire(key, seconds);
            return setRet != null && setRet == 1;
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return false;
    }

    public byte[] get(byte[] key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.get(key);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            if (jedis != null) {
                closeJedis(jedis);
            }
        }
        return null;
    }

    public String get(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.get(key);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            if (jedis != null) {
                closeJedis(jedis);
            }
        }
        return null;
    }

    public Boolean exists(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.exists(key);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            if (jedis != null) {
                closeJedis(jedis);
            }
        }
        return false;
    }

    public Long exists(String... key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.exists(key);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            if (jedis != null) {
                closeJedis(jedis);
            }
        }
        return 0L;
    }

    public Long hdel(String key, String field) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.hdel(key, field);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return 0L;
    }

    public void hmset(String key, Map<String, String> hash) {
        Jedis jedis = jedisPool.getResource();
        try {
            jedis.hmset(key, hash);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
    }

    public Long hset(String key, String field, String value) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.hset(key, field, value);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return 0L;
    }

    public Boolean hexists(String key, String field) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.hexists(key, field);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return false;
    }

    public Long hsetnx(String key, String field, String value) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.hsetnx(key, field, value);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return 0L;
    }

    public List<byte[]> mget(byte[]... keys) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.mget(keys);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            if (jedis != null) {
                closeJedis(jedis);
            }
        }
        return new LinkedList<byte[]>();
    }

    public void mset(byte[]... keysvalues) {
        Jedis jedis = jedisPool.getResource();
        try {
            jedis.mset(keysvalues);
        } catch (Exception e) {
            logger.error("keysvaluesLength=" + keysvalues.length + ";" + e.getLocalizedMessage());
        } finally {
            closeJedis(jedis);
        }
    }

    public Long hincrBy(String key, String field, long value) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.hincrBy(key, field, value);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return 0L;
    }

    public Double hincrByFloat(String key, String field, double value) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.hincrByFloat(key, field, value);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return 0D;
    }

    public Long hlen(String key) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.hlen(key);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return 0L;
    }

    public Long setnx(String key, String value) {
        Jedis jedis = jedisPool.getResource();
        try {
            Long setRet = jedis.setnx(key, value);
            return setRet;
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return 0L;
    }

    public void set(byte[] key, byte[] value) {
        Jedis jedis = jedisPool.getResource();
        try {
            jedis.set(key, value);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }

    }

    public void set(String key, String value) {
        Jedis jedis = jedisPool.getResource();
        try {
            jedis.set(key, value);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }

    }

    /**
     * set值，过期时间（秒）
     * @param key key
     * @param value value
     * @param seconds 过期时间，单位秒
     */
    public void setex(String key, String value, int seconds) {
        Jedis jedis = jedisPool.getResource();
        try {
            jedis.setex(key, seconds, value);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }

    }


    public Long decr(String key) {

        Jedis jedis = jedisPool.getResource();

        try {
            return jedis.decr(key);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return 0L;
    }

    public Long decrBy(String key,long byNum) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.decrBy(key, byNum);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return 0L;
    }


    public Long incr(String key) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.incr(key);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return 0L;
    }

    public Long incrBy(String key,long byNum) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.incrBy(key, byNum);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return 0L;
    }

    public String getSet(String key, String value) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.getSet(key, value);
        } catch (Exception e) {
            logger.error("key=" + key + ";value=" + value + ";err:" + e.getLocalizedMessage());
        } finally {
            closeJedis(jedis);
        }
        return null;
    }

    public void publish(String channel, String message) {
        Jedis jedis = jedisPool.getResource();
        try {
            jedis.publish(channel, message);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }

    }

    public List<String> lrange(String key, long start, long end) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.lrange(key, start, end);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return new LinkedList<String>();
    }

    public String lindex(String key, long index) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.lindex(key, index);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return null;
    }

    public String lset(String key, long index, String value) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.lset(key, index, value);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return null;
    }

    public Long lrem(String key, long count, String value) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.lrem(key, count, value);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return 0L;
    }

    public String lpop(String key) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.lpop(key);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return null;
    }

    public String rpop(String key) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.rpop(key);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return null;
    }

    public String rpoplpush(String srckey, String dstkey) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.rpoplpush(srckey, dstkey);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return null;
    }

    public Long sadd(String key, String... members) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.sadd(key, members);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return 0L;
    }

    public String ltrim(String key, long start, long end) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.ltrim(key, start, end);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return null;
    }

    public Long llen(String key) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.llen(key);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return 0L;
    }

    public Long lpush(String key, String... strings) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.lpush(key, strings);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return 0L;
    }

    public Long rpush(String key, String... strings) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.rpush(key, strings);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return 0L;
    }

    public Long rpushx(String key, String... strings) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.rpushx(key, strings);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return 0L;
    }

    public Set<String> smembers(String key) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.smembers(key);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return new HashSet<String>();
    }

    public Long srem(String key, String... members) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.srem(key, members);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return 0L;
    }

    public String spop(String key) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.spop(key);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return null;
    }

    public Set<String> spop(String key, long count) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.spop(key, count);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return new HashSet<String>();
    }

    public Long smove(String srckey, String dstkey, String member) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.smove(srckey, dstkey, member);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return 0L;
    }

    public Long scard(String key) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.scard(key);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return 0L;
    }

    public Boolean sismember(String key, String member) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.sismember(key, member);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return false;
    }

    public Set<String> sinter(String... keys) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.sinter(keys);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return new HashSet<String>();
    }

    public Long sinterstore(String dstkey, String... keys) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.sinterstore(dstkey, keys);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return 0L;
    }

    public Set<String> sunion(String... keys) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.sunion(keys);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return new HashSet<String>();
    }

    public Long sunionstore(String dstkey, String... keys) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.sunionstore(dstkey, keys);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return 0L;
    }

    public Set<String> sdiff(String... keys) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.sdiff(keys);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return new HashSet<String>();
    }

    public Long sdiffstore(String dstkey, String... keys) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.sdiffstore(dstkey, keys);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return 0L;
    }

    public String srandmember(String key) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.srandmember(key);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return null;
    }

    public Long zadd(String key, double score, String member) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.zadd(key, score, member);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return 0L;
    }

    public Long zadd(String key, double score, String member, ZAddParams params) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.zadd(key, score, member, params);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return 0L;
    }

    public Long zadd(String key, Map<String, Double> scoreMembers) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.zadd(key, scoreMembers);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return 0L;
    }

    public Long zadd(String key, Map<String, Double> scoreMembers, ZAddParams params) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.zadd(key, scoreMembers, params);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return 0L;
    }

    public Set<String> zrange(String key, long start, long end) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.zrange(key, start, end);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return new HashSet<String>();
    }

    public Long zrem(String key, String... members) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.zrem(key, members);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return 0L;
    }

    public Double zincrby(String key, double score, String member) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.zincrby(key, score, member);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return 0D;
    }

    public Double zincrby(String key, double score, String member, ZIncrByParams params) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.zincrby(key, score, member, params);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return 0D;
    }

    public Long zrank(String key, String member) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.zrank(key, member);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return 0L;
    }

    public Long zrevrank(String key, String member) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.zrevrank(key, member);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return 0L;
    }

    public Set<String> zrevrange(String key, long start, long end) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.zrevrange(key, start, end);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return new HashSet<String>();
    }

    public Set<Tuple> zrangeWithScores(String key, long start, long end) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.zrangeWithScores(key, start, end);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return new HashSet<Tuple>();
    }

    public Set<Tuple> zrevrangeWithScores(String key, long start, long end) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.zrevrangeWithScores(key, start, end);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return new HashSet<Tuple>();
    }

    public Long zcard(String key) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.zcard(key);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return 0L;
    }

    public Double zscore(String key, String member) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.zscore(key, member);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return 0D;
    }

    public List<String> sort(String key) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.sort(key);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return new LinkedList<String>();
    }

    public List<String> sort(String key, SortingParams sortingParameters) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.sort(key, sortingParameters);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return new LinkedList<String>();
    }

    public List<String> blpop(int timeout, String... keys) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.blpop(timeout, keys);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return new LinkedList<String>();
    }

    public List<String> blpop(String... args) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.blpop(args);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return new LinkedList<String>();
    }

    public List<String> brpop(String... args) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.brpop(args);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return new LinkedList<String>();
    }

    public Long sort(String key, SortingParams sortingParameters, String dstkey) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.sort(key, sortingParameters, dstkey);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return 0L;
    }

    public Long sort(String key, String dstkey) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.sort(key, dstkey);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return 0L;
    }

    public List<String> brpop(int timeout, String... keys) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.brpop(timeout, keys);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return new LinkedList<String>();
    }

    public Long zcount(String key, double min, double max) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.zcount(key, min, max);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return 0L;
    }

    public Long zcount(String key, String min, String max) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.zcount(key, min, max);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return 0L;
    }

    public Set<String> zrangeByScore(String key, double min, double max) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.zrangeByScore(key, min, max);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return new HashSet<String>();
    }

    public Set<String> zrangeByScore(String key, String min, String max) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.zrangeByScore(key, min, max);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return new HashSet<String>();
    }

    public Set<String> zrangeByScore(String key, double min, double max, int offset, int count) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.zrangeByScore(key, min, max, offset, count);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return new HashSet<String>();
    }

    public Set<String> zrangeByScore(String key, String min, String max, int offset, int count) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.zrangeByScore(key, min, max, offset, count);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return new HashSet<String>();
    }

    public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.zrangeByScoreWithScores(key, min, max);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return new HashSet<Tuple>();
    }

    public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.zrangeByScoreWithScores(key, min, max);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return new HashSet<Tuple>();
    }

    public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max, int offset, int count) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.zrangeByScoreWithScores(key, min, max, offset, count);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return new HashSet<Tuple>();
    }

    public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max, int offset, int count) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.zrangeByScoreWithScores(key, min, max, offset, count);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return new HashSet<Tuple>();
    }

    public Set<String> zrevrangeByScore(String key, double max, double min) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.zrevrangeByScore(key, max, min);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return new HashSet<String>();
    }

    public Set<String> zrevrangeByScore(String key, String max, String min) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.zrevrangeByScore(key, max, min);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return new HashSet<String>();
    }

    public Set<String> zrevrangeByScore(String key, double max, double min, int offset, int count) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.zrevrangeByScore(key, max, min, offset, count);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return new HashSet<String>();
    }

    public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.zrevrangeByScoreWithScores(key, max, min);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return new HashSet<Tuple>();
    }

    public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min, int offset, int count) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.zrevrangeByScoreWithScores(key, max, min, offset, count);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return new HashSet<Tuple>();
    }

    public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min, int offset, int count) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.zrevrangeByScoreWithScores(key, max, min, offset, count);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return new HashSet<Tuple>();
    }

    public Set<String> zrevrangeByScore(String key, String max, String min, int offset, int count) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.zrevrangeByScore(key, max, min, offset, count);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return new HashSet<String>();
    }

    public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.zrevrangeByScoreWithScores(key, max, min);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return new HashSet<Tuple>();
    }

    public Long zremrangeByRank(String key, long start, long end) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.zremrangeByRank(key, start, end);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return 0L;
    }

    public Long zremrangeByScore(String key, double start, double end) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.zremrangeByScore(key, start, end);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return 0L;
    }

    public Long zremrangeByScore(String key, String start, String end) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.zremrangeByScore(key, start, end);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return 0L;
    }

    public Long zunionstore(String dstkey, String... sets) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.zunionstore(dstkey, sets);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return 0L;
    }

    public Long zunionstore(String dstkey, ZParams params, String... sets) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.zunionstore(dstkey, params, sets);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return 0L;
    }

    public Long zinterstore(String dstkey, String... sets) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.zunionstore(dstkey, sets);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return 0L;
    }

    public Long zinterstore(String dstkey, ZParams params, String... sets) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.zunionstore(dstkey, params, sets);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return 0L;
    }

    public Long zlexcount(String key, String min, String max) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.zlexcount(key, min, max);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return 0L;
    }

    public Set<String> zrangeByLex(String key, String min, String max) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.zrangeByLex(key, min, max);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return new HashSet<String>();
    }

    public Set<String> zrangeByLex(String key, String min, String max, int offset, int count) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.zrangeByLex(key, min, max, offset, count);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return new HashSet<String>();
    }

    public Set<String> zrevrangeByLex(String key, String max, String min) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.zrevrangeByLex(key, max, min);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return new HashSet<String>();
    }

    public Set<String> zrevrangeByLex(String key, String max, String min, int offset, int count) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.zrevrangeByLex(key, max, min, offset, count);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return new HashSet<String>();
    }

    public Long zremrangeByLex(String key, String min, String max) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.zremrangeByLex(key, min, max);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return 0L;
    }

    public Long strlen(String key) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.strlen(key);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return 0L;
    }

    public Long lpushx(String key, String... string) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.lpushx(key, string);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return 0L;
    }

    public Long persist(String key) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.persist(key);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return 0L;
    }

    public String brpoplpush(String source, String destination, int timeout) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.brpoplpush(source, destination, timeout);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return null;
    }

    public Boolean setbit(String key, long offset, boolean value) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.setbit(key, offset, value);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return false;
    }

    public Boolean setbit(String key, long offset, String value) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.setbit(key, offset, value);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return false;
    }

    public Boolean getbit(String key, long offset) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.getbit(key, offset);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return false;
    }

    public Long setrange(String key, long offset, String value) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.setrange(key, offset, value);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return 0L;
    }

    public String getrange(String key, long startOffset, long endOffset) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.getrange(key, startOffset, endOffset);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return null;
    }

    public Long bitpos(String key, boolean value) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.bitpos(key, value);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return 0L;
    }

    public Long bitcount(String key) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.bitcount(key);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return 0L;
    }

    public Long bitcount(String key, long start, long end) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.bitcount(key, start, end);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return 0L;
    }

    public Long bitop(BitOP op, String destKey, String... srcKeys) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.bitop(op, destKey, srcKeys);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return 0L;
    }

    public <T> T tryLock(String cacheKey, int timeoutInSecond, Functions.Function0<T> func, int retryTimes) {
        return tryLock(cacheKey,timeoutInSecond,func,retryTimes,7);
    }

    public <T> T tryLock(String cacheKey, int timeoutInSecond, Functions.Function0<T> func, int retryTimes, long sleepMillis) {
        Object ret = null;
        while(retryTimes > 0){
            ret = tryLock(cacheKey,timeoutInSecond,func);
            if(ret != null){
                break;
            }
            try {
                long ran = RandomUtil.getRandomIndexNum(10) - 5;
                if(ran + sleepMillis > 0){
                    sleepMillis = ran + sleepMillis;
                }
                Thread.sleep(sleepMillis);
            } catch (Exception e) {
                logger.error("Jedis sleep Exception:",e);
            }
            retryTimes--;
        }
        return (T) ret;
    }

    public <T> T tryLock(String cacheKey, int timeoutInSecond, Functions.Function0<T> func) {
        long lockEnquired = 0L;

        String currentTimeMillis = String.valueOf(System.currentTimeMillis());
        long timeoutInMillis = timeoutInSecond * 1000L + 1;
        Object o = null;
        try {
            if((lockEnquired = this.setnx(cacheKey, String.valueOf(System.currentTimeMillis() + timeoutInMillis)).longValue()) > 0L) {
                o = func.apply();
                return (T) o;
            }
            //开始校验锁里存放的过期时间
            String lockedTime = this.get(cacheKey);
            if(lockedTime == null){//得到加锁失败后的瞬间，锁被释放,则立即重试
                return tryLock(cacheKey,timeoutInSecond,func);
            }

            if(lockedTime.compareTo(currentTimeMillis) >= 0) {
                return null;
            }else{//锁内时间表示已过期，尝试加锁
                String lastLockedTime = this.getSet(cacheKey, String.valueOf(System.currentTimeMillis() + timeoutInMillis));
                if(lastLockedTime != null && lastLockedTime.compareTo(currentTimeMillis) >= 0){
                    return null;
                }//lastLockedTime为null，表示前一瞬间，锁被释放，则说明本次尝试成功
            }

            lockEnquired = 1L;
            o = func.apply();
        } finally {
            if(lockEnquired > 0L && this.get(cacheKey).compareTo(currentTimeMillis) >= 0) {
                this.del(cacheKey);
            }
        }

        return (T) o;
    }

    public Double incrByFloat(final String key, final double value) {

        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.incrByFloat(key, value);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return 0D;
    }

    public void usePipelineSync(Functions.Function3<Pipeline> func) {
        Jedis jedis = jedisPool.getResource();
        try {
            Pipeline pipeline = jedis.pipelined();
            func.apply(pipeline);
            pipeline.sync();
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
    }

    public List<Object> usePipelineSyncAndReturnAll(Functions.Function3<Pipeline> func) {
        Jedis jedis = jedisPool.getResource();
        try {
            Pipeline pipeline = jedis.pipelined();
            func.apply(pipeline);
            return pipeline.syncAndReturnAll();
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            closeJedis(jedis);
        }
        return null;
    }

    public Set<String> keys(final String pattern) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.keys(pattern);
        } catch (Exception e) {
            dealWithJedisException(e);
        } finally {
            if (jedis != null) {
                closeJedis(jedis);
            }
        }
        return Collections.emptySet();
    }

    public Long ttl(final String key)
    {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.ttl(key);
        }
        catch (Exception e)
        {
            dealWithJedisException(e);
        }
        finally {
            if (jedis != null)
            {
                closeJedis(jedis);
            }
        }
        return 0L;
    }
}
