package com.suntion;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTemplate {

	@Autowired
	StringRedisTemplate redisTemplate;
	
	@Test
	public void test() {





        System.out.println(redisTemplate.opsForValue().get("suns"));
	}

}
