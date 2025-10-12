package com.project.zipmin.controller;

import org.springframework.web.bind.annotation.RestController;

import com.project.zipmin.api.ApiException;
import com.project.zipmin.api.ApiResponse;
import com.project.zipmin.api.UserErrorCode;
import com.project.zipmin.api.ClassErrorCode;
import com.project.zipmin.api.ClassSuccessCode;
import com.project.zipmin.dto.ClassApplyCreateRequestDto;
import com.project.zipmin.dto.ClassApplyCreateResponseDto;
import com.project.zipmin.dto.ClassApplyDeleteRequestDto;
import com.project.zipmin.dto.ClassApplyReadResponseDto;
import com.project.zipmin.dto.ClassApplyUpdateRequestDto;
import com.project.zipmin.dto.ClassApplyUpdateResponseDto;
import com.project.zipmin.dto.ClassApprovalUpdateRequestDto;
import com.project.zipmin.dto.ClassMyApplyReadResponseDto;
import com.project.zipmin.dto.ClassReadResponseDto;
import com.project.zipmin.dto.UserClassReadResponseDto;
import com.project.zipmin.entity.Role;
import com.project.zipmin.service.CookingService;
import com.project.zipmin.service.UserService;
import com.project.zipmin.swagger.CommentReadListSuccessResponse;
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

@RestController
@RequiredArgsConstructor
@Tag(name = "COOKING API", description = "쿠킹클래스 관련 API")
public class CookingController {
	
	private final CookingService cookingService;
	private final UserService userService;
	
	
	
	
	
	@Operation(
	    summary = "클래스 목록 조회"
	)
	@ApiResponses(value = {
		// 200 CLASS_READ_LIST_SUCCESS
		// 400 CLASS_READ_LIST_FAIL
		// 400 CLASS_INVALID_INPUT
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserInvalidInputResponse.class))),
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
	// 클래스 목록 조회
	@GetMapping("/classes")
	public ResponseEntity<?> listClass(
			@Parameter(description = "카테고리", required = false) @RequestParam(required = false) String category,
			@Parameter(description = "검색어", required = false) @RequestParam(required = false) String keyword,
			@Parameter(description = "승인 상태", required = false) @RequestParam(required = false) String approval,
			@Parameter(description = "진행 상태", required = false) @RequestParam(required = false) String status,
			@Parameter(description = "정렬", required = false) @RequestParam(required = false) String sort,
		    @Parameter(description = "페이지 번호") @RequestParam int page,
		    @Parameter(description = "페이지 크기") @RequestParam int size) {
		
		Pageable pageable = PageRequest.of(page, size);
		Page<ClassReadResponseDto> classPage = cookingService.readClassPage(category, keyword, approval, status, sort, pageable);
		
		return ResponseEntity.status(ClassSuccessCode.CLASS_READ_LIST_SUCCESS.getStatus())
				.body(ApiResponse.success(ClassSuccessCode.CLASS_READ_LIST_SUCCESS, classPage));
	}

	
	
	
	
	@Operation(
	    summary = "클래스 상세 조회"
	)
	@ApiResponses(value = {
		// 200 CLASS_READ_SUCCESS
		// 400 CLASS_TARGET_READ_LIST_FAIL
		// 400 CLASS_SCHEDULE_READ_LIST_FAIL
		// 400 CLASS_TUTOR_READ_FAIL
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserInvalidInputResponse.class))),
		// 404 CLASS_NOT_FOUND
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
	// 클래스 상세 조회
	@GetMapping("/classes/{id}")
	public ResponseEntity<?> viewClass(
			@Parameter(description = "클래스의 일련번호") @PathVariable int id) {
		
		ClassReadResponseDto classDto = cookingService.readClassById(id);
		
		return ResponseEntity.status(ClassSuccessCode.CLASS_READ_SUCCESS.getStatus())
				.body(ApiResponse.success(ClassSuccessCode.CLASS_READ_SUCCESS, classDto));
	}

	
	
	
	
	// 클래스 작성
	@PostMapping("/classes")
	public ResponseEntity<?> writeClass() {
		
		return null;
	}
	
	
	
	
	
	// 클래스 수정
	@PatchMapping("/classes/{id}")
	public ResponseEntity<?> editClass(
			@Parameter(description = "클래스의 일련번호") @PathVariable int id
			// ***** 수정 요청 정보 추가 *****
			) {
		
		// ***** 관리자는 만료되기 전 수정 가능 *****
		// ***** 일반 사용자는 대기 상태인 경우에만 수정 가능 *****
		
		return null;
	}
	
	
	
	
	
	@Operation(
	    summary = "클래스 삭제"
	)
	@ApiResponses(value = {
		// 200 클래스 삭제 성공 CLASS_DELETE_SUCCESS
		// 400 클래스 삭제 실패 CLASS_DELETE_FAIL
		// 400 입력값이 유효하지 않음 CLASS_INVALID_INPUT
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserInvalidInputResponse.class))),
		// 401 로그인되지 않은 사용자 CLASS_UNAUTHRIZED
		// 403 권한 없는 사용자의 접근 CLASS_FORBIDDEN
		// 403 클래스 종료 후 접근 시도 CLASS_ALREADY_ENDED
		// 404 해당 클래스를 찾을 수 없음 CLASS_NOT_FOUND
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
	// 클래스 삭제
	@DeleteMapping("/classes/{id}")
	public ResponseEntity<?> deleteClass(
			@Parameter(description = "클래스의 일련번호") @PathVariable int id) {
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
			throw new ApiException(ClassErrorCode.CLASS_UNAUTHORIZED_ACCESS);
		}
		
		cookingService.deleteClass(id);
		
		return ResponseEntity.status(ClassSuccessCode.CLASS_DELETE_SUCCESS.getStatus())
				.body(ApiResponse.success(ClassSuccessCode.CLASS_DELETE_SUCCESS, null));
	}
	
	
	
	
	
	// 클래스의 신청 목록 조회
	@GetMapping("/classes/{id}/applies")
	public ResponseEntity<?> listClassApply(
			@Parameter(description = "클래스의 일련번호") @PathVariable int id,
			@Parameter(description = "정렬") @RequestParam int sort,
			@Parameter(description = "페이지 번호") @RequestParam int page,
			@Parameter(description = "페이지 크기") @RequestParam int size) {
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
			throw new ApiException(ClassErrorCode.CLASS_UNAUTHORIZED_ACCESS);
		}
		
		Pageable pageable = PageRequest.of(page, size);
		Page<ClassApplyReadResponseDto> applyPage = cookingService.readApplyPageById(id, sort, pageable);
		
		return ResponseEntity.status(ClassSuccessCode.CLASS_APPLY_READ_LIST_SUCCESS.getStatus())
				.body(ApiResponse.success(ClassSuccessCode.CLASS_APPLY_READ_LIST_SUCCESS, applyPage));
	}
	
	
	
	
	
	// 클래스 신청 작성
	@PostMapping("/classes/{id}/applies")
	public ResponseEntity<?> applyClass(
			@Parameter(description = "클래스의 일련번호") @PathVariable int id,
			@Parameter(description = "클래스 신청 작성 요청 정보") @RequestBody ClassApplyCreateRequestDto applyRequestDto) {
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
		    throw new ApiException(ClassErrorCode.CLASS_UNAUTHORIZED_ACCESS);
		}
		
		// 로그인 정보
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		applyRequestDto.setUserId(userService.readUserByUsername(username).getId());
		
		ClassApplyCreateResponseDto applyResponseDto = cookingService.createApply(applyRequestDto);
		
		return ResponseEntity.status(ClassSuccessCode.CLASS_APPLY_CREATE_SUCCESS.getStatus())
				.body(ApiResponse.success(ClassSuccessCode.CLASS_APPLY_CREATE_SUCCESS, applyResponseDto));
	}
	
	
	
	// 특정 클래스에 참가 신청 취소
	@DeleteMapping("/classes/{id}/applies")
	public ResponseEntity<?> cancelApplyClass(
			@PathVariable int id,
			@RequestBody ClassApplyDeleteRequestDto applyDto) {
		
		// 인증 여부 확인 (비로그인)
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
		    throw new ApiException(ClassErrorCode.CLASS_UNAUTHORIZED_ACCESS);
		}
		
		// 로그인 정보
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		applyDto.setUserId(userService.readUserByUsername(username).getId());
		
		cookingService.deleteApply(applyDto);
		
		return ResponseEntity.status(ClassSuccessCode.CLASS_APPLY_DELETE_SUCCESS.getStatus())
				.body(ApiResponse.success(ClassSuccessCode.CLASS_APPLY_DELETE_SUCCESS, null));
	}
	
	
	
	
	
	// 클래스 신청 수정 (출석)
	@PatchMapping("/classes/{classId}/applies/{applyId}")
	public ResponseEntity<?> attendClass(
			@PathVariable int classId,
			@PathVariable int applyId,
			@RequestBody ClassApplyUpdateRequestDto applyRequestDto) {
		
		// 인증 여부 확인 (비로그인)
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
		    throw new ApiException(ClassErrorCode.CLASS_UNAUTHORIZED_ACCESS);
		}
		
		// 로그인 정보
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		int id = userService.readUserByUsername(username).getId();
		
		// 본인 확인 (******** 수정 필요함id도 적절히 수정해야하고 개최자 여부 확인 *******)
//		if (!userService.readUserByUsername(username).getRole().equals(Role.ROLE_ADMIN)) {
//			if (id != applyRequestDto.getUserId()) {
//				throw new ApiException(CookingErrorCode.COOKING_FORBIDDEN);
//			}
//		}
		
		ClassApplyUpdateResponseDto applyResponseDto = cookingService.updateApply(applyRequestDto);
		
		return ResponseEntity.status(ClassSuccessCode.CLASS_APPLY_UPDATE_SUCCESS.getStatus())
				.body(ApiResponse.success(ClassSuccessCode.CLASS_APPLY_UPDATE_SUCCESS, applyResponseDto));
	}
	
	
	
	
	
	
	
	
	
	// CLASS_READ_LIST_SUCCESS
	// CLASS_READ_LIST_FAIL
	// CLASS_INVALID_INPUT
	
	// 사용자가 개설한 쿠킹클래스
	@GetMapping("/users/{id}/classes")
	public ResponseEntity<?> listUserClass(
			@Parameter(description = "사용자의 일련번호") @PathVariable Integer id,
			@Parameter(description = "정렬") @RequestParam String sort,
			@Parameter(description = "조회할 페이지 번호") @RequestParam int page,
			@Parameter(description = "페이지의 항목 수") @RequestParam int size) {
		
		Pageable pageable = PageRequest.of(page, size);
		Page<UserClassReadResponseDto> classPage = cookingService.readClassPageByUserId(id, sort, pageable);
		
		return ResponseEntity.status(ClassSuccessCode.CLASS_READ_LIST_SUCCESS.getStatus())
				.body(ApiResponse.success(ClassSuccessCode.CLASS_READ_LIST_SUCCESS, classPage));
	}
	
	
	
	
	
	
	
	// 사용자가 신청한 쿠킹클래스
	// TODO : 전면 수정 필요
	@GetMapping("/users/{id}/applied-classes")
	public ResponseEntity<?> readUserClassApplyList(
			@Parameter(description = "사용자의 일련번호") @PathVariable Integer id,
			@Parameter(description = "정렬") @RequestParam String sort,
			@Parameter(description = "페이지 번호") @RequestParam int page,
			@Parameter(description = "페이지 크기") @RequestParam int size) {
		
		// 입력값 검증
		if (id == null) {
			throw new ApiException(UserErrorCode.USER_INVALID_INPUT);
		}
		
		// 인증 여부 확인 (비로그인)
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
		    throw new ApiException(UserErrorCode.USER_UNAUTHORIZED_ACCESS);
		}
		
		// 로그인 정보
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		// 본인 확인
		if (!userService.readUserById(id).getRole().equals(Role.ROLE_ADMIN.name())) {
			if (id != userService.readUserByUsername(username).getId()) {
				throw new ApiException(UserErrorCode.USER_FORBIDDEN);
			}
		}
		
		Pageable pageable = PageRequest.of(page, size);
		Page<ClassMyApplyReadResponseDto> applyPage = cookingService.readApplyClassPageByUserId(id, sort, pageable);
		
		return ResponseEntity.status(ClassSuccessCode.CLASS_READ_LIST_SUCCESS.getStatus())
				.body(ApiResponse.success(ClassSuccessCode.CLASS_READ_LIST_SUCCESS, applyPage));
	}
	
	
	
	
	
	
	
	
	
	
}
