package com.project.zipmin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.zipmin.api.ApiException;
import com.project.zipmin.api.ApiResponse;
import com.project.zipmin.api.FundErrorCode;
import com.project.zipmin.api.FundSuccessCode;
import com.project.zipmin.api.UserAccountErrorCode;
import com.project.zipmin.api.UserErrorCode;
import com.project.zipmin.dto.FundCreateRequestDto;
import com.project.zipmin.dto.FundCreateResponseDto;
import com.project.zipmin.dto.UserAccountReadResponseDto;
import com.project.zipmin.entity.User;
import com.project.zipmin.entity.UserAccount;
import com.project.zipmin.service.FundService;
import com.project.zipmin.service.UserService;
import com.project.zipmin.swagger.FundInvalidInputFailResponse;
import com.project.zipmin.swagger.FundPointExceedFailResponse;
import com.project.zipmin.swagger.FundSupportSuccessResponse;
import com.project.zipmin.swagger.FundUnauthorizedFailResponse;
import com.project.zipmin.swagger.InternalServerErrorResponse;
import com.project.zipmin.swagger.RecipeNotFoundResponse;
import com.project.zipmin.swagger.UserNotFoundResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
	
@RestController
@RequiredArgsConstructor
@Tag(name = "Fund API", description = "후원 관련 API")
public class FundController {
	
	// R
	
	// M
	
	// S
	private final FundService fundService;
	private final UserService userService;
	
	
	// 사용자 계좌 상세 조회
	public UserAccountReadResponseDto readUserAccountById(Integer id) {

		// 입력값 검증
		if (id == null) {
			throw new ApiException(UserAccountErrorCode.USER_ACCOUNT_INVALID_INPUT);
		}
		
		UserAccount
		
		
		
		
		UserAccount account = userAccountRepository.findByUser(user).orElse(null);

		// 계좌가 없으면 null 반환
		if (account == null) {
			return null;
		}

		return userMapper.toReadAccountResponseDto(account);
	}
	
	
	
	
	
	

	
	
	
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
