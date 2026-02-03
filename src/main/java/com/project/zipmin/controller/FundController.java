package com.project.zipmin.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import com.project.zipmin.api.PaymentErrorCode;
import com.project.zipmin.api.PaymentSuccessCode;
import com.project.zipmin.api.UserAccountErrorCode;
import com.project.zipmin.api.UserAccountSuccessCode;
import com.project.zipmin.api.WithdrawErrorCode;
import com.project.zipmin.api.WithdrawSuccessCode;
import com.project.zipmin.dto.UserAccountCreateRequestDto;
import com.project.zipmin.dto.UserAccountCreateResponseDto;
import com.project.zipmin.dto.UserAccountReadResponseDto;
import com.project.zipmin.dto.UserAccountUpdateRequestDto;
import com.project.zipmin.dto.UserAccountUpdateResponseDto;
import com.project.zipmin.dto.fund.FundCreateRequestDto;
import com.project.zipmin.dto.fund.FundCreateResponseDto;
import com.project.zipmin.dto.fund.FundReadResponseDto;
import com.project.zipmin.dto.fund.PaymentCreateRequestDto;
import com.project.zipmin.dto.fund.PaymentCreateResponseDto;
import com.project.zipmin.dto.fund.WithdrawCreateRequestDto;
import com.project.zipmin.dto.fund.WithdrawReadResponseDto;
import com.project.zipmin.dto.fund.WithdrawUpdateRequestDto;
import com.project.zipmin.dto.fund.WithdrawUpdateResponseDto;
import com.project.zipmin.service.FundService;
import com.project.zipmin.swagger.InternalServerErrorResponse;
import com.project.zipmin.swagger.fund.FundCreateFailResponse;
import com.project.zipmin.swagger.fund.FundCreateSuccessResponse;
import com.project.zipmin.swagger.fund.FundForbiddenResponse;
import com.project.zipmin.swagger.fund.FundInvalidInputFailResponse;
import com.project.zipmin.swagger.fund.FundInvalidPointFailResponse;
import com.project.zipmin.swagger.fund.FundReadListFailResponse;
import com.project.zipmin.swagger.fund.FundReadListSuccessResponse;
import com.project.zipmin.swagger.fund.FundUnauthorizedResponse;
import com.project.zipmin.swagger.fund.PaymentCreateFailResponse;
import com.project.zipmin.swagger.fund.PaymentCreateSuccessResponse;
import com.project.zipmin.swagger.fund.PaymentForbiddenResponse;
import com.project.zipmin.swagger.fund.PaymentInvalidInputResponse;
import com.project.zipmin.swagger.fund.PaymentInvalidPointResponse;
import com.project.zipmin.swagger.fund.PaymentInvalidStatusResponse;
import com.project.zipmin.swagger.fund.PaymentLookupFailResponse;
import com.project.zipmin.swagger.fund.PaymentUnauthorizedResponse;
import com.project.zipmin.swagger.fund.UserAccountCreateFailResponse;
import com.project.zipmin.swagger.fund.UserAccountCreateSuccessResponse;
import com.project.zipmin.swagger.fund.UserAccountForbiddenResponse;
import com.project.zipmin.swagger.fund.UserAccountInvalidInputResponse;
import com.project.zipmin.swagger.fund.UserAccountNotFoundResponse;
import com.project.zipmin.swagger.fund.UserAccountReadSuccessResponse;
import com.project.zipmin.swagger.fund.UserAccountUnauthorizedResponse;
import com.project.zipmin.swagger.fund.UserAccountUpdateFailResponse;
import com.project.zipmin.swagger.fund.UserAccountUpdateSuccessResponse;
import com.project.zipmin.swagger.fund.WithdrawCreateFailResponse;
import com.project.zipmin.swagger.fund.WithdrawCreateSuccessResponse;
import com.project.zipmin.swagger.fund.WithdrawDeleteFailResponse;
import com.project.zipmin.swagger.fund.WithdrawDeleteSuccessResponse;
import com.project.zipmin.swagger.fund.WithdrawForbiddenResponse;
import com.project.zipmin.swagger.fund.WithdrawInvalidInputResponse;
import com.project.zipmin.swagger.fund.WithdrawInvalidPointResponse;
import com.project.zipmin.swagger.fund.WithdrawNotFoundResponse;
import com.project.zipmin.swagger.fund.WithdrawReadListFailResponse;
import com.project.zipmin.swagger.fund.WithdrawReadListSuccessResponse;
import com.project.zipmin.swagger.fund.WithdrawUnauthorizedResponse;
import com.project.zipmin.swagger.fund.WithdrawUpdateFailResponse;
import com.project.zipmin.swagger.fund.WithdrawUpdateSuccessResponse;
import com.project.zipmin.swagger.user.UserInvalidInputResponse;
import com.project.zipmin.swagger.user.UserNotFoundResponse;

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

	private final FundService fundService;
	
	
	
	
	
	// 사용자의 계좌 상세 조회
	@Operation(
	    summary = "사용자의 계좌 상세 조회"
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "200",
				description = "계좌 조회 성공",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserAccountReadSuccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserAccountInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "401",
				description = "로그인되지 않은 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserAccountUnauthorizedResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "403",
				description = "권한 없는 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserAccountForbiddenResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 계좌를 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserAccountNotFoundResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 사용자를 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserNotFoundResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "500",
				description = "서버 내부 오류",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = InternalServerErrorResponse.class)))
		
	})
	// 사용자의 계좌 상세 조회
	@GetMapping("/users/{id}/account")
	public ResponseEntity<?> readUserAccount(
			@Parameter(description = "사용자의 일련번호") @PathVariable int id) {

		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
			throw new ApiException(UserAccountErrorCode.USER_ACCOUNT_UNAUTHORIZED);
		}
		
		// 계좌 상세 조회
		UserAccountReadResponseDto accountDto = fundService.readAccountByUserId(id);

		return ResponseEntity.status(UserAccountSuccessCode.USER_ACCOUNT_READ_SUCCESS.getStatus())
				.body(ApiResponse.success(UserAccountSuccessCode.USER_ACCOUNT_READ_SUCCESS, accountDto));
	}
	
	
	
	
	
	// 계좌 작성
	@Operation(
	    summary = "계좌 작성"
	)
	@ApiResponses(value = {
		// 400 USER_ACCOUNT_INVALID_INPUT 입력값이 유효하지 않음
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "200",
				description = "계좌 작성 성공",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserAccountCreateSuccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "계좌 작성 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserAccountCreateFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserAccountInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "401",
				description = "로그인되지 않은 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserAccountUnauthorizedResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "403",
				description = "권한 없는 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserAccountForbiddenResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 사용자를 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserNotFoundResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "500",
				description = "서버 내부 오류",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = InternalServerErrorResponse.class)))
		
	})
	// 계좌 작성
	@PostMapping("/users/{id}/account")
	public ResponseEntity<?> createUserAccount(
			@Parameter(description = "계좌 작성 요청 정보") @RequestBody UserAccountCreateRequestDto accountRequestDto) {
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
			throw new ApiException(UserAccountErrorCode.USER_ACCOUNT_UNAUTHORIZED);
		}

		UserAccountCreateResponseDto accountResponseDto = fundService.createAccount(accountRequestDto);

		return ResponseEntity.status(UserAccountSuccessCode.USER_ACCOUNT_CREATE_SUCCESS.getStatus())
				.body(ApiResponse.success(UserAccountSuccessCode.USER_ACCOUNT_CREATE_SUCCESS, accountResponseDto));
	}
	
	
	
	
	
	// 계좌 수정
	@Operation(
	    summary = "계좌 수정"
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "200",
				description = "계좌 수정 성공",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserAccountUpdateSuccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "계좌 수정 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserAccountUpdateFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserAccountInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "401",
				description = "로그인되지 않은 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserAccountUnauthorizedResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "403",
				description = "권한 없는 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserAccountForbiddenResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 계좌를 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserAccountNotFoundResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 사용자를 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserNotFoundResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "500",
				description = "서버 내부 오류",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = InternalServerErrorResponse.class)))
		
	})
	// 계좌 수정
	@PatchMapping("/users/{id}/account")
	public ResponseEntity<?> updateUserAccount(
			@Parameter(description = "계좌 수정 요청 정보") @RequestBody UserAccountUpdateRequestDto accountRequestDto) {
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
			throw new ApiException(UserAccountErrorCode.USER_ACCOUNT_UNAUTHORIZED);
		}

		// 계좌 수정
		UserAccountUpdateResponseDto accountResponseDto = fundService.updateAccount(accountRequestDto);

		return ResponseEntity.status(UserAccountSuccessCode.USER_ACCOUNT_UPDATE_SUCCESS.getStatus())
				.body(ApiResponse.success(UserAccountSuccessCode.USER_ACCOUNT_UPDATE_SUCCESS, accountResponseDto));
	}
	
	
	
	
	
	// 결제 작성 (포인트 충전)
	@Operation(
	    summary = "결졔 작성"
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "200",
				description = "결제 작성 성공",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = PaymentCreateSuccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "결제 작성 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = PaymentCreateFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "포트원 결제 내역 조회 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = PaymentLookupFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = PaymentInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "포인트가 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = PaymentInvalidPointResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "상태가 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = PaymentInvalidStatusResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "401",
				description = "로그인되지 않은 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = PaymentUnauthorizedResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "403",
				description = "권한 없는 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = PaymentForbiddenResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 사용자를 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserNotFoundResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "500",
				description = "서버 내부 오류",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = InternalServerErrorResponse.class)))
		
	})
	@PostMapping("/users/{id}/point")
	public ResponseEntity<?> createUserPoint(
			@Parameter(description = "사용자의 일련번호") @PathVariable int id,
			@Parameter(description = "결제 작성 요청 정보") @RequestBody PaymentCreateRequestDto paymentRequestDto) {
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
			throw new ApiException(PaymentErrorCode.PAYMENT_UNAUTHORIZED);
		}
		
		// 결제 작성
		PaymentCreateResponseDto paymentResponseDto = fundService.createPayment(paymentRequestDto);
		
		return ResponseEntity.status(PaymentSuccessCode.PAYMENT_CREATE_SUCCESS.getStatus())
				.body(ApiResponse.success(PaymentSuccessCode.PAYMENT_CREATE_SUCCESS, paymentResponseDto));
		
	}
	
	
	
	
	
	// 사용자의 후원 목록 조회
	@Operation(
	    summary = "사용자의 후원 목록 조회"
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "200",
				description = "후원 목록 조회 성공",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FundReadListSuccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "후원 목록 조회 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FundReadListFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FundInvalidInputFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "401",
				description = "로그인되지 않은 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FundUnauthorizedResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "403",
				description = "권한 없는 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FundForbiddenResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 사용자를 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserNotFoundResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "500",
				description = "서버 내부 오류",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = InternalServerErrorResponse.class)))
		
	})
	// 사용자의 후원 목록 조회
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
		
		// 후원 목록 조회
		Pageable pageable = PageRequest.of(page, size);
		Page<FundReadResponseDto> revenuePage = fundService.readFundPageByUserId(id, pageable);
		
		return ResponseEntity.status(FundSuccessCode.FUND_READ_LIST_SUCCESS.getStatus())
				.body(ApiResponse.success(FundSuccessCode.FUND_READ_LIST_SUCCESS, revenuePage));
	}
	
	
	
	
	
	// TODO : 사용자의 후원 총합 조회
	@Operation(
	    summary = "사용자의 후원 총합 조회"
	)
	@ApiResponses(value = {
		// 200 FUND_READ_SUM_SUCCESS 후원 합계 조회 실패
		// 400 FUND_READ_SUM_FAIL 후원 합계 조회 실패
		// 400 FUND_INVALID_INPUT 입력값이 유효하지 않음
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "401",
				description = "로그인되지 않은 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FundUnauthorizedResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "403",
				description = "권한 없는 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FundForbiddenResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 사용자를 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserNotFoundResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "500",
				description = "서버 내부 오류",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = InternalServerErrorResponse.class)))
		
	})
	// 사용자의 후원 총합 조회
	@GetMapping("/users/{id}/funds/sum")
	public ResponseEntity<?> readUserFund(
			@Parameter(description = "사용자의 일련번호") @PathVariable Integer id) {

		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
			throw new ApiException(FundErrorCode.FUND_UNAUTHORIZED);
		}
		
		int sum = fundService.sumFundByUserId(id);
		
		return ResponseEntity.status(FundSuccessCode.FUND_SUM_SUCCESS.getStatus())
				.body(ApiResponse.success(FundSuccessCode.FUND_SUM_SUCCESS, sum));
	}
	
	
	
	
	
	// 후원 작성
	@Operation(
	    summary = "후원 작성"
	)
	@ApiResponses(value = {
		// TODO : 사용자 갱신 과정에서 발생할 수 있는 에러코드 추가
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "201",
				description = "후원 작성 성공",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FundCreateSuccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "후원 작성 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FundCreateFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FundInvalidInputFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "포인트가 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FundInvalidPointFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "401",
				description = "로그인되지 않은 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FundUnauthorizedResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "403",
				description = "권한 없는 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FundForbiddenResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 사용자를 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserNotFoundResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "500",
				description = "서버 내부 오류",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = InternalServerErrorResponse.class)))
		
	})
	// 후원 작성
	@PostMapping("/funds")
	public ResponseEntity<?> supportRecipe(
			@Parameter(description = "후원 요청 정보") @RequestBody FundCreateRequestDto fundRequestDto) {

		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
			throw new ApiException(FundErrorCode.FUND_UNAUTHORIZED);
		}
		
		// 후원 작성
		FundCreateResponseDto fundResponseDto = fundService.createFund(fundRequestDto);

		return ResponseEntity.status(FundSuccessCode.FUND_CREATE_SUCCESS.getStatus())
				.body(ApiResponse.success(FundSuccessCode.FUND_CREATE_SUCCESS, fundResponseDto));
	}
	


	

	// 사용자의 출금 목록 조회
	@Operation(
	    summary = "사용자의 출금 목록 조회"
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "200",
				description = "출금 목록 조회 성공",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = WithdrawReadListSuccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "출금 목록 조회 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = WithdrawReadListFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = WithdrawInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "401",
				description = "로그인되지 않은 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = WithdrawUnauthorizedResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "403",
				description = "권한 없는 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = WithdrawForbiddenResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 사용자를 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserNotFoundResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "500",
				description = "서버 내부 오류",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = InternalServerErrorResponse.class)))
		
	})
	// 사용자 출금 목록 조회
	@GetMapping("/users/{id}/withdraws")
	public ResponseEntity<?> readUserWithdrawList(
			@Parameter(description = "사용자의 일련번호") @PathVariable Integer id,
			@Parameter(description = "페이지 번호") @RequestParam int page,
			@Parameter(description = "페이지 크기") @RequestParam int size) {
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
			throw new ApiException(WithdrawErrorCode.WITHDRAW_UNAUTHORIZED);
		}

		Pageable pageable = PageRequest.of(page, size);
		Page<WithdrawReadResponseDto> withdrawPage = fundService.readWithdrawPageByUserId(id, pageable);
		
		return ResponseEntity.status(WithdrawSuccessCode.WITHDRAW_READ_LIST_SUCCESS.getStatus())
				.body(ApiResponse.success(WithdrawSuccessCode.WITHDRAW_READ_LIST_SUCCESS, withdrawPage));
	}
	
	
	
	
	
	// 출금 작성
	@Operation(
	    summary = "출금 작성"
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "200",
				description = "출금 작성 성공",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = WithdrawCreateSuccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "출금 작성 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = WithdrawCreateFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = WithdrawInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "포인트가 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = WithdrawInvalidPointResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "401",
				description = "로그인되지 않은 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = WithdrawUnauthorizedResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "403",
				description = "권한 없는 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = WithdrawForbiddenResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 사용자를 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserNotFoundResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "500",
				description = "서버 내부 오류",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = InternalServerErrorResponse.class)))
		
	})
	// 출금 작성
	@PostMapping("/withdraws")
	public ResponseEntity<?> createWithdraw(
			@Parameter(description = "출금 작성 요청 정보") @RequestBody WithdrawCreateRequestDto withdrawRequestDto) {
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
			throw new ApiException(WithdrawErrorCode.WITHDRAW_UNAUTHORIZED);
		}
		
		// 출금 작성
		WithdrawReadResponseDto withdrawResponseDto = fundService.createWithdraw(withdrawRequestDto);

		return ResponseEntity.status(WithdrawSuccessCode.WITHDRAW_CREATE_SUCCESS.getStatus())
				.body(ApiResponse.success(WithdrawSuccessCode.WITHDRAW_CREATE_SUCCESS, withdrawResponseDto));
	}
	
	
	
	
	
	// 출금 수정
	@Operation(
	    summary = "출금 수정"
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "200",
				description = "출금 수정 성공",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = WithdrawUpdateSuccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "출금 수정 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = WithdrawUpdateFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = WithdrawInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "401",
				description = "로그인되지 않은 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = WithdrawUnauthorizedResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "403",
				description = "권한 없는 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = WithdrawForbiddenResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 출금을 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = WithdrawNotFoundResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 사용자를 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserNotFoundResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "500",
				description = "서버 내부 오류",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = InternalServerErrorResponse.class)))
		
	})
	// 출금 수정
	@PatchMapping("/withdraws/{id}")
	public ResponseEntity<?> updateWithdraw(
			@Parameter(description = "출금의 일련번호") @PathVariable int id,
			@Parameter(description = "출금 수정 요청 정보") @RequestBody WithdrawUpdateRequestDto withdrawRequestDto) {
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
			throw new ApiException(WithdrawErrorCode.WITHDRAW_UNAUTHORIZED);
		}
		
		// 출금 수정
		WithdrawUpdateResponseDto withdrawResponseDto = fundService.updateWithdraw(withdrawRequestDto);
		
		return ResponseEntity.status(WithdrawSuccessCode.WITHDRAW_UPDATE_SUCCESS.getStatus())
				.body(ApiResponse.success(WithdrawSuccessCode.WITHDRAW_UPDATE_SUCCESS, withdrawResponseDto));
		
	}
	
	
	
	
	
	// 출금 삭제
	@Operation(
	    summary = "출금 삭제"
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "200",
				description = "출금 삭제 성공",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = WithdrawDeleteSuccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "출금 삭제 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = WithdrawDeleteFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = WithdrawInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "401",
				description = "로그인되지 않은 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = WithdrawUnauthorizedResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "403",
				description = "권한 없는 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = WithdrawForbiddenResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 출금을 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = WithdrawNotFoundResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 사용자를 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserNotFoundResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "500",
				description = "서버 내부 오류",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = InternalServerErrorResponse.class)))
		
	})
	// 출금 삭제
	@DeleteMapping("/withdraws/{id}")
	public ResponseEntity<?> deleteWithdraw(
			@Parameter(description = "출금의 일련번호") @PathVariable int id) {
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
			throw new ApiException(WithdrawErrorCode.WITHDRAW_UNAUTHORIZED);
		}
		
		// 출금 삭제
		fundService.deleteWithdraw(id);
		
		return ResponseEntity.status(WithdrawSuccessCode.WITHDRAW_DELETE_SUCCESS.getStatus())
				.body(ApiResponse.success(WithdrawSuccessCode.WITHDRAW_DELETE_SUCCESS, null));
	}

}
