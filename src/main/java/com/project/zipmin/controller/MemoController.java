package com.project.zipmin.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.zipmin.api.ApiException;
import com.project.zipmin.api.ApiResponse;
import com.project.zipmin.api.MemoErrorCode;
import com.project.zipmin.api.MemoSuccessCode;
import com.project.zipmin.api.UserErrorCode;
import com.project.zipmin.dto.MemoCreateRequestDto;
import com.project.zipmin.dto.MemoCreateResponseDto;
import com.project.zipmin.dto.MemoReadResponseDto;
import com.project.zipmin.dto.MemoUpdateRequestDto;
import com.project.zipmin.dto.MemoUpdateResponseDto;
import com.project.zipmin.entity.Role;
import com.project.zipmin.service.MemoService;
import com.project.zipmin.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MemoController {
	
	private final UserService userService;
	private final MemoService memoService;	
	
	
	// 장보기 메모 목록 조회
	@GetMapping("/users/{id}/memos")
	public ResponseEntity<?> listAllMemo(@PathVariable Integer id) {

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

	    List<MemoReadResponseDto> memoList = memoService.readMemoList(id);

	    return ResponseEntity.status(MemoSuccessCode.MEMO_READ_LIST_SUCCESS.getStatus())
	            .body(ApiResponse.success(MemoSuccessCode.MEMO_READ_LIST_SUCCESS, memoList));
	}

	

	
	
	// 장보기 메모 입력
	@PostMapping("/users/{userId}/memos")
	public ResponseEntity<?> writeMemo(
			@PathVariable int userId,
	        @RequestBody MemoCreateRequestDto memoRequestDto) {
	    
	    // 로그인 여부 확인
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
	        throw new ApiException(MemoErrorCode.MEMO_UNAUTHORIZED_ACCESS);
	    }

	    MemoCreateResponseDto memoResponseDto = memoService.createMemo(memoRequestDto);
	    
	    return ResponseEntity.status(MemoSuccessCode.MEMO_CREATE_SUCCESS.getStatus())
	            .body(ApiResponse.success(MemoSuccessCode.MEMO_CREATE_SUCCESS, memoResponseDto));
	}

	
	
	
	
	// 장보기 메모 수정
	@PatchMapping("/users/{userId}/memos/{memoId}")
	public ResponseEntity<?> updateMemo(
	        @PathVariable int memoId,
	        @RequestBody MemoUpdateRequestDto memoRequestDto) {
		
	    // 인증 여부 확인 (비로그인)
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
	        throw new ApiException(MemoErrorCode.MEMO_UNAUTHORIZED_ACCESS);
	    }
	    
	    // 메모 수정 서비스 호출
	    MemoUpdateResponseDto memoResponseDto = memoService.updateMemo(memoId, memoRequestDto);
	    
	    return ResponseEntity.status(MemoSuccessCode.MEMO_UPDATE_SUCCESS.getStatus())
	            .body(ApiResponse.success(MemoSuccessCode.MEMO_UPDATE_SUCCESS, memoResponseDto));
	}

	
}
