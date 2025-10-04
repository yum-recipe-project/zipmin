package com.project.zipmin.service;

import java.sql.Date;

import org.springframework.stereotype.Service;

import com.project.zipmin.api.ApiException;
import com.project.zipmin.api.AuthErrorCode;
import com.project.zipmin.api.UserErrorCode;
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
			throw new ApiException(AuthErrorCode.AUTH_REFRESH_TOKEN_MISSING);
		}
		
		// 만료 여부 확인
		try {
			jwtUtil.isExpired(refresh);
		}
		catch (ExpiredJwtException e) {
			throw new ApiException(AuthErrorCode.AUTH_REFRESH_TOKEN_EXPIRED);
		}
		
		// 토큰 타입 확인
		String category = jwtUtil.getCategory(refresh);
		if (!category.equals("refresh")) {
			throw new ApiException(AuthErrorCode.AUTH_REFRESH_TOKEN_EXPIRED);
		}
		
		// 사용자 정보 추출
		int id = jwtUtil.getId(refresh);
		String username = jwtUtil.getUsername(refresh);
		String nickname = jwtUtil.getNickname(refresh);
		String avatar = jwtUtil.getAvatar(refresh);
		String role = jwtUtil.getRole(refresh);
		
		// DB의 refresh token과 일치 여부 확인
		User user = userRepository.findByUsername(username)
			    .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));
		String refreshToken = user.getRefresh();
		if (!refreshToken.equals(refresh)) {
			throw new ApiException(AuthErrorCode.AUTH_TOKEN_INVALID);
		}
		
		// 새로운 토큰 생성
		String newAccess = jwtUtil.createJwt("access", id, username, nickname, avatar, role, 600_000L);
		String newRefresh = jwtUtil.createJwt("refresh", id, username,  nickname, avatar, role, 86400_000L);
		
		// DB에 새로운 refresh 저장
		addRefresh(username, newRefresh, 84600_000L);
		
		// 응답 쿠키 설정
		TokenDto tokenDto = TokenDto.toDto(newAccess);
		response.addCookie(CookieUtil.createCookie("refresh", newRefresh, 86_400));
		
		return tokenDto;
	}
	
	public void addRefresh(String username, String refresh, Long expiredMs) {
		Date date = new Date(System.currentTimeMillis() + expiredMs);
		User user = userRepository.findByUsername(username)
			    .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));
		user.updateRefresh(refresh, date.toString());
	}

}
