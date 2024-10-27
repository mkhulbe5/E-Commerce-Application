package com.myStartup;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@SpringBootApplication
public class MyStartupApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyStartupApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public RedisTemplate redisTemplate(RedisConnectionFactory factory) {
		RedisTemplate r = new RedisTemplate<>();
		r.setConnectionFactory(factory);
		r.setKeySerializer(new StringRedisSerializer());
		r.setValueSerializer(new StringRedisSerializer());
		return r;
	}
}
