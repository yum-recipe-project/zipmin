package com.project.zipmin.util;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.project.zipmin.dto.UserDTO;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {
	
	private final JWTUtil jwtUtil;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		
		String accessToken = request.getHeader("access");
		if (accessToken == null) {
			filterChain.doFilter(request, response);
			return;
		}
		
		// Bearer 제거 (OAuth2를 이용했다고 명시적으로 붙여주는 타입인데 JWT를 검증하거나 정보를 추출 시 제거해주어야 함)
		String originToken = accessToken.substring(7);
		
		// 유효한지 확인 후 클라이언트로 상태 코드 응답
		try {
			if (jwtUtil.isExpired(originToken)) {
				PrintWriter writer = response.getWriter();
				writer.println("Access Token Expired");
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				return;
			}
		}
		catch (ExpiredJwtException e) {
			PrintWriter writer = response.getWriter();
			writer.println("Access Token Expired");
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
		
		// 토큰 종류 확인 (Access 혹은 Refresh)
		String category = jwtUtil.getCategory(originToken);
		
		// JWTFilter는 요청에 대해 AccessToken만 취급하므로 access인지 확인
		if (!"access".equals(category)) {
			PrintWriter writer = response.getWriter();
			writer.println("Invalid Access Token");
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
		
		// JWT에서 사용자 정보 추출
		String name = jwtUtil.getUsername(originToken);
		String role = jwtUtil.getRole(originToken);
		
		UserDTO userDTO = new UserDTO();
		userDTO.setName(name);
		userDTO.setAuth(role);
		
		CustomOAuth2User customOAuth2User = new CustomOAuth2User(userDTO);
		
		Authentication authentication = new UsernamePasswordAuthenticationToken(customOAuth2User, null, customOAuth2User.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		filterChain.doFilter(request, response);
	}

}
