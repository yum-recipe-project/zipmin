package com.project.zipmin.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.project.zipmin.service.RedisService;
import com.project.zipmin.util.CustomOAuth2User;
import com.project.zipmin.util.JWTUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	private final JWTUtil jwtUtil;
	private final RedisService redisService;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		CustomOAuth2User customUserDetail = (CustomOAuth2User) authentication.getPrincipal();
		
		String id = customUserDetail.getId();
		
		
	}
	
	
	
}
