package com.project.zipmin.service;

import java.sql.Date;

import org.springframework.stereotype.Service;

import com.project.zipmin.api.ApiException;
import com.project.zipmin.dto.TokenDto;
import com.project.zipmin.entity.User;
import com.project.zipmin.repository.UserRepository;
import com.project.zipmin.util.CookieUtil;
import com.project.zipmin.util.JwtUtil;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ReissueService {
	
	private final UserRepository userRepository;
	private final JwtUtil jwtUtil;
	
	/**
	 * access token 재발급 요청 처리
	 * - 쿠키에서 refresh token 추출
	 * - 유효성 검사 및 DB refresh 일치 여부 확인
	 * - 새로운 access/refresh token 발급 및 쿠키에 저장
	 */
	public TokenDto reissue(HttpServletRequest request, HttpServletResponse response) {
		String refresh = null;
		Cookie[] cookies = request.getCookies();
		
		// 쿠키에서 refresh token 추출
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("refresh")) {
				refresh = cookie.getValue();
			}
		}
		
		if (refresh == null) {
			throw new ApiException("TOKEN_EXCEPTION", "Refresh Token이 없습니다.");
		}
		
		// 만료 여부 확인
		try {
			jwtUtil.isExpired(refresh);
		}
		catch (ExpiredJwtException e) {
			throw new ApiException("TOKEN_EXCEPTION", "Refresh Token이 만료되었습니다.");
		}
		
		// 토큰 타입 확인
		String category = jwtUtil.getCategory(refresh);
		if (!category.equals("refresh")) {
			throw new ApiException("TOKEN_EXCEPTION", "Refresh Token이 아닙니다.");
		}
		
		// 사용자 정보 추출
		String username = jwtUtil.getUsername(refresh);
		String role = jwtUtil.getRole(refresh);
		
		// DB의 refresh token과 일치 여부 확인
		String refreshToken = userRepository.findByUsername(username).getRefresh();
		if (!refreshToken.equals(refresh)) {
			throw new ApiException("TOKEN_EXCEPTION", "올바른 Refresh Token이 아닙니다.");
		}
		
		// 새로운 토큰 생성
		String newAccess = jwtUtil.createJwt("access", username, role, 600_000L);
		String newRefresh = jwtUtil.createJwt("access", username, role, 86400_000L);
		
		// DB에 새로운 refresh 저장
		addRefresh(username, newRefresh, 84600_000L);
		
		// 응답 쿠키 설정
		TokenDto tokenDto = TokenDto.toDto(newAccess);
		response.addCookie(CookieUtil.createCookie("refresh", newRefresh, 86_400));
		
		return tokenDto;
	}
	
	public void addRefresh(String username, String refresh, Long expiredMs) {
		Date date = new Date(System.currentTimeMillis() + expiredMs);
		User user = userRepository.findByUsername(username);
		user.updateRefresh(refresh, date.toString());
	}

}
