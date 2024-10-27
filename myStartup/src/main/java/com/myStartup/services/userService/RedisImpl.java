package com.myStartup.services.userService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisImpl {

	@Autowired
	RedisTemplate redisTemplate;
	

	public void testData() {
		redisTemplate.opsForValue().set("email","mohit.chand@flipkart.com");
	}
}
