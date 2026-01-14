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
import com.project.zipmin.api.CommentErrorCode;
import com.project.zipmin.api.FundErrorCode;
import com.project.zipmin.api.FundSuccessCode;
import com.project.zipmin.api.UserAccountErrorCode;
import com.project.zipmin.api.UserErrorCode;
import com.project.zipmin.dto.FundCreateRequestDto;
import com.project.zipmin.dto.FundCreateResponseDto;
import com.project.zipmin.dto.UserAccountCreateRequestDto;
import com.project.zipmin.dto.UserAccountCreateResponseDto;
import com.project.zipmin.dto.UserAccountReadResponseDto;
import com.project.zipmin.dto.UserAccountUpdateRequestDto;
import com.project.zipmin.dto.UserReadResponseDto;
import com.project.zipmin.entity.Role;
import com.project.zipmin.entity.User;
import com.project.zipmin.entity.UserAccount;
import com.project.zipmin.mapper.UserAccountMapper;
import com.project.zipmin.repository.UserAccountRepository;
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

	UserAccountRepository accountRepository;

	UserAccountMapper accountMapper;

	private final FundService fundService;
	private final UserService userService;
	
	
	
	
	
	// 계좌 상세 조회
	public UserAccountReadResponseDto readAccountById(Integer id) {

		// 입력값 검증
		if (id == null) {
			throw new ApiException(UserAccountErrorCode.USER_ACCOUNT_INVALID_INPUT);
		}
		
		// 계좌 조회
		UserAccount userAccount = accountRepository.findById(id)
				.orElseThrow(() -> new ApiException(UserAccountErrorCode.USER_ACCOUNT_NOT_FOUND));

		return accountMapper.toReadResponseDto(userAccount);
	}
	
	
	
	
	
	// 계좌 작성
	public UserAccountCreateResponseDto createAccount(UserAccountCreateRequestDto accountRequestDto) {
		
		// 입력값 검증
		if (accountRequestDto == null 
				|| accountRequestDto.getBank() == null 
				|| accountRequestDto.getAccountnum() == null
				|| accountRequestDto.getName() == null
				|| accountRequestDto.getUserId() == 0) {
			throw new ApiException(UserAccountErrorCode.USER_ACCOUNT_INVALID_INPUT);
		}
		
		// 계좌 저장
		UserAccount userAccount = accountMapper.toEntity(accountRequestDto);
		try {
			userAccount = accountRepository.save(userAccount);
			return accountMapper.toCreateResponseDto(userAccount);
		}
		catch (Exception e) {
			throw new ApiException(UserAccountErrorCode.USER_ACCOUNT_CREATE_FAIL);
		}
	}
	
	
	
	
	// 계좌 수정
	public UserAccountCreateResponseDto updateAccount(UserAccountUpdateRequestDto accountRequestDto) {

		// 입력값 검증
		if (accountRequestDto == null 
				|| accountRequestDto.getUserId() == 0
				|| accountRequestDto.getBank() == null
				|| accountRequestDto.getAccountnum() == null
				|| accountRequestDto.getName() == null) {
			throw new ApiException(UserAccountErrorCode.USER_ACCOUNT_INVALID_INPUT);
		}
		
		// 계좌 조회
		UserAccount account = accountRepository.findById(accountRequestDto.getUserId())
				.orElseThrow(() -> new ApiException(UserAccountErrorCode.USER_ACCOUNT_NOT_FOUND));

		// 권한 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserReadResponseDto userDto = userService.readUserByUsername(username);
		if (!userDto.getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			if (userDto.getRole().equals(Role.ROLE_ADMIN.name())) {
				if (account.getUser().getRole().equals(Role.ROLE_SUPER_ADMIN)) {
					throw new ApiException(UserAccountErrorCode.USER_ACCOUNT_FORBIDDEN);
				}
				else if (account.getUser().getRole().equals(Role.ROLE_ADMIN)) {
					if (userDto.getId() != account.getUser().getId()) {
						throw new ApiException(UserAccountErrorCode.USER_ACCOUNT_FORBIDDEN);
					}
				}
			}
			else {
				if (userDto.getId() != account.getUser().getId()) {
					throw new ApiException(UserAccountErrorCode.USER_ACCOUNT_FORBIDDEN);
				}
			}
		}
		
		// 변경 값 설정
		account.setBank(accountRequestDto.getBank());
		account.setAccountnum(accountRequestDto.getAccountnum());
		account.setName(accountRequestDto.getName());

		try {
			account = accountRepository.save(account);
			return accountMapper.toCreateResponseDto(account);
		}
		catch (Exception e) {
			throw new ApiException(UserAccountErrorCode.USER_ACCOUNT_UPDATE_FAIL);
		}
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
