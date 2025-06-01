package com.project.zipmin.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Iterator;

import org.springdoc.api.ErrorMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StreamUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.zipmin.api.ApiException;
import com.project.zipmin.api.ApiResponse;
import com.project.zipmin.dto.CustomUserDetails;
import com.project.zipmin.dto.TokenDto;
import com.project.zipmin.service.ReissueService;
import com.project.zipmin.util.CookieUtil;
import com.project.zipmin.util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * 사용자 로그인 요청을 처리하는 커스텀 필터
 * - JSON 형식의 사용자 인증 정보(username, password)를 처리
 * - 인증 성공 시 JWT 토큰 발급 및 응답
 * - 인증 실패 시 401 응답 반환
 */
@RequiredArgsConstructor
public class CustomLoginFilter extends UsernamePasswordAuthenticationFilter {
	
	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;
	private final ObjectMapper objectMapper;
	// private final ReissueService reissureService;
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
		LoginDto loginDto = null;
		try {
			loginDto = objectMapper.readValue(StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8), LoginDto.class);
		}
		catch (IOException e) {
			throw new ApiException("ACCESS_DENINED", "엑세스가 거부되었습니다.");
		}
		
		String username = loginDto.getUsername();
		String password = loginDto.getPassword();
		
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password, null);
		return authenticationManager.authenticate(authToken);
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException {
		CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
		
		String username = customUserDetails.getUsername();
		String nickname = customUserDetails.getNickname();
		
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
		GrantedAuthority auth = iterator.next();
		String role = auth.getAuthority();
		
		// JWT 발급
		String access = jwtUtil.createJwt("access", username, nickname, role, 60 * 60 * 60L);
		String refresh = jwtUtil.createJwt("refresh", username, nickname, role, 86400_000L);
		
		// refresh 토큰 저장 (DB or Redis)
		// reissureService.addRefresh(username, refresh, 86400_000L);
		
		TokenDto tokenDto = TokenDto.toDto(access);
		response.addCookie(CookieUtil.createCookie("refresh", refresh, 86_400));
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(objectMapper.writeValueAsString(ApiResponse.success(tokenDto)));
	}
	
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(objectMapper.writeValueAsString(ApiResponse.error("ACCESS_DENIED_EXCEPTION", "인증되지 않은 사용자입니다.")));
	}
	
	@Data
	private static class LoginDto {
		String username;
		String password;
	}
}
