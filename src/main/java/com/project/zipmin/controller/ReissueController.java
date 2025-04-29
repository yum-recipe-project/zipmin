package com.project.zipmin.controller;

import java.time.Duration;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.zipmin.service.RedisService;
import com.project.zipmin.util.JWTUtil;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ReissueController {
	
	private final JWTUtil jwtUtil;
	private final RedisService redisService;
	
	@PostMapping("/reissue")
	public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {
		// 쿠키에 존재하는 refreshToken을 가져오기
		String refresh = null;
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if ("refresh".equals(cookie.getName())) {
				refresh = cookie.getValue();
			}
		}
		
		// 검증 시작
		// refreshToken이 없는 경우
		if (refresh == null) {
			return new ResponseEntity<>("Refresh Token Null", HttpStatus.BAD_REQUEST);
		}
		// 유효기간 확인
		try {
			if (jwtUtil.isExpired(refresh)) {
				return new ResponseEntity<>("Refresh Token Expired", HttpStatus.BAD_REQUEST);
			}
		}
		catch (ExpiredJwtException e) {
			return new ResponseEntity<>("Refresh Token Expired", HttpStatus.BAD_REQUEST);
		}
		
		// 토근이 refresh인지 확인
		String category = jwtUtil.getCategory(refresh);
		
		if (!category.equals("refresh")) {
			return new ResponseEntity<>("Invalid Refresh Token", HttpStatus.BAD_REQUEST);
		}
		
		// 새로운 Token을 만들기 위해 준비
		String username = jwtUtil.getUsername(refresh);
		String role = jwtUtil.getRole(refresh);
		
		// Redis에 존재하는 refreshToken인지 확인
		String redisRefreshToken = redisService.getValues(username);
		if (redisService.checkExistsValue(redisRefreshToken)) {
			return new ResponseEntity<>("No Exists In Redis refresh token", HttpStatus.BAD_REQUEST);
		}
		
		// 새로운 JWT 토근 생성
		String newAccessToken = jwtUtil.createJwt("access", username, role, 6000000L);
		String newRefreshToken = jwtUtil.createJwt("refresh", username, role, 86400000L);
		
		// update refreshToken to Redis
		redisService.setValues(username, newRefreshToken, Duration.ofMillis(86400000L));
		
		// 응답
		response.setHeader("access", "Bearer " + newRefreshToken);
		response.addCookie(createCookie("refresh", newRefreshToken));
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	private Cookie createCookie(String key, String value) {
		Cookie cookie = new Cookie(key, value);
		cookie.setMaxAge(24*60*60);
		return cookie;
	}
}
