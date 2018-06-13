package com.sbatis2.cache;

import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

/**
* <p>
* DO: 
* </p>
* <p>Company: suns </p> 
* @author suns suntion@yeah.net
* @since 2016年12月8日 下午4:47:42
*/
public class RedisCache implements Cache{
    private static final Logger logger = LoggerFactory.getLogger(RedisCache.class);
    
    /** The ReadWriteLock. */
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    
    private static RedisSerializer<Object> serializer = new JdkSerializationRedisSerializer();
    
    private final String id;
    
    private static final String UTF_8 = "utf-8";
    
    public RedisCache(String id ) {
        if (id == null || "".equals(id)) {
            throw new IllegalArgumentException("Cache instances require an ID");
        }
        this.id = id;
    }
    
    @Override
    public String getId() {
        return this.id;
    }

    /**
     * 按照一定规则标识key
     */
    private String getKey(Object key) {
        StringBuilder accum = new StringBuilder();
        accum.append(this.id).append(":");
        accum.append(DigestUtils.md5Hex(String.valueOf(key)));
        return accum.toString();
    }
    
    /**
     * redis key规则前缀
     */
    private String getKeys() {
        return this.id + ":*";
    }
    
    
    @Override
    public void clear() {
        Jedis jedis = null;
        try {
            if (null != JedisFactory.getJedisFromPool()) {
                jedis = JedisFactory.getJedisFromPool();
                Set<byte[]> keys = jedis.keys(getKeys().getBytes(UTF_8));
                logger.debug("×××××,delete cache :{},size:{}", getKeys(), keys.size());
                for (byte[] key : keys) {
                    jedis.del(key);
                }
            }
        } catch (JedisConnectionException e) {
            logger.warn("Reids not connection..!!! Please check redis database is running...{}", e.getMessage());
        } catch (Throwable e) {
            logger.error("Reids clear error, error msg by:{}", e.getLocalizedMessage());
            e.printStackTrace();
        } finally {
            if (jedis != null && jedis.isConnected()) {
                jedis.disconnect();
                jedis.close();
            }
        }
    }


    @Override
    public Object getObject(Object key) {
        Object result = null;
        Jedis jedis = null;
        try {
            if (null != JedisFactory.getJedisFromPool()) {
                jedis = JedisFactory.getJedisFromPool();
                byte[] keyByte = getKey(key).getBytes(UTF_8);
                
                if (jedis.exists(keyByte)) {
                    byte[] valueByte = jedis.get(keyByte);
                    //反序列化key
                    result = serializer.deserialize(valueByte);
                    logger.debug("↑↑↑↑↑,Reids getObject by key:{}", getKey(key));
                }
            }
        } catch (SerializationException e) {
            logger.error("Reids getObject error, error msg:{}", e.getLocalizedMessage());
            e.printStackTrace();
        } catch (JedisConnectionException e) {
            logger.warn("Reids not connection..!!! Please check redis database is running...{}", e.getMessage());
        } catch (Throwable e) {
            logger.error("Reids getObject error, error msg:{}", e.getLocalizedMessage());
            e.printStackTrace();
        } finally {
            if (jedis != null && jedis.isConnected()) {
                jedis.disconnect();
                jedis.close();
            }
        }
        return result;
    }
    
    @Override
    public void putObject(Object key, Object value) {
        Jedis jedis = null;
        try {
            if (null != JedisFactory.getJedisFromPool()) {
                jedis = JedisFactory.getJedisFromPool();
                //key转换
                byte[] keyByte = getKey(key).getBytes(UTF_8);
                //序列化value
                byte[] valueByte = serializer.serialize(value);
                
                jedis.set(keyByte, valueByte);
                logger.debug("↓↓↓↓↓,Reids put key:{}", getKey(key));
            }
        } catch (SerializationException e) {
            logger.error("Reids putObject mothed RedisSerializer object error, error msg by:{}", e.getLocalizedMessage());
            e.printStackTrace();
        } catch (JedisConnectionException e) {
            logger.warn("Reids not connection..!!! Please check redis database is running...{}", e.getMessage());
        } catch (Throwable e) {
            logger.error("Reids putObject error, error msg by:{}", e.getLocalizedMessage());
            e.printStackTrace();
        } finally {
            if (jedis != null && jedis.isConnected()) {
                jedis.disconnect();
                jedis.close();
            }
        }
    }
    
    @Override
    public Object removeObject(Object key) {
        Object result = null;
        Jedis jedis = null;
        try {
            if (null != JedisFactory.getJedisFromPool()) {
                jedis = JedisFactory.getJedisFromPool();
                result = jedis.del(getKey(key).getBytes(UTF_8));
                logger.debug("×××××,LRU Reids removeObject key:{}", getKey(key));
            }
        } catch (SerializationException e) {
            logger.error("Reids not connection..!!! Please check redis database is running");
            e.printStackTrace();
        } catch (JedisConnectionException e) {
            logger.warn("Reids not connection..!!! Please check redis database is running...{}", e.getMessage());
        } catch (Throwable e) {
            logger.error("Reids removeObject error, error msg by:{}", e.getLocalizedMessage());
            e.printStackTrace();
        } finally {
            if (jedis != null && jedis.isConnected()) {
                jedis.disconnect();
                jedis.close();
            }
        }
        return result;
    }

    /**
     * 获取缓存对象中存储的键/值对的数量
     * 可选的方法，没有被框架核心调用
     */
    @Override
    public int getSize() {
        int result = 0;
        Jedis jedis = null;
        try {
            if (null != JedisFactory.getJedisFromPool()) {
                jedis = JedisFactory.getJedisFromPool();
                Set<byte[]> keys = jedis.keys(getKeys().getBytes(UTF_8));
                if (null != keys && !keys.isEmpty()) {
                    result = keys.size();
                }
                logger.debug("{},Reids size:{}", this.id, result);
            }
        } catch (JedisConnectionException e) {
            logger.warn("Reids not connection..!!! Please check redis database is running...{}", e.getMessage());
        } catch (Throwable e) {
            logger.error("Reids removeObject error, error msg by:{}", e.getLocalizedMessage());
            e.printStackTrace();
        } finally {
            if (jedis != null && jedis.isConnected()) {
                jedis.disconnect();
                jedis.close();
            }
        }
        return result;
    }
    
    /**
     * 获取读写锁
     * 可选的方法，从3.2.6起这个方法不再被框架核心调用
     * 任何需要的锁，都必须由缓存供应商提供
     * @return A ReadWriteLock
     */
    @Override
    public ReadWriteLock getReadWriteLock() {
        logger.debug(">>>Reids getReadWriteLock");
        return readWriteLock;
    }
}
