package com.project.zipmin.jwt;

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

/*
 * 사용자 요청이 들어왔을 때 JWT 토큰이 유효한지 확인하는 기능을 담당
 */
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {
	
	private final JWTUtil jwtUtil;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		// access 토큰
		String accessToken = request.getHeader("access");
		if (accessToken == null) {
			filterChain.doFilter(request, response);
			return;
		}
		
		// JWT 검증 시 OAuth2를 이용했다고 명시적으로 붙여주는 타입인 Bearer 접두사 제거 필요
		String originToken = accessToken.substring(7);
		
		// 유효한지 확인 후 클라이언트로 상태 코드 응답
		try {
			if (jwtUtil.isExpired(originToken)) {
				PrintWriter writer = response.getWriter();
				writer.println("Access Token Expired");
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 error
				return;
			}
		}
		catch (ExpiredJwtException e) {
			PrintWriter writer = response.getWriter();
			writer.println("Access Token Expired");
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 error
			return;
		}
		
		// 토큰 종류 확인
		String category = jwtUtil.getCategory(originToken);
		
		// JWTFilter는 요청에 대해 access 토큰만 취급하므로 access인지 확인
		if (!"access".equals(category)) {
			PrintWriter writer = response.getWriter();
			writer.println("Invalid Access Token");
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
		
		// JWT에서 사용자 정보 추출
		String id = jwtUtil.getId(originToken);
		String role = jwtUtil.getRole(originToken);
		
		UserDTO userDTO = new UserDTO();
		userDTO.setId(id);
		userDTO.setAuth(role);
		
		CustomOAuth2User customOAuth2User = new CustomOAuth2User(userDTO);
		
		Authentication authentication = new UsernamePasswordAuthenticationToken(customOAuth2User, null, customOAuth2User.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		filterChain.doFilter(request, response);
	}

}
