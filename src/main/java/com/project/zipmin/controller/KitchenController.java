package com.project.zipmin.controller;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.zipmin.api.ApiException;
import com.project.zipmin.api.ApiResponse;
import com.project.zipmin.api.KitchenErrorCode;
import com.project.zipmin.api.KitchenSuccessCode;
import com.project.zipmin.dto.GuideCreateRequestDto;
import com.project.zipmin.dto.GuideCreateResponseDto;
import com.project.zipmin.dto.GuideReadResponseDto;
//import com.project.zipmin.dto.GuideResponseDTO;
import com.project.zipmin.dto.LikeCreateRequestDto;
import com.project.zipmin.dto.LikeCreateResponseDto;
import com.project.zipmin.dto.LikeDeleteRequestDto;
import com.project.zipmin.service.KitchenService;
import com.project.zipmin.service.LikeService;
import com.project.zipmin.service.UserService;

@RestController
public class KitchenController {
	
	@Autowired
	KitchenService kitchenService;
	
	@Autowired
	LikeService likeService;
	
	@Autowired
	UserService userService;

	// 가이드 목록 조회
	@GetMapping("/guides")
	public ResponseEntity<?> listGuide(
			@RequestParam(required = false) String category,
			@RequestParam(required = false) String keyword, 
		    @RequestParam String sort,
		    @RequestParam int page,
		    @RequestParam int size) {
		
		Pageable pageable = PageRequest.of(page, size);
		Page<GuideReadResponseDto> guidePage = null;
		
		guidePage = kitchenService.readGuidePage(category, keyword, sort, pageable);
		
        return ResponseEntity.status(KitchenSuccessCode.KITCHEN_READ_LIST_SUCCESS.getStatus())
                .body(ApiResponse.success(KitchenSuccessCode.KITCHEN_READ_LIST_SUCCESS, guidePage));
	}
	
	

	
	// 가이드 조회
	@GetMapping("/guides/{id}")
	public ResponseEntity<?> viewGuide(@PathVariable int id) {
		GuideReadResponseDto guide = kitchenService.readGuideById(id);	    
	    
	    return ResponseEntity.status(KitchenSuccessCode.KITCHEN_READ_SUCCESS.getStatus())
	            .body(ApiResponse.success(KitchenSuccessCode.KITCHEN_READ_SUCCESS, guide));
	}


	
	
	
	// 새 가이드 등록 (관리자)
	@PostMapping("/guides")
	public ResponseEntity<?> writeGuide(
	        @RequestBody GuideCreateRequestDto guideRequestDto) {
	    
	    // 로그인 여부 확인
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
	        throw new ApiException(KitchenErrorCode.KITCHEN_UNAUTHORIZED_ACCESS);
	    }

	    GuideCreateResponseDto guideResponseDto = kitchenService.createGuide(guideRequestDto);

	    return ResponseEntity.status(KitchenSuccessCode.KITCHEN_CREATE_SUCCESS.getStatus())
	            .body(ApiResponse.success(KitchenSuccessCode.KITCHEN_CREATE_SUCCESS, guideResponseDto));
	}
	
	
	
	
	
	
	// 특정 가이드 수정 (관리자)
	@PatchMapping("/guides/{id}")
	public int editGuide(@PathVariable int id) {
		return 0;
	}
	
	
	
	// 특정 가이드 삭제 (관리자)
	@DeleteMapping("/guides/{id}")
	public int deleteGuide(@PathVariable int id) {
		return 0;
	}
	
	
	
	
	// 특정 가이드 좋아요 (저장)
	@PostMapping("/guides/{id}/likes")
	public ResponseEntity<?> likeGuide(
			@PathVariable("id") int guideId,
			@RequestBody LikeCreateRequestDto likeRequestDto) {
		
		System.err.println("==likeGuide 진입");
		System.err.println(likeRequestDto);
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
			// *** todo: 키친가이드 에러코드로 변경 ***
		    throw new ApiException(KitchenErrorCode.KITCHEN_UNAUTHORIZED_ACCESS);
		}
		
		likeRequestDto.setUserId(userService.readUserByUsername(authentication.getName()).getId());
		LikeCreateResponseDto likeResponseDto = kitchenService.likeGuide(likeRequestDto);
					
		// *** todo: 키친가이드 에러코드로 변경 ***
		return ResponseEntity.status(KitchenSuccessCode.KITCHEN_LIKE_SUCCESS.getStatus())
				.body(ApiResponse.success(KitchenSuccessCode.KITCHEN_LIKE_SUCCESS, likeResponseDto));
	}

	
	
	
	// 특정 가이드 좋아요 취소
	@DeleteMapping("/guides/{id}/likes")
	public ResponseEntity<?> unlikeGuide(
	        @PathVariable("id") int guideId,
	        @RequestBody LikeDeleteRequestDto likeDto) {

	    // 로그인 여부 확인
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
	        throw new ApiException(KitchenErrorCode.KITCHEN_UNAUTHORIZED_ACCESS);
	    }

	    likeDto.setUserId(userService.readUserByUsername(authentication.getName()).getId());

	    // 서비스 호출
	    kitchenService.unlikeGuide(likeDto);

	    return ResponseEntity.status(KitchenSuccessCode.KITCHEN_UNLIKE_SUCCESS.getStatus())
	            .body(ApiResponse.success(KitchenSuccessCode.KITCHEN_UNLIKE_SUCCESS, null));
	}
	
}
