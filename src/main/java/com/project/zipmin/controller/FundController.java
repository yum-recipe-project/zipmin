package com.project.zipmin.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.zipmin.api.ApiException;
import com.project.zipmin.api.ApiResponse;
import com.project.zipmin.api.FundErrorCode;
import com.project.zipmin.api.FundSuccessCode;
import com.project.zipmin.api.UserAccountSuccessCode;
import com.project.zipmin.api.WithdrawErrorCode;
import com.project.zipmin.api.WithdrawSuccessCode;
import com.project.zipmin.dto.FundCreateRequestDto;
import com.project.zipmin.dto.FundCreateResponseDto;
import com.project.zipmin.dto.FundReadResponseDto;
import com.project.zipmin.dto.UserAccountCreateRequestDto;
import com.project.zipmin.dto.UserAccountCreateResponseDto;
import com.project.zipmin.dto.UserAccountReadResponseDto;
import com.project.zipmin.dto.UserAccountUpdateRequestDto;
import com.project.zipmin.dto.UserAccountUpdateResponseDto;
import com.project.zipmin.dto.WithdrawCreateRequestDto;
import com.project.zipmin.dto.WithdrawReadResponseDto;
import com.project.zipmin.service.FundService;
import com.project.zipmin.service.UserService;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name = "Fund API", description = "후원 관련 API")
public class FundController {

	private final FundService fundService;
	private final UserService userService;
	
	
	
	
	
	// 계좌 작성
	@PostMapping("/users/{id}/account")
	public ResponseEntity<?> createUserAccount(
			@Parameter(description = "계좌 작성 요청 정보") @RequestBody UserAccountCreateRequestDto accountRequestDto) {
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
			throw new ApiException(FundErrorCode.FUND_UNAUTHORIZED);
		}
		accountRequestDto.setUserId(userService.readUserByUsername(authentication.getName()).getId());

		UserAccountCreateResponseDto accountResponseDto = fundService.createAccount(accountRequestDto);

		return ResponseEntity.status(UserAccountSuccessCode.USER_ACCOUNT_CREATE_SUCCESS.getStatus())
				.body(ApiResponse.success(UserAccountSuccessCode.USER_ACCOUNT_CREATE_SUCCESS, accountResponseDto));
	}
	
	
	
	
	
	// 계좌 수정
	@PatchMapping("/users/{id}/account")
	public ResponseEntity<?> updateUserAccount(
			@Parameter(description = "계좌 수정 요청 정보") @RequestBody UserAccountUpdateRequestDto accountRequestDto) {
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
			throw new ApiException(FundErrorCode.FUND_UNAUTHORIZED);
		}

		UserAccountUpdateResponseDto accountResponseDto = fundService.updateAccount(accountRequestDto);

		return ResponseEntity.status(UserAccountSuccessCode.USER_ACCOUNT_UPDATE_SUCCESS.getStatus())
				.body(ApiResponse.success(UserAccountSuccessCode.USER_ACCOUNT_UPDATE_SUCCESS, accountResponseDto));
	}
	
	
	
	
	
	// 사용자 수익 목록 조회
	@GetMapping("/users/{id}/funds")
	public ResponseEntity<?> readUserFundList(
			@Parameter(description = "사용자의 일련번호") @PathVariable Integer id,
			@Parameter(description = "페이지 번호") @RequestParam int page,
			@Parameter(description = "페이지 크기") @RequestParam int size) {
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
			throw new ApiException(FundErrorCode.FUND_UNAUTHORIZED);
		}
		
		Pageable pageable = PageRequest.of(page, size);
		Page<FundReadResponseDto> revenuePage = fundService.readFundPageByUserId(id, pageable);
		
		return ResponseEntity.status(FundSuccessCode.FUND_READ_LIST_SUCCESS.getStatus())
				.body(ApiResponse.success(FundSuccessCode.FUND_READ_LIST_SUCCESS, revenuePage));
	}
	
	
	
	
	
	// 사용자의 수익 총합 조회
	@GetMapping("/users/{id}/funds/sum")
	public ResponseEntity<?> readUserFund(
			@Parameter(description = "사용자의 일련번호") @PathVariable Integer id) {

		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
			throw new ApiException(FundErrorCode.FUND_UNAUTHORIZED);
		}
		
		int sum = fundService.sumFundByUserId(id);
		
		return ResponseEntity.status(FundSuccessCode.FUND_READ_SUM_SUCCESS.getStatus())
				.body(ApiResponse.success(FundSuccessCode.FUND_READ_SUM_SUCCESS, sum));
	}
	
	
	
	
	
	// 수익 작성 (포인트 후원)
	@PostMapping("/funds")
	public ResponseEntity<?> supportRecipe(
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
	

	
	
	
	// 사용자 계좌 조회
	@GetMapping("/users/{id}/account")
	public ResponseEntity<?> readUserAccount(
			@Parameter(description = "사용자의 일련번호") @PathVariable int id) {

		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
			throw new ApiException(FundErrorCode.FUND_UNAUTHORIZED);
		}
		
		UserAccountReadResponseDto accountDto = fundService.readAccountByUserId(id);

		return ResponseEntity.status(UserAccountSuccessCode.USER_ACCOUNT_READ_SUCCESS.getStatus())
				.body(ApiResponse.success(UserAccountSuccessCode.USER_ACCOUNT_READ_SUCCESS, accountDto));
	}
	
	
	
	
	
	// 사용자 출금 목록 조회
	@GetMapping("/users/{id}/withdraw")
	public ResponseEntity<?> readUserWithdrawList(
			@Parameter(description = "사용자의 일련번호") @PathVariable Integer id,
			@Parameter(description = "페이지 번호") @RequestParam int page,
			@Parameter(description = "페이지 크기") @RequestParam int size) {
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
			throw new ApiException(WithdrawErrorCode.WITHDRAW_UNAUTHORIZED_ACCESS);
		}

		Pageable pageable = PageRequest.of(page, size);
		Page<WithdrawReadResponseDto> withdrawPage = fundService.readWithdrawPageByUserId(id, pageable);
		
		return ResponseEntity.status(WithdrawSuccessCode.WITHDRAW_READ_LIST_SUCCESS.getStatus())
				.body(ApiResponse.success(WithdrawSuccessCode.WITHDRAW_READ_LIST_SUCCESS, withdrawPage));
	}
	
	
	
	
	
	// 출금 작성
	@PostMapping("/users/{id}/withdraw")
	public ResponseEntity<?> createUserWithdrawRequest(
			@Parameter(description = "사용자의 일련번호") @PathVariable int id,
			@Parameter(description = "출금 작성 요청 정보") @RequestBody WithdrawCreateRequestDto withdrawRequestDto) {
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
			throw new ApiException(WithdrawErrorCode.WITHDRAW_UNAUTHORIZED_ACCESS);
		}
		withdrawRequestDto.setUserId(userService.readUserByUsername(authentication.getName()).getId());

		WithdrawReadResponseDto withdrawResponseDto = fundService.createWithdraw(withdrawRequestDto);

		return ResponseEntity.status(WithdrawSuccessCode.WITHDRAW_CREATE_SUCCESS.getStatus())
				.body(ApiResponse.success(WithdrawSuccessCode.WITHDRAW_CREATE_SUCCESS, withdrawResponseDto));
	}
	
}
