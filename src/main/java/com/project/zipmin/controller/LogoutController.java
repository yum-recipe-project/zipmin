package com.project.zipmin.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.filter.GenericFilterBean;

import com.project.zipmin.api.ApiException;
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

@Controller
@RequiredArgsConstructor
public class LogoutController extends GenericFilterBean {
	
	private final JwtUtil jwtUtil;
	private final ReissueService reissueService;
	private final UserRepository userRepository;
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		doFilter((HttpServletRequest) request, (HttpServletResponse) response, chain);
	}
	
	private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		String requestUri = request.getRequestURI();
		if (!requestUri.matches(("^\\/logout$"))) {
			filterChain.doFilter(request, response);
			return;
		}
		
		String requestMethod = request.getMethod();
		if (!requestMethod.equals("POST")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		String refresh = null;
		
		// 쿠키 유무 확인
		Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("refresh")) {
                refresh = cookie.getValue();
            }
        }

        if (refresh == null) {
            throw new ApiException("TOKEN_EXCEPTION", "Refresh Token이 없습니다.");
        }

        try {
            jwtUtil.isExpired(refresh);
        } catch (ExpiredJwtException e) {
        	throw new ApiException("TOKEN_EXCEPTION", "Refresh Token이 만료되었습니다.");
        }

        String category = jwtUtil.getCategory(refresh);
        if (!category.equals("refresh")) {
        	throw new ApiException("TOKEN_EXCEPTION", "Refresh Token이 아닙니다.");
        }

        String username = jwtUtil.getUsername(refresh);
        Boolean isExist = userRepository.existsByUsername(username);
        if (!isExist) {
            throw new ApiException("NOT_FOUND_EXCEPTION", "사용자가 존재하지 않습니다.");
        }
        
        reissueService.addRefresh(username, null, 0L);
        
        Cookie cookie = CookieUtil.createCookie("refresh", null, 0);
        
        response.addCookie(cookie);
        response.setStatus(HttpServletResponse.SC_OK);
	}
	
}
