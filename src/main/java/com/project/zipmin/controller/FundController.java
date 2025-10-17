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
import com.project.zipmin.dto.FundSupportRequestDto;
import com.project.zipmin.dto.FundSupportResponseDto;
import com.project.zipmin.service.FundService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/funds")
@RequiredArgsConstructor
@Tag(name = "FUND API", description = "후원 관련 API")
public class FundController {
	
	private final FundService fundService;
	
	
	// 특정 후원 조회 (관리자)
	@GetMapping("/{fundId}")
	public int viewSupport(
			@PathVariable("fundId") String fundId) {
		// 안쓸거같기도?
		return 0;
	}
	
	
	
	// 특정 후원 취소 (관리자)
	@DeleteMapping("/{fundId}")
	public int deleteSupport(
			@PathVariable("fundId") String fundId) {
		return 0;
	}
	
	
	
	
	
	// 특정 사용자가 다른 사용자에게 후원
	@PostMapping("/{funderId}/supports/{fundeeId}")
    public ResponseEntity<?> supportRecipe(
            @PathVariable int funderId,
            @PathVariable int fundeeId,
            @RequestBody FundSupportRequestDto fundSupportRequestDto) {

		// 로그인 여부 확인
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
	        throw new ApiException(FundErrorCode.FUND_UNAUTHORIZED);
	    }
	    
	    FundSupportResponseDto response = fundService.support(funderId, fundeeId, fundSupportRequestDto);

	    return ResponseEntity.status(FundSuccessCode.FUND_COMPLETE_SUCCESS.getStatus())
	            .body(ApiResponse.success(FundSuccessCode.FUND_COMPLETE_SUCCESS, response));
    }

	
	
	
}
