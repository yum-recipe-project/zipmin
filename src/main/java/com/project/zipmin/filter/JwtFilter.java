package com.project.zipmin.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.project.zipmin.dto.CustomUserDetails;
import com.project.zipmin.dto.UserDto;
import com.project.zipmin.entity.User;
import com.project.zipmin.entity.Role;
import com.project.zipmin.oauth2.CustomOAuth2User;
import com.project.zipmin.util.JwtUtil;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * JWT 필터 - 클라이언트 요청 시 JWT의 유효성 검사 및 인증 객체 생성
 */
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
	
	private final JwtUtil jwtUtil;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {	

		// Authorization 헤더에서 토큰 추출
		String authrization = request.getHeader("Authorization");
		
		// 헤더가 없거나 Bearer 토큰이 아닌 경우 다음 필터로 이동
		if (authrization == null || !authrization.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		// 엑세스 토큰 추출
		String accessToken = authrization.split(" ")[1];
		
		// 토큰 만료 여부 확인
		try {
			jwtUtil.isExpired(accessToken);
		}
		catch (ExpiredJwtException e) {
			PrintWriter writer = response.getWriter();
			writer.println("access token expired");
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}

		// 토큰 카테고리 검사 (access 토큰만 허용)
		String category = jwtUtil.getCategory(accessToken);
		if (!"access".equals(category)) {
			PrintWriter writer = response.getWriter();
			writer.println("invalid access token");
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
		
		// JWT에서 사용자 정보 추출
		String username = jwtUtil.getUsername(accessToken);
		String role = jwtUtil.getRole(accessToken);
		
		System.err.println("JWTFiler) role = " + role);
		
		// 사용자 객체 생성 및 인증 설정
		User user = User.createUser(username, Role.valueOf(role));
		CustomUserDetails customUserDetails = new CustomUserDetails(user);
		Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authToken);

		// 다음 필터로 요청 전달
		filterChain.doFilter(request, response);
	}

}
