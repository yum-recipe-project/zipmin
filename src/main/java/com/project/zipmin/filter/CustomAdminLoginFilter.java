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
import com.project.zipmin.api.AuthErrorCode;
import com.project.zipmin.api.AuthSuccessCode;
import com.project.zipmin.api.ErrorCode;
import com.project.zipmin.api.UserSuccessCode;
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
 * 관리자 로그인 요청을 처리하는 커스텀 필터입니다.
 *
 * JSON 형식의 사용자 인증 정보를 처리하여,
 * 인증 성공 시 JWT 토큰을 발급하고
 * 인증 실패 시 401 응답을 반환합니다.
 * 
 * @author 정하림
 * @since 1.0 (2025-09-27)
 */
@RequiredArgsConstructor
public class CustomAdminLoginFilter extends UsernamePasswordAuthenticationFilter {
	
	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;
	private final ObjectMapper objectMapper;
	private final ReissueService reissueService;
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
		
		LoginDto loginDto = null;
		try {
			loginDto = objectMapper.readValue(StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8), LoginDto.class);
		}
		// 엑세스가 거부되었습니다.
		catch (IOException e) {
			throw new ApiException(ErrorCode.FORBIDDEN);
		}
		
		String username = loginDto.getUsername();
		String password = loginDto.getPassword();
		
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password, null);
		return authenticationManager.authenticate(authToken);
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException {
		CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
		
		int id = customUserDetails.getId();
		String username = customUserDetails.getUsername();
		String nickname = customUserDetails.getNickname();
		
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
		GrantedAuthority auth = iterator.next();
		String role = auth.getAuthority();
		
		// 관리자 로그인 요청일 경우 관리자 권한이 아니면 로그인 실패 처리
		if (request.getRequestURI().equals("/admin/login") && !role.equals("ROLE_SUPER_ADMIN") && !role.equals("ROLE_ADMIN")) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.setContentType("application/json");
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(objectMapper.writeValueAsString(ApiResponse.error(AuthErrorCode.AUTH_UNAUTHORIZED)));
			return;
		}
		
		// JWT 토큰 발급 및 응답
		String access = jwtUtil.createJwt("access", id, username, nickname, role, 60 * 60 * 60L);
		String refresh = jwtUtil.createJwt("refresh", id, username, nickname, role, 86400_000L);
		
		// refresh 토큰 저장 (DB or Redis)
		reissueService.addRefresh(username, refresh, 86400_000L);
		
		TokenDto tokenDto = TokenDto.toDto(access);
		response.addCookie(CookieUtil.createCookie("refresh", refresh, 86_400));
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(objectMapper.writeValueAsString(ApiResponse.success(UserSuccessCode.USER_LOGIN_SUCCESS, tokenDto)));
	}
	
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(objectMapper.writeValueAsString(ApiResponse.error(AuthErrorCode.AUTH_UNAUTHORIZED)));
	}
	
	@Data
	private static class LoginDto {
		String username;
		String password;
	}
}
