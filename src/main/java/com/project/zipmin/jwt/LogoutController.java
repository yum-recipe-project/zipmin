package com.project.zipmin.jwt;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LogoutController {
	
	private final JWTUtil jwtUtil;
	private final RedisService redisService;
	
	@PostMapping("/logout")
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		// Cookie가 있는지 확인
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		// Cookie 내부에 RefreshToken이 있는지 확인
		Optional<Cookie> refreshCookie = Arrays.stream(cookies)
				.filter(cookie -> "refresh".equals(cookie.getName()))
				.findFirst();
		
		// RefreshToken의 값이 존재하는지
		if (!refreshCookie.isPresent()) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		// 존재한다면 해당 RefreshToken으로부터 사용자명(username) 추출
		String refreshToken = refreshCookie.get().getValue();
		if (refreshToken == null || refreshToken.isEmpty()) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		// 사용자명을 key로 Redis에 존재하는 키의 값을 확인
		String key = jwtUtil.getUsername(refreshToken);
		if (redisService.getValues(key) == null) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		// 있다면 Redis에서 삭제
		redisService.deleteValues(key);
		
		// 클라이언트측 refresh 쿠키를 null로 변경하여 200 상태와 함께 응답
		Cookie cookie = new Cookie("refresh", null);
		cookie.setMaxAge(0);
		cookie.setPath("/");
		
		response.setStatus(HttpServletResponse.SC_OK);
		response.addCookie(cookie);
	}
	
}
