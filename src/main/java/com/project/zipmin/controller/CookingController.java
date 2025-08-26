package com.project.zipmin.controller;

import org.springframework.web.bind.annotation.RestController;

import com.project.zipmin.api.ApiException;
import com.project.zipmin.api.ApiResponse;
import com.project.zipmin.api.CommentErrorCode;
import com.project.zipmin.api.CommentSuccessCode;
import com.project.zipmin.api.ClassErrorCode;
import com.project.zipmin.api.ClassSuccessCode;
import com.project.zipmin.dto.ClassApplyCreateRequestDto;
import com.project.zipmin.dto.ClassApplyCreateResponseDto;
import com.project.zipmin.dto.ClassApplyDeleteRequestDto;
import com.project.zipmin.dto.ClassApplyReadResponseDto;
import com.project.zipmin.dto.ClassApplyUpdateRequestDto;
import com.project.zipmin.dto.ClassApplyUpdateResponseDto;
import com.project.zipmin.dto.ClassReadResponseDto;
import com.project.zipmin.dto.ClassScheduleReadResponseDto;
import com.project.zipmin.dto.ClassTutorReadResponseDto;
import com.project.zipmin.dto.GuideReadResponseDto;
import com.project.zipmin.entity.Role;
import com.project.zipmin.service.CookingService;
import com.project.zipmin.service.UserService;
import com.project.zipmin.swagger.CookingReadSuccessResponse;
import com.project.zipmin.swagger.InternalServerErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@Tag(name = "Cooking API", description = "쿠킹클래스 관련 API")
public class CookingController {
	
	@Autowired
	CookingService cookingService;
	
	@Autowired
	UserService userService;
	
	

	// 쿠킹클래스 목록 조회
	@GetMapping("/classes")
	public ResponseEntity<?> listClass(
			@Parameter(description = "카테고리", required = false) @RequestParam(required = false) String category,
			@Parameter(description = "검색어", required = false) @RequestParam(required = false) String keyword,
			@Parameter(description = "상태", required = false) @RequestParam(required = false) String status,
			@Parameter(description = "정렬", required = false) @RequestParam(required = false) String sort,
		    @Parameter(description = "페이지 번호") @RequestParam int page,
		    @Parameter(description = "페이지 크기") @RequestParam int size) {
		
		Pageable pageable = PageRequest.of(page, size);
		Page<ClassReadResponseDto> classPage = cookingService.readClassPage(category, keyword, status, sort, pageable);
		
		return ResponseEntity.status(ClassSuccessCode.CLASS_READ_LIST_SUCCESS.getStatus())
				.body(ApiResponse.success(ClassSuccessCode.CLASS_READ_LIST_SUCCESS, classPage));
	}

	
	
	// 쿠킹클래스 상세 조회
	// API 문서 추가 필요 ***
	@Operation(
	    summary = "쿠킹클래스 상세 조회"
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "200",
				description = "쿠킹클래스 조회 성공",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = CookingReadSuccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "500",
				description = "서버 내부 오류",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = InternalServerErrorResponse.class)))
	})
	@GetMapping("/classes/{id}")
	public ResponseEntity<?> viewClass(
			@PathVariable int id) {
		
		ClassReadResponseDto classDto = cookingService.readClassById(id);
		
		return ResponseEntity.status(ClassSuccessCode.CLASS_READ_SUCCESS.getStatus())
				.body(ApiResponse.success(ClassSuccessCode.CLASS_READ_SUCCESS, classDto));
	}

	
	
	// 새 클래스 신청
	@PostMapping("/classes")
	public int writeClass() {
		return 0;
	}

	// 클래스 승인 및 거절 (관리자)
	@PatchMapping("/classes/{id}/status")
	public int updateClassStatus(
			@PathVariable int id) {
		return 0;
	}

	// 특정 클래스 수정 (관리자)
	@PatchMapping("/classes/{id}")
	public int editClass(
			@PathVariable int id) {
		// 구현 안할듯
		return 0;
	}

	// 특정 클래스 삭제 (관리자)
	@DeleteMapping("/classes/{id}")
	public int deleteGuide(
			@PathVariable int id) {
		return 0;
	}
	
	
	
	// 특정 클래스의 일정 목록 조회
	@GetMapping("/classes/{id}/schedules")
	public List<ClassScheduleReadResponseDto> viewSchedule(
			@PathVariable int id) {
		// 필요에 따라선 클래스 조회에서 일정 목록을 한번에 조회하게 될 수도 있음
		return null;
	}

	
	
	
	// 클래스의 신청 목록 조회
	@GetMapping("/classes/{id}/applies")
	public ResponseEntity<?> listClassApply(
			@PathVariable int id,
			@RequestParam int sort,
		    @RequestParam int page,
		    @RequestParam int size) {
		
		Pageable pageable = PageRequest.of(page, size);
		Page<ClassApplyReadResponseDto> applyPage = null;
		
		// 로그인 여부도 잘 확인해야함
		
		
		applyPage = cookingService.readApplyPageById(id, sort, pageable);
		
		return ResponseEntity.status(ClassSuccessCode.CLASS_APPLY_READ_LIST_SUCCESS.getStatus())
				.body(ApiResponse.success(ClassSuccessCode.CLASS_APPLY_READ_LIST_SUCCESS, applyPage));
	}
	
	
	
	// 클래스 신청 작성
	@PostMapping("/classes/{id}/applies")
	public ResponseEntity<?> applyClass(
			@PathVariable int id,
			@RequestBody ClassApplyCreateRequestDto applyRequestDto) {
		
		// 인증 여부 확인 (비로그인)
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
