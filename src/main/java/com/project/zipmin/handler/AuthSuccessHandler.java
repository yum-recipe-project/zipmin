package com.project.zipmin.handler;

import java.time.Duration;
import java.util.Collection;
import java.util.Iterator;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.project.zipmin.jwt.CustomOAuth2User;
import com.project.zipmin.jwt.JWTUtil;
import com.project.zipmin.jwt.RedisService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/*
 * 핸들러를 통해 JWT를 발급하고 Redis에 발급된 refresh 토큰을 넣는 기능
 */
@Component
@RequiredArgsConstructor
public class AuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	private final JWTUtil jwtUtil;
	private final RedisService redisService;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		CustomOAuth2User customUserDetail = (CustomOAuth2User) authentication.getPrincipal();
		
		String id = customUserDetail.getId();
		
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
		GrantedAuthority auth = iterator.next();
		String role = auth.getAuthority();
		
		// access 토큰과 refresh 토큰 생성
		String accessToken = jwtUtil.createJwt("access", id, role, 60000L);
		String refreshToken = jwtUtil.createJwt("refresh", id, role, 86400000L);
		
		// redis에 insert (key = id / value = refreshToken)
		redisService.setValues(id, refreshToken, Duration.ofMillis(86400000L));
		
		// 응답
		response.setHeader("accesss", "Bearer " + accessToken);
		response.addCookie(createCookie("refresh", refreshToken));
		response.setStatus(HttpStatus.OK.value());
	}
	
	private Cookie createCookie(String key, String value) {
		Cookie cookie = new Cookie(key, value);
		cookie.setMaxAge(24 * 60 * 60);
		// cookie.setPath("/"); // 쿠키가 전역에서 동작
		cookie.setHttpOnly(true); // 쿠키가 http에서만 동작할 수 있도록 (js와 같은 곳에서 가져갈 수 없도록)
		return cookie;
	}
	
	
	
}
