package com.suntion;

import com.suntion.service.RedissonLocker;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisLock {

	private static final String LOCK_SUCCESS = "OK";
	private static final String SET_IF_NOT_EXIST = "NX";
	private static final String SET_WITH_EXPIRE_TIME = "PX";
	private static final Long RELEASE_SUCCESS = 1L;

	@Autowired
	RedissonLocker redissonLocker;
	
	@Test
	public void redisLock() {
		System.out.println("redis中的setnx具备天然的锁机制；如果redis中存在key,则返回0，存储失败；不存在，则返回1，存储成功");


		//加锁
		redissonLocker.lock("suns");
		try {
			//TODO 干事情
		} catch (Exception e) {
			//异常处理
		}finally{
			//释放锁
			redissonLocker.unlock("suns");
		}

		System.out.println(redissonLocker.isLocked("locak"));

//		redisTemplate.getConnectionFactory().getConnection().set x
//
//
//		(lockKey, requestid, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expretime);



	}
}
