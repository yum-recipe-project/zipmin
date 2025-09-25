package com.project.zipmin.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.zipmin.api.ApiException;
import com.project.zipmin.api.ApiResponse;
import com.project.zipmin.api.MemoSuccessCode;
import com.project.zipmin.api.UserErrorCode;
import com.project.zipmin.dto.MemoReadResponseDto;
import com.project.zipmin.entity.Role;
import com.project.zipmin.service.MemoService;
import com.project.zipmin.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemoController {
	
	private final UserService userService;
	private final MemoService memoService;	
	
	
	//테스트
	@GetMapping("/users/{id}/memos")
	public ResponseEntity<?> listMemo(
			@PathVariable Integer id,
			@RequestParam int page,
			@RequestParam int size) {
		System.err.println("메모 컨트롤러 진입, 사용자 아이디:" + id);
		
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
		Page<MemoReadResponseDto> memoPage = memoService.readMemoPage(id, pageable);
		System.err.println(memoPage);
		
        return ResponseEntity.status(MemoSuccessCode.MEMO_READ_LIST_SUCCESS.getStatus())
                .body(ApiResponse.success(MemoSuccessCode.MEMO_READ_LIST_SUCCESS, memoPage));
	}
	

	
	
	

	
	
}
