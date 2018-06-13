package com.sbatis2.cache;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Protocol;
import redis.clients.jedis.exceptions.JedisConnectionException;

/**
* <p>
* DO: 
* </p>
* <p>Company: suns </p> 
* @author suns suntion@yeah.net
* @since 2016年12月12日 下午4:14:42
*/
public class JedisFactory {
    private static final Logger logger = LoggerFactory.getLogger(JedisFactory.class);
    
    private static JedisCluster jedisCluster;
    
    private static JedisPool jedisPool;
    
    private static boolean openRedis = Boolean.FALSE;

    public JedisFactory(boolean openRedis, final GenericObjectPoolConfig poolConfig, final String host, final int port){
        this(openRedis, poolConfig, host, port, Protocol.DEFAULT_TIMEOUT, null, Protocol.DEFAULT_DATABASE);
    }
    
    public JedisFactory(boolean openRedis, GenericObjectPoolConfig poolConfig, String host, int port, final int timeout){
        this(openRedis, poolConfig, host, port, timeout, null, Protocol.DEFAULT_DATABASE);
    }
    
    public JedisFactory(boolean openRedis, GenericObjectPoolConfig poolConfig, String host, int port, final int timeout, String password){
        this(openRedis, poolConfig, host, port, timeout, password, Protocol.DEFAULT_DATABASE);
    }
    
    public JedisFactory(boolean openRedis, GenericObjectPoolConfig poolConfig, String host, int port, final int timeout, final int database){
        this(openRedis, poolConfig, host, port, timeout, null, database);
    }

    public JedisFactory(boolean openRedis, final GenericObjectPoolConfig poolConfig, final String host, final int port, final int timeout, final String password, final int database){
        if (openRedis) {
            try{
                Set<HostAndPort> jedisClusterNodes= new HashSet<HostAndPort>();
                jedisClusterNodes.add(new HostAndPort(host, port));
                jedisCluster = new JedisCluster(jedisClusterNodes, poolConfig);
                logger.info("JedisFactory load JedisCluster...");
            } catch (Exception e) {
                logger.warn("JedisFactory failed load JedisCluster...{}", e.getMessage());
            }
            
            try {
                jedisPool = new JedisPool(poolConfig, host, port, timeout, password, database);
                logger.info("JedisFactory load JedisPool...");
            } catch (Exception e) {
                logger.warn("JedisFactory failed load JedisPool...{}", e.getMessage());
            }
        }
        JedisFactory.setOpenRedis(openRedis);
    }
    
    public static Jedis getJedisFromPool() {
        Jedis jedis = null;
        try {
            if (JedisFactory.getJedisPool() != null) {
                jedis = JedisFactory.getJedisPool().getResource();
            }
        } catch (JedisConnectionException e) {
            logger.error("Reids not connection,Please check redis database is running or modify properties {redis.openRedis} to false...{}", e.getMessage());
        } catch (Throwable e) {
            logger.error("JedisFromPool failed...", e);
        }
        return jedis;
    }
    
    public static boolean getOpenRedis() {
        return openRedis;
    }

    public static void setOpenRedis(boolean openRedis) {
        JedisFactory.openRedis = openRedis;
    }

    public static JedisCluster getJedisCluster() {
        return jedisCluster;
    }

    public static void setJedisCluster(JedisCluster jedisCluster) {
        JedisFactory.jedisCluster = jedisCluster;
    }
    
    public static JedisPool getJedisPool() {
        return jedisPool;
    }

    public static void setJedisPool(JedisPool jedisPool) {
        JedisFactory.jedisPool = jedisPool;
    }
    
}
