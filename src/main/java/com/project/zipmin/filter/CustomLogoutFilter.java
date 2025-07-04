package com.project.zipmin.filter;

import java.io.IOException;

import org.springframework.web.filter.GenericFilterBean;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.zipmin.api.ApiException;
import com.project.zipmin.api.ApiResponse;
import com.project.zipmin.api.AuthErrorCode;
import com.project.zipmin.api.UserErrorCode;
import com.project.zipmin.api.UserSuccessCode;
import com.project.zipmin.controller.ReissueController;
import com.project.zipmin.dto.UserReadResponseDto;
import com.project.zipmin.repository.UserRepository;
import com.project.zipmin.service.ReissueService;
import com.project.zipmin.util.CookieUtil;
import com.project.zipmin.util.JwtUtil;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomLogoutFilter extends GenericFilterBean {
	
	private final JwtUtil jwtUtil;
	private final ReissueService reissueService;
	private final UserRepository userRepository;
	private final ObjectMapper objectMapper;
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		doFilter((HttpServletRequest)request, (HttpServletResponse)response, chain);
	}
	
	private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		String requestUri = request.getRequestURI();
		if (!requestUri.matches("^\\/logout$")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		String requestMethod = request.getMethod();
		if (!requestMethod.equals("POST")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		String refresh = null;
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("refresh")) {
				refresh = cookie.getValue();
			}
		}
		
		// 리프레시 토큰이 없습니다.
		if (refresh == null) {
			throw new ApiException(AuthErrorCode.AUTH_REFRESH_TOKEN_MISSING);
		}
		
		// 리프레시 토큰이 만료되었습니다.
		try {
			jwtUtil.isExpired(refresh);
		}
		catch (ExpiredJwtException e) {
			throw new ApiException(AuthErrorCode.AUTH_REFRESH_TOKEN_EXPIRED);
		}
		
		// 리프레시 토큰이 아닙니다.
		String category = jwtUtil.getCategory(refresh);
		if (!category.equals("refresh")) {
			throw new ApiException(AuthErrorCode.AUTH_REFRESH_TOKEN_INVALID);
		}
		
		// 사용자가 존재하지 않습니다.
		String username = jwtUtil.getUsername(refresh);
		Boolean isExist = userRepository.existsByUsername(username);
		if (!isExist) {
			throw new ApiException(UserErrorCode.USER_NOT_FOUND);
		}
		
		reissueService.addRefresh(username, null, 0L);
		Cookie cookie = CookieUtil.deleteCookie("refresh");
		
		response.addCookie(cookie);
		response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(objectMapper.writeValueAsString(ApiResponse.success(UserSuccessCode.USER_LOGOUT_SUCCESS, null)));
	}
	
}
