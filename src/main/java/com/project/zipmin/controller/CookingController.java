package com.project.zipmin.controller;

import org.springframework.web.bind.annotation.RestController;

import com.project.zipmin.api.ApiException;
import com.project.zipmin.api.ApiResponse;
import com.project.zipmin.api.CommentErrorCode;
import com.project.zipmin.api.CommentSuccessCode;
import com.project.zipmin.api.CookingErrorCode;
import com.project.zipmin.api.CookingSuccessCode;
import com.project.zipmin.dto.ClassApplyCreateRequestDto;
import com.project.zipmin.dto.ClassApplyCreateResponseDto;
import com.project.zipmin.dto.ClassApplyReadResponseDto;
import com.project.zipmin.dto.ClassReadResponseDto;
import com.project.zipmin.dto.ClassScheduleReadResponseDto;
import com.project.zipmin.dto.ClassTutorReadResponseDto;
import com.project.zipmin.service.CookingService;
import com.project.zipmin.service.UserService;
import com.project.zipmin.swagger.CookingReadSuccessResponse;
import com.project.zipmin.swagger.InternalServerErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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


@RestController
public class CookingController {
	
	@Autowired
	CookingService cookingService;
	
	@Autowired
	UserService userService;
	
	

	// 쿠킹클래스 목록 조회
	@GetMapping("/classes")
	public List<ClassReadResponseDto> listClass() {
		return null;
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
		
		return ResponseEntity.status(CookingSuccessCode.COOKING_READ_SUCCESS.getStatus())
				.body(ApiResponse.success(CookingSuccessCode.COOKING_READ_SUCCESS, classDto));
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

	// 특정 클래스의 일정 추가
	@PostMapping("/classes/{id}/schedules")
	public int writeSchedule(
			@PathVariable int id) {
		return 0;
	}

	// 특정 클래스의 일정 수정
	@PutMapping("/classes/{id}/schedules")
	public int editSchedule(
			@PathVariable int id) {
		// 필요 없을수도있음
		return 0;
	}

	// 특정 클래스의 일정 삭제
	@DeleteMapping("/classes/{id}/schedules")
	public int deleteSchedule(
			@PathVariable int id) {
		// 필요 없을 수도 있음
		return 0;
	}
	
	
	
	// 특정 클래스의 강사 목록 조회
	@GetMapping("/classes/{id}/teacher")
	public List<ClassTutorReadResponseDto> viewTeacher(
			@PathVariable int id) {
		// 필요에 따라선 클래스 조회에서 일정 목록을 한번에 조회하게 될 수도 있음
		return null;
	}

	// 특정 클래스의 강사 추가
	@PostMapping("/classes/{id}/teacher")
	public int writeTeacher(
			@PathVariable int id) {
		return 0;
	}

	// 특정 클래스의 강사 수정
	@PutMapping("/classes/{id}/teacher")
	public int editTeacher(
			@PathVariable int id) {
		// 필요 없을수도있음
		return 0;
	}

	// 특정 클래스의 강사 삭제
	@DeleteMapping("/classes/{id}/teacher")
	public int deleteTeacher(
			@PathVariable int id) {
		// 필요 없을 수도 있음
		return 0;
	}
	
	
	
	// 특정 클래스의 신청서 목록 조회
	@GetMapping("/classes/{id}/applies")
	public List<ClassApplyReadResponseDto> listClassApply(
			@PathVariable int id) {
		return null;
	}
	
	
	
	// 클래스 지원 작성
	@PostMapping("/classes/{id}/applies")
	public ResponseEntity<?> applyClass(
			@PathVariable int id,
			@RequestBody ClassApplyCreateRequestDto applyRequestDto) {
		
		// 인증 여부 확인 (비로그인)
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
		    throw new ApiException(CookingErrorCode.COOKING_UNAUTHORIZED_ACCESS);
		}
		
		// 로그인 정보
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		applyRequestDto.setUserId(userService.readUserByUsername(username).getId());
		
		ClassApplyCreateResponseDto applyResponseDto = cookingService.createApply(applyRequestDto);
		
		return ResponseEntity.status(CookingSuccessCode.COOKING_APPLY_CREATE_SUCCESS.getStatus())
				.body(ApiResponse.success(CookingSuccessCode.COOKING_APPLY_CREATE_SUCCESS, applyResponseDto));
	}
	
	
	
	// 특정 클래스에 참가 신청 취소
	@DeleteMapping("/classes/{id}/applies")
	public int cancelApplyClass(
			@PathVariable int id) {
		return 0;
	}
	
	// 특정 클래스 출석
	@PatchMapping("/classes/{id}/applies/{applyId}")
	public int attendClass(
			@PathVariable int id) {
		return 0;
	}
}
