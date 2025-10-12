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
import com.project.zipmin.api.FundSuccessCode;
import com.project.zipmin.api.UserErrorCode;
import com.project.zipmin.api.UserSuccessCode;
import com.project.zipmin.dto.UserAccountCreateRequestDto;
import com.project.zipmin.dto.UserAccountReadResponseDto;
import com.project.zipmin.dto.UserAccountUpdateRequestDto;
import com.project.zipmin.dto.UserRevenueReadResponseDto;
import com.project.zipmin.dto.WithdrawCreateRequestDto;
import com.project.zipmin.dto.WithdrawReadResponseDto;
import com.project.zipmin.service.RevenueService;
import com.project.zipmin.service.WithdrawService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class RevenueController {
	
	private final RevenueService revenueService;
	private final WithdrawService withdrawService;
	
	
	// 사용자 총 수익 조회
	@GetMapping("/users/{id}/revenue/total")
	public ResponseEntity<Integer> readUserTotalRevenue(@PathVariable Integer id) {
	    // 입력값 검증
	    if (id == null || id <= 0) {
	        throw new ApiException(UserErrorCode.USER_INVALID_INPUT);
	    }

	    int totalRevenue = revenueService.getUserTotalRevenue(id);
	    return ResponseEntity.ok(totalRevenue);
	}


	// 사용자 수익 목록 조회
	@GetMapping("/users/{id}/revenue")
	public ResponseEntity<?> readUserRevenueList(
	        @PathVariable Integer id,
	        @RequestParam int page,
	        @RequestParam int size) {
		// 입력값 검증
		if (id == null) {
			throw new ApiException(UserErrorCode.USER_INVALID_INPUT);
		}
		
		Pageable pageable = PageRequest.of(page, size);
		Page<UserRevenueReadResponseDto> revenuePage = revenueService.readUserRevenuePageById(id, pageable);
		
	    return ResponseEntity.status(FundSuccessCode.FUND_HISTORY_READ_SUCCESS.getStatus())
	            .body(ApiResponse.success(FundSuccessCode.FUND_HISTORY_READ_SUCCESS, revenuePage));
	}
	
	
	
	// 사용자 출금 계좌 조회
	@GetMapping("/users/{id}/account")
	public ResponseEntity<?> readUserAccount(
	        @PathVariable int id) {

	    UserAccountReadResponseDto accountDto = revenueService.readUserAccountById(id);

	    return ResponseEntity.status(UserSuccessCode.USER_READ_ACCOUNT_SUCCESS.getStatus())
	            .body(ApiResponse.success(UserSuccessCode.USER_READ_ACCOUNT_SUCCESS, accountDto));
	}
	
	
	
	// 사용자 출금 계좌 등록
    @PostMapping("/users/{id}/account")
    public ResponseEntity<?> createUserAccount(@RequestBody UserAccountCreateRequestDto accountRequestDto) {
        // 로그인 여부 확인
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            throw new ApiException(UserErrorCode.USER_UNAUTHORIZED_ACCESS);
        }

        // 계좌 등록 서비스 호출
        UserAccountReadResponseDto accountResponseDto = revenueService.createUserAccount(accountRequestDto);

        return ResponseEntity.status(UserSuccessCode.USER_CREATE_ACCOUNT_SUCCESS.getStatus())
                .body(ApiResponse.success(UserSuccessCode.USER_CREATE_ACCOUNT_SUCCESS, accountResponseDto));
    }
    
    
    
    // 사용자 출금 계좌 수정
    @PatchMapping("/users/{id}/account")
    public ResponseEntity<?> updateUserAccount(@RequestBody UserAccountUpdateRequestDto accountRequestDto) {
    	
        // 로그인 체크
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            throw new ApiException(UserErrorCode.USER_UNAUTHORIZED_ACCESS);
        }

        UserAccountReadResponseDto accountResponseDto = revenueService.updateUserAccount(accountRequestDto);

        return ResponseEntity.status(UserSuccessCode.USER_UPDATE_ACCOUNT_SUCCESS.getStatus())
                .body(ApiResponse.success(UserSuccessCode.USER_UPDATE_ACCOUNT_SUCCESS, accountResponseDto));
    }
    
    
    
    // 사용자 출금 내역 목록 조회
    @GetMapping("/users/{id}/withdraw")
    public ResponseEntity<?> readUserWithdrawList(
            @PathVariable Integer id,
            @RequestParam int page,
            @RequestParam int size) {
    	System.err.println("입력값: " + id + " page: " + page + "size: " + size);

        // 입력값 검증
        if (id == null || id <= 0) {
            throw new ApiException(UserErrorCode.USER_INVALID_INPUT);
        }

        Pageable pageable = PageRequest.of(page, size);

        // 서비스 호출
        Page<WithdrawReadResponseDto> withdrawPage = withdrawService.readUserWithdrawPageById(id, pageable);
        System.err.println("서비스 호출 완료: " + withdrawPage);
        
        return ResponseEntity.status(UserSuccessCode.USER_WITHDRAW_HISTORY_READ_SUCCESS.getStatus())
                .body(ApiResponse.success(UserSuccessCode.USER_WITHDRAW_HISTORY_READ_SUCCESS, withdrawPage));
    }



    
    
    
    // 사용자 포인트 출금 신청
    @PostMapping("/users/{id}/withdraw")
    public ResponseEntity<?> createUserWithdrawRequest(
            @PathVariable int id,
            @RequestBody WithdrawCreateRequestDto withdrawRequestDto) {
    	
        // 로그인 체크
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            throw new ApiException(UserErrorCode.USER_UNAUTHORIZED_ACCESS);
        }
        
        if (id <= 0 || withdrawRequestDto == null) {
            throw new ApiException(UserErrorCode.USER_INVALID_INPUT);
        }

        // 출금 신청 처리
        WithdrawReadResponseDto withdrawResponseDto = withdrawService.createWithdrawRequest(id, withdrawRequestDto);

        return ResponseEntity.status(UserSuccessCode.USER_WITHDRAW_REQUEST_SUCCESS.getStatus())
                .body(ApiResponse.success(UserSuccessCode.USER_WITHDRAW_REQUEST_SUCCESS, withdrawResponseDto));
    }

	
	
}
