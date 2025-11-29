package com.project.zipmin.handler;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.project.zipmin.oauth2.CustomOAuth2User;
import com.project.zipmin.service.ReissueService;
import com.project.zipmin.util.CookieUtil;
import com.project.zipmin.util.JwtUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomOAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	
	private final ReissueService reissureService;
	private final JwtUtil jwtUtil;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		
		// OAuth2 로그인한 사용자 정보 추출
		CustomOAuth2User customOAuth2User = (CustomOAuth2User) authentication.getPrincipal();
		int id = customOAuth2User.getId();
		String username = customOAuth2User.getUsername();
		String name = customOAuth2User.getName();
		String nickname = customOAuth2User.getNickname();
		String avatar = customOAuth2User.getAvatar();
		
		// 권한 정보 추출
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		String role = authorities.iterator().next().getAuthority();
		
		// JWT 토큰 생성
		String access = jwtUtil.createJwt("access", id, username, nickname, avatar, role, 60 * 60 * 60L);
		String refresh = jwtUtil.createJwt("refresh", id, username, nickname, avatar, role, 86400_000L);
		
		// refresh 토큰 저장 (DB or Redis)
		reissureService.addRefresh(username, refresh, 86400_000L);
		
		// 쿠키에 토큰 저장
		response.addCookie(CookieUtil.createCookie("Authorization", access, 600));
		response.addCookie(CookieUtil.createCookie("refresh", refresh, 600));
		
		// 이름 인코딩 후 리다이렉트
		String encodeName = URLEncoder.encode(name, "UTF-8");
		response.sendRedirect("http://ec2-15-164-104-202.ap-northeast-2.compute.amazonaws.com:8586/oauth2-jwt-header?name=" + encodeName);
	}
}
