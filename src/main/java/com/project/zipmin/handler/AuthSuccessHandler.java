package com.project.zipmin.handler;

import java.io.IOException;
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
		
		String username = customUserDetail.getUsername();
		
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
		GrantedAuthority auth = iterator.next();
		String role = auth.getAuthority();
		
		// access 토큰과 refresh 토큰 생성
		String accessToken = jwtUtil.createJwt("access", username, role, 60000L);
		String refreshToken = jwtUtil.createJwt("refresh", username, role, 86400000L);
		
		// redis에 insert (key = username / value = refreshToken)
		redisService.setValues(username, refreshToken, Duration.ofMillis(86400000L));
		
		String redisToken = redisService.getValues(username);
		System.err.println("AuthSuccessHandler) Redis 저장된 RefreshToken: " + redisToken);
		
		// 응답
		// response.setHeader("access", "Bearer " + accessToken);
		response.setHeader("Authorization", "Bearer " + accessToken);
		response.addCookie(createCookie("refresh", refreshToken));
		response.setStatus(HttpStatus.OK.value());
		// **** 로그인 성공시 redirect ****
		try {
			response.sendRedirect("http://localhost:8586/");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.err.println("AuthSuccessHandler) access token : " + accessToken);
		System.err.println("AuthSuccessHandler) refresh token : " + refreshToken);
	}
	
	private Cookie createCookie(String key, String value) {
		Cookie cookie = new Cookie(key, value);
		cookie.setMaxAge(24 * 60 * 60);
		// cookie.setPath("/"); // 쿠키가 전역에서 동작
		cookie.setHttpOnly(true); // 쿠키가 http에서만 동작할 수 있도록 (js와 같은 곳에서 가져갈 수 없도록)
		return cookie;
	}
	
	
	
}
