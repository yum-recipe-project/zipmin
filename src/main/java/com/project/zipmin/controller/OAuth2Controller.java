package com.project.zipmin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.zipmin.api.ApiResponse;
import com.project.zipmin.service.OAuth2Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class OAuth2Controller {
	private final OAuth2Service oAuth2Service;
	
	@PostMapping("/oauth2-jwt-header")
	public ResponseEntity<ApiResponse> oAuth2JwtHeader(HttpServletRequest request, HttpServletResponse response) {
		return oAuth2Service.oauth2JwtHeaderSet(request, response);
	}
}
