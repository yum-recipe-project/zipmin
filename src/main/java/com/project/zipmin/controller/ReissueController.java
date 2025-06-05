package com.project.zipmin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.zipmin.api.ApiResponse;
import com.project.zipmin.api.AuthSuccessCode;
import com.project.zipmin.dto.TokenDto;
import com.project.zipmin.service.ReissueService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ReissueController {

	private final ReissueService reissueService;
	
	@PostMapping("/reissue")
	public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {
		TokenDto token = reissueService.reissue(request, response);
		
		return ResponseEntity.status(AuthSuccessCode.AUTH_TOKEN_REISSUE_SUCCESS.getStatus())
				.body(ApiResponse.success(AuthSuccessCode.AUTH_TOKEN_REISSUE_SUCCESS, token));
	}
}