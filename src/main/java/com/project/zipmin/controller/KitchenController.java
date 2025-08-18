package com.project.zipmin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.zipmin.api.ApiResponse;
import com.project.zipmin.api.KitchenSuccessCode;
import com.project.zipmin.dto.GuideReadResponseDto;
import com.project.zipmin.dto.GuideResponseDTO;
import com.project.zipmin.service.KitchenService;
import com.project.zipmin.service.LikeService;

import io.swagger.v3.oas.annotations.Parameter;

@RestController
public class KitchenController {
	
	@Autowired
	KitchenService kitchenService;
	
	@Autowired
	LikeService likeService;
	

	// 가이드 목록 조회
	@GetMapping("/guides")
	public ResponseEntity<?> listGuide(
			// 키워드 추가할 것
			@RequestParam(required = false) String category,
			@RequestParam(required = false) String keyword, 
		    @RequestParam String sort,
		    @RequestParam int page,
		    @RequestParam int size) {
		
		Pageable pageable = PageRequest.of(page, size);
		Page<GuideReadResponseDto> guidePage = null;
		
//		guidePage = kitchenService.readGuidePage(category, sort, pageable);
		guidePage = kitchenService.readGuidePage(category, keyword, sort, pageable);
		
        return ResponseEntity.status(KitchenSuccessCode.KITCHEN_READ_LIST_SUCCESS.getStatus())
                .body(ApiResponse.success(KitchenSuccessCode.KITCHEN_READ_LIST_SUCCESS, guidePage));
	}
	
	

	
	// 가이드 조회
	@GetMapping("/guides/{id}")
	public ResponseEntity<?> viewGuide(@PathVariable int id) {
	    GuideReadResponseDto guide = kitchenService.readGuideById(id);
	    int likeCount = likeService.selectLikeCountByTable("guide", id);
	    
	    GuideResponseDTO response = new GuideResponseDTO(guide, likeCount);
	    
	    return ResponseEntity.status(KitchenSuccessCode.KITCHEN_READ_SUCCESS.getStatus())
	            .body(ApiResponse.success(KitchenSuccessCode.KITCHEN_READ_SUCCESS, response));
	}

	
	
	
	// 새 가이드 등록 (관리자)
	@PostMapping("/guides")
	public int writeGuide() {
		return 0;
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
	public int likeGuide(
			@PathVariable int guideId) {
		return 0;
	}
	
	
	
	
}
