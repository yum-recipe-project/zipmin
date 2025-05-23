package com.project.zipmin.jwt;

import java.time.Duration;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/*
 * Redis에 추가, 삭제, 조회, 수정 등의 기능을 담당
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class RedisService {
	private final RedisTemplate<String, Object> redisTemplate;
	
	// TTL 설정 X
	public void setValues(String key, String data) {
		ValueOperations<String, Object> values = redisTemplate.opsForValue();
		values.set(key, data);
	}
	
	// RefreshToken TTL 설정 O
	public void setValues(String key, String data, Duration duration) {
		ValueOperations<String, Object> values = redisTemplate.opsForValue();
		values.set(key, data, duration);
	}
	
	// redis에 저장된 RefreshToken 삭제
	public void deleteValues(String key) {
		redisTemplate.delete(key);
	}
	
	// redis에 저장된 RefreshToken 조회
	public String getValues(String key) {
		ValueOperations<String, Object> values = redisTemplate.opsForValue();
		if (values.get(key) == null) {
			return "false";
		}
		return (String) values.get(key);
	}
	
	public boolean checkExistsValue(String value) {
		return value.equals("false");
	}
}
