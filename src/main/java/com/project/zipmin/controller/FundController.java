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
import com.project.zipmin.api.UserAccountErrorCode;
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
import com.project.zipmin.dto.WithdrawUpdateRequestDto;
import com.project.zipmin.dto.WithdrawUpdateResponseDto;
import com.project.zipmin.service.FundService;
import com.project.zipmin.service.UserService;
import com.project.zipmin.swagger.InternalServerErrorResponse;
import com.project.zipmin.swagger.UserInvalidInputResponse;
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

	private final FundService fundService;
	private final UserService userService;
	
	
	
	
	
	@Operation(
	    summary = "사용자 계좌 상세 조회"
	)
	@ApiResponses(value = {
		// 200 USER_ACCOUNT_READ_SUCCESS 계좌 조회 성공
		// 400 USER_ACCOUNT_READ_FAIL 계좌 조회 실패
		// 400 USER_ACCOUNT_INVALID_INPUT 입력값이 유효하지 않음
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserInvalidInputResponse.class))),
		// 401 USER_ACCOUNT_UNAUTHORIZED_ACCESS 로그인되지 않은 사용자
		// 403 USER_ACCOUNT_FORBIDDEN 권한 없는 사용자의 접근
		// 404 USER_ACCOUNT_NOT_FOUND 해당 계좌를 찾을 수 없음
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
	// 사용자 계좌 상세 조회
	@GetMapping("/users/{id}/account")
	public ResponseEntity<?> readUserAccount(
			@Parameter(description = "사용자의 일련번호") @PathVariable int id) {

		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
			throw new ApiException(UserAccountErrorCode.USER_ACCOUNT_UNAUTHORIZED_ACCESS);
		}
		
		UserAccountReadResponseDto accountDto = fundService.readAccountByUserId(id);

		return ResponseEntity.status(UserAccountSuccessCode.USER_ACCOUNT_READ_SUCCESS.getStatus())
				.body(ApiResponse.success(UserAccountSuccessCode.USER_ACCOUNT_READ_SUCCESS, accountDto));
	}
	
	
	
	
	
	@Operation(
	    summary = "계좌 작성"
	)
	@ApiResponses(value = {
		// 201 USER_ACCOUNT_CREATE_SUCCESS 계좌 작성 성공
		// 400 USER_ACCOUNT_CREATE_FAIL 계좌 작성 실패
		// 400 USER_ACCOUNT_INVALID_INPUT 입력값이 유효하지 않음
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserInvalidInputResponse.class))),
		// 401 USER_ACCOUNT_UNAUTHORIZED_ACCESS 로그인되지 않은 사용자
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
			throw new ApiException(UserAccountErrorCode.USER_ACCOUNT_UNAUTHORIZED_ACCESS);
		}
		accountRequestDto.setUserId(userService.readUserByUsername(authentication.getName()).getId());

		UserAccountCreateResponseDto accountResponseDto = fundService.createAccount(accountRequestDto);

		return ResponseEntity.status(UserAccountSuccessCode.USER_ACCOUNT_CREATE_SUCCESS.getStatus())
				.body(ApiResponse.success(UserAccountSuccessCode.USER_ACCOUNT_CREATE_SUCCESS, accountResponseDto));
	}
	
	
	
	
	
	@Operation(
	    summary = "계좌 수정"
	)
	@ApiResponses(value = {
		// 200 USER_ACCOUNT_UPDATE_SUCCESS 계좌 수정 성공
		// 400 USER_ACCOUNT_UPDATE_FAIL 계좌 수정 실패
		// 400 USER_ACCOUNT_INVALID_INPUT 입력값이 유효하지 않음
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserInvalidInputResponse.class))),
		// 401 USER_ACCOUNT_UNAUTHORIZED_ACCESS 로그인되지 않은 사용자
		// 403 USER_ACCOUNT_FORBIDDEN 권한 없는 사용자의 접근
		// 404 USER_ACCOUNT_NOT_FOUND 해당 계좌를 찾을 수 없음
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
			throw new ApiException(UserAccountErrorCode.USER_ACCOUNT_UNAUTHORIZED_ACCESS);
		}
		accountRequestDto.setUserId(userService.readUserByUsername(authentication.getName()).getId());

		UserAccountUpdateResponseDto accountResponseDto = fundService.updateAccount(accountRequestDto);

		return ResponseEntity.status(UserAccountSuccessCode.USER_ACCOUNT_UPDATE_SUCCESS.getStatus())
				.body(ApiResponse.success(UserAccountSuccessCode.USER_ACCOUNT_UPDATE_SUCCESS, accountResponseDto));
	}
	
	
	
	
	
	@Operation(
	    summary = "사용자의 후원 목록 조회"
	)
	@ApiResponses(value = {
		// 200 FUND_READ_LIST_SUCCESS 후원 목록 조회 성공
		// 400 FUND_READ_LIST_FAIL 후원 목록 조회 실패
		// 400 FUND_INVALID_INPUT 입력값이 유효하지 않음
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserInvalidInputResponse.class))),
		// 401 FUND_UNAUTHORIZED_ACCESS 로그인되지 않은 사용자
		// 403 FUND_FORBIDDEN 권한 없는 사용자의 접근
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
			throw new ApiException(FundErrorCode.FUND_UNAUTHORIZED_ACCESS);
		}
		
		Pageable pageable = PageRequest.of(page, size);
		Page<FundReadResponseDto> revenuePage = fundService.readFundPageByUserId(id, pageable);
		
		return ResponseEntity.status(FundSuccessCode.FUND_READ_LIST_SUCCESS.getStatus())
				.body(ApiResponse.success(FundSuccessCode.FUND_READ_LIST_SUCCESS, revenuePage));
	}
	
	
	
	
	
	@Operation(
	    summary = "사용자의 후원 조회"
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
		// 401 FUND_UNAUTHORIZED_ACCESS 로그인되지 않은 사용자
		// 403 FUND_FORBIDDEN 권한 없는 사용자의 접근
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
			throw new ApiException(FundErrorCode.FUND_UNAUTHORIZED_ACCESS);
		}
		
		int sum = fundService.sumFundByUserId(id);
		
		return ResponseEntity.status(FundSuccessCode.FUND_READ_SUM_SUCCESS.getStatus())
				.body(ApiResponse.success(FundSuccessCode.FUND_READ_SUM_SUCCESS, sum));
	}
	
	
	
	
	@Operation(
	    summary = "후원 작성"
	)
	@ApiResponses(value = {
		// TODO : 사용자 갱신 과정에서 발생할 수 있는 에러코드 추가
		
		// 201 FUND_CREATE_SUCCESS 후원 작성 성공
		// 400 FUND_CREATE_FAIL 후원 작성 실패
		// 400 FUND_INVALID_INPUT 입력값이 유효하지 않음
		// 400 FUND_INVALID_POINT 입력값이 유효하지 않음
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserInvalidInputResponse.class))),
		// 401 FUND_UNAUTHORIZED_ACCESS 로그인되지 않은 사용자
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
	// 후원 작성 (포인트 후원)
	@PostMapping("/funds")
	public ResponseEntity<?> supportRecipe(
			@Parameter(description = "후원 요청 정보") @RequestBody FundCreateRequestDto fundRequestDto) {

		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
			throw new ApiException(FundErrorCode.FUND_UNAUTHORIZED_ACCESS);
		}
		fundRequestDto.setFunderId(userService.readUserByUsername(authentication.getName()).getId());
		
		FundCreateResponseDto fundResponseDto = fundService.createFund(fundRequestDto);

		return ResponseEntity.status(FundSuccessCode.FUND_CREATE_SUCCESS.getStatus())
				.body(ApiResponse.success(FundSuccessCode.FUND_CREATE_SUCCESS, fundResponseDto));
	}
	


	

	@Operation(
	    summary = "사용자의 출금 목록 조회"
	)
	@ApiResponses(value = {
		// 200 WITHDRAW_READ_LIST_SUCCESS 출금 목록 조회 성공
		// 400 WITHDRAW_READ_LIST_FAIL 출금 목록 조회 실패
		// 400 WITHDRAW_INVALID_INPUT 입력값이 유효하지 않음
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserInvalidInputResponse.class))),
		// 401 WITHDRAW_UNAUTHORIZED_ACCESS 로그인되지 않은 사용자
		// 403 WITHDRAW_FORBIDDEN 권한 없는 사용자의 접근
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
			throw new ApiException(WithdrawErrorCode.WITHDRAW_UNAUTHORIZED_ACCESS);
		}

		Pageable pageable = PageRequest.of(page, size);
		Page<WithdrawReadResponseDto> withdrawPage = fundService.readWithdrawPageByUserId(id, pageable);
		
		return ResponseEntity.status(WithdrawSuccessCode.WITHDRAW_READ_LIST_SUCCESS.getStatus())
				.body(ApiResponse.success(WithdrawSuccessCode.WITHDRAW_READ_LIST_SUCCESS, withdrawPage));
	}
	
	
	
	
	
	@Operation(
	    summary = "출금 작성"
	)
	@ApiResponses(value = {
		// 201 WITHDRAW_CREATE_SUCCESS 출금 작성 성공
		// 400 WITHDRAW_CREATE_FAIL 출금 작성 실패
		// 400 WITHDRAW_INVALID_INPUT 입력값이 유효하지 않음
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserInvalidInputResponse.class))),
		// 401 WITHDRAW_UNAUTHORIZED_ACCESS 로그인되지 않은 사용자
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
			throw new ApiException(WithdrawErrorCode.WITHDRAW_UNAUTHORIZED_ACCESS);
		}
		withdrawRequestDto.setUserId(userService.readUserByUsername(authentication.getName()).getId());

		WithdrawReadResponseDto withdrawResponseDto = fundService.createWithdraw(withdrawRequestDto);

		return ResponseEntity.status(WithdrawSuccessCode.WITHDRAW_CREATE_SUCCESS.getStatus())
				.body(ApiResponse.success(WithdrawSuccessCode.WITHDRAW_CREATE_SUCCESS, withdrawResponseDto));
	}
	
	
	
	
	
	// WITHDRAW_UPDATE_SUCCESS
	// WITHDRAW_UPDATE_FAIL
	// WITHDRAW_INVALID_INPUT
	// USER_INVALID_INPUT
	// WITHDRAW_UNAUTHORIZED_ACCESS
	// WITHDRAW_FORBIDDEN
	// WITHDRAW_NOT_FOUND
	// USER_NOT_FOUND
	
	// 출금 수정
	@PatchMapping("/withdraws/{id}")
	public ResponseEntity<?> updateWithdraw(
			@Parameter(description = "출금의 일련번호") @PathVariable int id,
			@Parameter(description = "출금 수정 요청 정보") @RequestBody WithdrawUpdateRequestDto withdrawRequestDto) {
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
			throw new ApiException(WithdrawErrorCode.WITHDRAW_UNAUTHORIZED_ACCESS);
		}
		
		WithdrawUpdateResponseDto withdrawResponseDto = fundService.updateWithdraw(withdrawRequestDto);
		
		return ResponseEntity.status(WithdrawSuccessCode.WITHDRAW_UPDATE_SUCCESS.getStatus())
				.body(ApiResponse.success(WithdrawSuccessCode.WITHDRAW_UPDATE_SUCCESS, withdrawResponseDto));
		
	}
	
	
	
	
	// WITHDRAW_DELETE_SUCCESS
	// WITHDRAW_DELETE_FAIL
	// WITHDRAW_INVALID_INPUT
	// USER_INVALID_INPUT
	// WITHDRAW_UNAUTHORIZED_ACCESS
	// WITHDRAW_FORBIDDEN
	// WITHDRAW_NOT_FOUND
	// USER_NOT_FOUND

	// 출금 삭제
	@DeleteMapping("/withdraws/{id}")
	public ResponseEntity<?> deleteWithdraw(
			@Parameter(description = "출금의 일련번호") @PathVariable int id) {
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
			throw new ApiException(WithdrawErrorCode.WITHDRAW_UNAUTHORIZED_ACCESS);
		}
		
		fundService.deleteWithdraw(id);
		
		return ResponseEntity.status(WithdrawSuccessCode.WITHDRAW_DELETE_SUCCESS.getStatus())
				.body(ApiResponse.success(WithdrawSuccessCode.WITHDRAW_DELETE_SUCCESS, null));
	}
	
	
	
	
	
	
	
	
}
