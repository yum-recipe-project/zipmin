package com.project.zipmin.jwt;

import java.time.Duration;

import org.hibernate.boot.model.relational.ColumnOrderingStrategyLegacy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/*
 * AccessToken 만료 시 RefreshToken을 이용해 새로운 AccessToken과 RefreshToken을 발급
 * 
 * 토큰 재발급 시 검증사항
 * 1. Cookie 내에 RefreshToken이 존재하는지
 * 2. Token이 RefreshToken이 맞는지
 * 3. Token의 유효기간이 지났는지
 * 4. Redis에 해당 토큰이 존재하는지
 */
@RestController
@RequiredArgsConstructor
public class ReissueController {
	private final JWTUtil jwtUtil;
	private final RedisService redisService;
	
	@PostMapping("/reissue")
	public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {
		// 쿠키에 존재하는 refreshToken 가져오기
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
		
		// 토큰이 refresh인지 확인
		String category = jwtUtil.getCategory(refresh);
		if (!category.equals("refresh")) {
			return new ResponseEntity<>("Invalid Refresh Token", HttpStatus.BAD_REQUEST);
		}
		
		// 새로운 토큰을 만들기 위한 준비
		String id = jwtUtil.getId(refresh);
		String auth = jwtUtil.getRole(refresh);
		
		// Redis 내에 존재하는 refreshToken인지 확인
		String redisRefreshToken = redisService.getValues(id);
		if (redisService.checkExistsValue(redisRefreshToken)) {
			return new ResponseEntity<>("no exists in redis refresh token", HttpStatus.BAD_REQUEST);
		}
		
		// 새로운 JWT Token 생성
		String newAccessToken = jwtUtil.createJwt("access", id, auth, 600000L);
		String newRefreshToken = jwtUtil.createJwt("refresh", id, auth, 86400000L);
		
		// update refreshToken to Redis
		redisService.setValues(id, newRefreshToken, Duration.ofMillis(86400000L));
		
		// 응답
		response.setHeader("access", "Bearer " + newAccessToken);
		response.addCookie(createCookie("refresh", newRefreshToken));
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	private Cookie createCookie(String key, String value) {
		Cookie cookie = new Cookie(key, value);
		cookie.setMaxAge(24 * 60 * 60);
		cookie.setHttpOnly(true);
		return cookie;
	}
}
