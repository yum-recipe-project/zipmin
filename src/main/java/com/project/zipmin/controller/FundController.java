package com.project.zipmin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.zipmin.api.ApiException;
import com.project.zipmin.api.ApiResponse;
import com.project.zipmin.api.FundErrorCode;
import com.project.zipmin.api.FundSuccessCode;
import com.project.zipmin.dto.FundCreateRequestDto;
import com.project.zipmin.dto.FundCreateResponseDto;
import com.project.zipmin.mapper.UserAccountMapper;
import com.project.zipmin.repository.UserAccountRepository;
import com.project.zipmin.service.FundService;
import com.project.zipmin.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name = "Fund API", description = "후원 관련 API")
public class FundController {

	UserAccountRepository accountRepository;

	UserAccountMapper accountMapper;

	private final FundService fundService;
	private final UserService userService;
	
	
	
	@PostMapping("recipes/{id}/funds")
	public ResponseEntity<?> supportRecipe(
			@Parameter(description = "레시피의 일련번호") @PathVariable int id,
			@Parameter(description = "후원 요청 정보") @RequestBody FundCreateRequestDto fundRequestDto) {

		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
			throw new ApiException(FundErrorCode.FUND_UNAUTHORIZED);
		}
		fundRequestDto.setFunderId(userService.readUserByUsername(authentication.getName()).getId());
		
		FundCreateResponseDto fundResponseDto = fundService.createFund(fundRequestDto);

		return ResponseEntity.status(FundSuccessCode.FUND_CREATE_SUCCESS.getStatus())
				.body(ApiResponse.success(FundSuccessCode.FUND_CREATE_SUCCESS, fundResponseDto));
	}


	

	
	
	
	
	
	
}
