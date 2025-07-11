package com.project.zipmin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.zipmin.api.ApiException;
import com.project.zipmin.api.ApiResponse;
import com.project.zipmin.api.AuthErrorCode;
import com.project.zipmin.api.AuthSuccessCode;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@GetMapping("/check")
	public ResponseEntity<?> checkAuth(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		
		if (token == null || !token.startsWith("Bearer ")) {
			throw new ApiException(AuthErrorCode.AUTH_TOKEN_MISSING);
        }
        
        return ResponseEntity.status(AuthSuccessCode.AUTH_VALID_TOKEN.getStatus())
        			.body(ApiResponse.success(AuthSuccessCode.AUTH_VALID_TOKEN, null));
    }
}
