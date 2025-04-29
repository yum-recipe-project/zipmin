package com.project.zipmin.handler;

import org.springframework.stereotype.Component;

import com.project.zipmin.util.JWTUtil;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthSuccessHandler {
	private final JWTUtil jwtUtil;
	// private final RedisService redisService;
}
