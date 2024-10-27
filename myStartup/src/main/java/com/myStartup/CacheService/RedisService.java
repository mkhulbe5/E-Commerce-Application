package com.myStartup.CacheService;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RedisService {

	@Autowired
	RedisTemplate redisTemplate;

	public void setValueInRedis(Long key, Object object, Long ttl) {
		try {
			redisTemplate.opsForValue().set(key, object, ttl, TimeUnit.SECONDS);
		} catch (Exception e) {
			log.error("exception coming is {}", e);
		}
	}

	public <T> T getValueFromCache(Long key, Class<T> entityClass) throws JsonProcessingException {
		try {
			Object object = redisTemplate.opsForValue().get(key);
			ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(object.toString(), entityClass);
		} catch (JsonMappingException e) {
			log.error("exception coming is {}", e);
			return null;
		}
	}
}
