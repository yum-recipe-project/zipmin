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
import com.project.zipmin.swagger.FundInvalidInputFailResponse;
import com.project.zipmin.swagger.FundPointExceedFailResponse;
import com.project.zipmin.swagger.FundSupportSuccessResponse;
import com.project.zipmin.swagger.FundUnauthorizedFailResponse;
import com.project.zipmin.swagger.InternalServerErrorResponse;
import com.project.zipmin.swagger.RecipeNotFoundResponse;
import com.project.zipmin.swagger.UserNotFoundResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
	
	
	
	
	
	@Operation(
	    summary = "레시피 후원"
	)
	@ApiResponses(value = {
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	        responseCode = "200",
	        description = "후원 성공",
	        content = @Content(
	            mediaType = "application/json",
	            schema = @Schema(implementation = FundSupportSuccessResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	        responseCode = "400",
	        description = "후원 실패 - 입력값이 유효하지 않음",
	        content = @Content(
	            mediaType = "application/json",
	            schema = @Schema(implementation = FundInvalidInputFailResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	        responseCode = "401",
	        description = "후원 실패 - 로그인되지 않은 사용자",
	        content = @Content(
	            mediaType = "application/json",
	            schema = @Schema(implementation = FundUnauthorizedFailResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	        responseCode = "402",
	        description = "후원 실패 - 보유 포인트를 초과한 후원",
	        content = @Content(
	            mediaType = "application/json",
	            schema = @Schema(implementation = FundPointExceedFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 사용자를 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserNotFoundResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 레시피를 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = RecipeNotFoundResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	        responseCode = "500",
	        description = "서버 내부 오류",
	        content = @Content(
	            mediaType = "application/json",
	            schema = @Schema(implementation = InternalServerErrorResponse.class)
	        )
	    )
	})
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
