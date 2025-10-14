package com.project.zipmin.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.zipmin.api.ApiException;
import com.project.zipmin.api.ApiResponse;
import com.project.zipmin.api.ClassErrorCode;
import com.project.zipmin.api.ClassSuccessCode;
import com.project.zipmin.api.UserErrorCode;
import com.project.zipmin.dto.ClassApplyCreateRequestDto;
import com.project.zipmin.dto.ClassApplyCreateResponseDto;
import com.project.zipmin.dto.ClassApplyReadResponseDto;
import com.project.zipmin.dto.ClassApplyStatusUpdateRequestDto;
import com.project.zipmin.dto.ClassApplyUpdateResponseDto;
import com.project.zipmin.dto.ClassCreateRequestDto;
import com.project.zipmin.dto.ClassReadResponseDto;
import com.project.zipmin.dto.UserAppliedClassResponseDto;
import com.project.zipmin.dto.UserClassReadResponseDto;
import com.project.zipmin.service.CookingService;
import com.project.zipmin.service.UserService;
import com.project.zipmin.swagger.ClassAlreadyEndedResponse;
import com.project.zipmin.swagger.ClassApplyCreateFailResponse;
import com.project.zipmin.swagger.ClassApplyCreateSuccessResponse;
import com.project.zipmin.swagger.ClassApplyDuplicateResponse;
import com.project.zipmin.swagger.ClassApplyInvalidInputResponse;
import com.project.zipmin.swagger.ClassApplyReadListFailResponse;
import com.project.zipmin.swagger.ClassApplyUnableResponse;
import com.project.zipmin.swagger.ClassApplyUpdateSuccessResponse;
import com.project.zipmin.swagger.ClassDeleteFailResponse;
import com.project.zipmin.swagger.ClassDeleteSuccessResponse;
import com.project.zipmin.swagger.ClassForbiddenResponse;
import com.project.zipmin.swagger.ClassInvalidInputResponse;
import com.project.zipmin.swagger.ClassNotFoundResponse;
import com.project.zipmin.swagger.ClassReadListFailResponse;
import com.project.zipmin.swagger.ClassReadSuccessResponse;
import com.project.zipmin.swagger.ClassScheduleReadListFailResponse;
import com.project.zipmin.swagger.ClassTargetReadListFailResponse;
import com.project.zipmin.swagger.ClassTutorReadListFailResponse;
import com.project.zipmin.swagger.ClassUnauthorizedAccessResponse;
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
@Tag(name = "COOKING API", description = "쿠킹클래스 관련 API")
public class CookingController {
	
	private final CookingService cookingService;
	private final UserService userService;
	
	
	
	
	
	@Operation(
	    summary = "클래스 목록 조회"
	)
	@ApiResponses(value = {
		// 200 CLASS_READ_LIST_SUCCESS
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "클래스 목록 조회 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = ClassReadListFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = ClassInvalidInputResponse.class))),
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
	// js/cooking/list-class.js
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
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "200",
				description = "클래스 조회 성공",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = ClassReadSuccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "클래스 교육대상 목록 조회 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = ClassTargetReadListFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "클래스 교육일정 목록 조회 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = ClassScheduleReadListFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "클래스 강사 목록 조회 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = ClassTutorReadListFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = ClassInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 클래스를 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = ClassNotFoundResponse.class))),
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
	// js/cooking/view-class.js
	@GetMapping("/classes/{id}")
	public ResponseEntity<?> viewClass(
			@Parameter(description = "클래스의 일련번호") @PathVariable int id) {
		
		ClassReadResponseDto classDto = cookingService.readClassById(id);
		
		return ResponseEntity.status(ClassSuccessCode.CLASS_READ_SUCCESS.getStatus())
				.body(ApiResponse.success(ClassSuccessCode.CLASS_READ_SUCCESS, classDto));
	}

	
	
	
	
	// 클래스 작성
	@PostMapping(value = "/classes", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> writeClass(
			@RequestPart ClassCreateRequestDto createRequestDto,
	        @RequestPart(required = false) MultipartFile classImage,
	        @RequestPart(required = false) List<MultipartFile> tutorImages) {
	    // 로그인 여부 확인
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
	        throw new ApiException(ClassErrorCode.CLASS_UNAUTHORIZED_ACCESS);
	    }
	    
	    // 로그인 사용자 정보
	    String username = SecurityContextHolder.getContext().getAuthentication().getName();
	    createRequestDto.setUserId(userService.readUserByUsername(username).getId());
	    
	    try {
	    	// 서비스에 DTO + 파일 전달
	        cookingService.createClass(createRequestDto, classImage, tutorImages);
	    	System.err.println("3. 쿠킹클래스 서비스 완료");
	    } catch (Exception e) {
	        throw new ApiException(ClassErrorCode.CLASS_CREATE_FAIL);
	    }
	    
	    
	    return ResponseEntity.status(ClassSuccessCode.CLASS_CREATE_SUCCESS.getStatus())
	            .body(ApiResponse.success(ClassSuccessCode.CLASS_CREATE_SUCCESS, null));
	}
	
	
	
	
	
	// 클래스 수정
	@PatchMapping("/classes/{id}")
	public ResponseEntity<?> editClass(
			@Parameter(description = "클래스의 일련번호") @PathVariable int id) {
		
		return null;
	}
	
	
	
	
	
	@Operation(
	    summary = "클래스 삭제"
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "200",
				description = "클래스 삭제 성공",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = ClassDeleteSuccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "클래스 삭제 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = ClassDeleteFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = ClassInvalidInputResponse.class))),
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
						schema = @Schema(implementation = ClassUnauthorizedAccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "401",
				description = "권한 없는 사용자의 접근",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = ClassForbiddenResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "401",
				description = "클래스 종료 후 접근 시도",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = ClassAlreadyEndedResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 클래스를 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = ClassNotFoundResponse.class))),
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
	// js/admin/list-class.js
	// js/mypage/classes.js
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
	
	
	
	
	
	@Operation(
	    summary = "클래스 신청 목록 조회"
	)
	@ApiResponses(value = {
		// 200 CLASS_APPLY_READ_LIST_SUCCESS
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "클래스 신청 목록 조회 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = ClassApplyReadListFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = ClassApplyInvalidInputResponse.class))),
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
						schema = @Schema(implementation = ClassUnauthorizedAccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "401",
				description = "권한 없는 사용자의 접근",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = ClassForbiddenResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 클래스를 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = ClassNotFoundResponse.class))),
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
	// 클래스 신청 목록 조회
	// js/mypage/application.js
	@GetMapping("/classes/{id}/applies")
	public ResponseEntity<?> listClassApply(
			@Parameter(description = "클래스의 일련번호") @PathVariable int id,
			@Parameter(description = "선정 상태") @RequestParam String selected,
			@Parameter(description = "페이지 번호") @RequestParam int page,
			@Parameter(description = "페이지 크기") @RequestParam int size) {
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
			throw new ApiException(ClassErrorCode.CLASS_UNAUTHORIZED_ACCESS);
		}
		
		Pageable pageable = PageRequest.of(page, size);
		Page<ClassApplyReadResponseDto> applyPage = cookingService.readApplyPageById(id, selected, pageable);
		
		return ResponseEntity.status(ClassSuccessCode.CLASS_APPLY_READ_LIST_SUCCESS.getStatus())
				.body(ApiResponse.success(ClassSuccessCode.CLASS_APPLY_READ_LIST_SUCCESS, applyPage));
	}
	
	
	
	
	
	@Operation(
	    summary = "클래스 신청 작성"
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "200",
				description = "클래스 신청 작성 성공",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = ClassApplyCreateSuccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "클래스 신청 작성 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = ClassApplyCreateFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = ClassApplyInvalidInputResponse.class))),
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
						schema = @Schema(implementation = ClassUnauthorizedAccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "401",
				description = "클래스 종료 후 접근 시도",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = ClassAlreadyEndedResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "401",
				description = "클래스 신청 작성 불가",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = ClassApplyUnableResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 클래스를 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = ClassNotFoundResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 사용자를 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserNotFoundResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "409",
				description = "클래스 신청 중복 작성 시도",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = ClassApplyDuplicateResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "500",
				description = "서버 내부 오류",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = InternalServerErrorResponse.class)))
		
	})
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
		applyRequestDto.setUserId(userService.readUserByUsername(authentication.getName()).getId());
		
		ClassApplyCreateResponseDto applyResponseDto = cookingService.createApply(applyRequestDto);
		
		return ResponseEntity.status(ClassSuccessCode.CLASS_APPLY_CREATE_SUCCESS.getStatus())
				.body(ApiResponse.success(ClassSuccessCode.CLASS_APPLY_CREATE_SUCCESS, applyResponseDto));
	}
	
	
	
	
	
	// 특정 클래스에 참가 신청 취소
	@DeleteMapping("/classes/{classId}/applies/{applyId}")
	public ResponseEntity<?> cancelApplyClass(
			@PathVariable int classId,
			@PathVariable int applyId) {
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
		    throw new ApiException(ClassErrorCode.CLASS_UNAUTHORIZED_ACCESS);
		}
		
		cookingService.deleteApply(classId, applyId);
		
		return ResponseEntity.status(ClassSuccessCode.CLASS_APPLY_DELETE_SUCCESS.getStatus())
				.body(ApiResponse.success(ClassSuccessCode.CLASS_APPLY_DELETE_SUCCESS, null));
	}
	
	
	
	
	
	
	
	// CLASS_UNAUTHORIZED_ACCESS
		// CLASS_APPLY_INVALID_INPUT
		// CLASS_NOT_FOUND
		// CLASS_FORBIDDEN
		// CLASS_APPLY_NOT_FOUND
		// CLASS_ALREADY_ENDED
		// CLASS_APPLY_INVALID_INPUT
		// CLASS_ALREADY_ENDED
		// CLASS_APPLY_UPDATE_FAIL
		// USER
	
	

	@Operation(
	    summary = "클래스 신청 상태 수정"
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "200",
				description = "클래스 신청 수정 성공",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = ClassApplyUpdateSuccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "클래스 신청 작성 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = ClassApplyCreateFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = ClassApplyInvalidInputResponse.class))),
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
						schema = @Schema(implementation = ClassUnauthorizedAccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "401",
				description = "클래스 종료 후 접근 시도",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = ClassAlreadyEndedResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "401",
				description = "클래스 신청 작성 불가",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = ClassApplyUnableResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 클래스를 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = ClassNotFoundResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 사용자를 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserNotFoundResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "409",
				description = "클래스 신청 중복 작성 시도",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = ClassApplyDuplicateResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "500",
				description = "서버 내부 오류",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = InternalServerErrorResponse.class)))
		
	})
	// 클래스 신청 상태 수정
	@PatchMapping("/classes/{classId}/applies/{applyId}/status")
	public ResponseEntity<?> attendClass(
			@PathVariable int classId,
			@PathVariable int applyId,
			@RequestBody ClassApplyStatusUpdateRequestDto applyRequestDto) {
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
			throw new ApiException(ClassErrorCode.CLASS_UNAUTHORIZED_ACCESS);
		}
		
		ClassApplyUpdateResponseDto applyResponseDto = cookingService.updateApplySelected(applyRequestDto);
		
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
			@Parameter(description = "진행 상태") @RequestParam String status,
			@Parameter(description = "조회할 페이지 번호") @RequestParam int page,
			@Parameter(description = "페이지의 항목 수") @RequestParam int size) {
		
		Pageable pageable = PageRequest.of(page, size);
		Page<UserClassReadResponseDto> classPage = cookingService.readClassPageByUserId(id, status, pageable);
		
		return ResponseEntity.status(ClassSuccessCode.CLASS_READ_LIST_SUCCESS.getStatus())
				.body(ApiResponse.success(ClassSuccessCode.CLASS_READ_LIST_SUCCESS, classPage));
	}
	
	
	
	
	
	// 사용자가 신청한 쿠킹클래스
	@GetMapping("/users/{id}/applied-classes")
	public ResponseEntity<?> readUserClassApplyList(
			@Parameter(description = "사용자의 일련번호") @PathVariable Integer id,
			@Parameter(description = "진행 상태") @RequestParam String status,
			@Parameter(description = "페이지 번호") @RequestParam int page,
			@Parameter(description = "페이지 크기") @RequestParam int size) {
		
		// 입력값 검증
		if (id == null) {
			throw new ApiException(UserErrorCode.USER_INVALID_INPUT);
		}
		
		Pageable pageable = PageRequest.of(page, size);
		Page<UserAppliedClassResponseDto> applyPage = cookingService.readAppliedClassPageByUserId(id, status, pageable);
		
		return ResponseEntity.status(ClassSuccessCode.CLASS_READ_LIST_SUCCESS.getStatus())
				.body(ApiResponse.success(ClassSuccessCode.CLASS_READ_LIST_SUCCESS, applyPage));
	}
	
	
	
	
	
	// 결석 수
	// TODO : 수정 필요
	@GetMapping("/users/{id}/attend-classes/count")
	public ResponseEntity<?> countUserClassAttend(
			@Parameter(description = "사용자의 일련번호") @PathVariable Integer id) {
		
		// 입력값 검증
		if (id == null) {
			throw new ApiException(UserErrorCode.USER_INVALID_INPUT);
		}
		
		int count = (int) cookingService.countClassAttend(id);
		
		return ResponseEntity.status(ClassSuccessCode.CLASS_COUNT_ATTEND_SUCCESS.getStatus())
				.body(ApiResponse.success(ClassSuccessCode.CLASS_COUNT_ATTEND_SUCCESS, count));
	}
	
	
	
	
	
	
	
}
