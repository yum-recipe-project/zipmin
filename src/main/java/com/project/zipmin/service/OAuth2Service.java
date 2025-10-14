package com.project.zipmin.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.project.zipmin.api.ApiException;
import com.project.zipmin.api.ApiResponse;
import com.project.zipmin.api.AuthErrorCode;
import com.project.zipmin.api.AuthSuccessCode;
import com.project.zipmin.util.CookieUtil;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 소셜 로그인 후 쿠키에 담긴 access token을 응답 헤더로 옮기는 메서드
 */
@Service
public class OAuth2Service {
	
	public ResponseEntity<ApiResponse> oauth2JwtHeaderSet(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		String access = null;
		
		// 쿠키가 존재하지 않으면 예외 발생
		if (cookies == null) {
			throw new ApiException(AuthErrorCode.AUTH_COOKIE_MISSING);
		}
		
		// 쿠키에서 Authorization(access token) 추출
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("Authorization")) {
				access = cookie.getValue();
			}
		}
		
		// access token이 없으면 예외 발생
		if (access == null) {
			System.err.println("OAuth2Service.java");
			throw new ApiException(AuthErrorCode.AUTH_TOKEN_MISSING);
		}
		
		// 기존 쿠키 삭제 (maxAge 0)
		response.addCookie(CookieUtil.createCookie("Authorization", null, 0));
		// access token을 응답 헤더에 추가 (Bearer 포맷)
		response.addHeader("Authorization", "Bearer " + access);
		// 상태 코드 200 설정 및 응답
		response.setStatus(HttpServletResponse.SC_OK);
		
		return ResponseEntity.status(AuthSuccessCode.AUTH_VALID_TOKEN.getStatus())
				.body(ApiResponse.success(AuthSuccessCode.AUTH_VALID_TOKEN, null));
	}
}
