package com.project.zipmin.controller;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.zipmin.api.ApiException;
import com.project.zipmin.api.ApiResponse;
import com.project.zipmin.api.FridgeErrorCode;
import com.project.zipmin.api.FridgeSuccessCode;
import com.project.zipmin.dto.FridgeCreateRequestDto;
import com.project.zipmin.dto.FridgeCreateResponseDto;
import com.project.zipmin.dto.FridgeReadResponseDto;
import com.project.zipmin.dto.FridgeUpdateRequestDto;
import com.project.zipmin.dto.FridgeUpdateResponseDto;
import com.project.zipmin.dto.RecipeReadResponseDto;
import com.project.zipmin.dto.UserFridgeReadResponseDto;
import com.project.zipmin.service.FridgeService;
import com.project.zipmin.service.UserService;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Fridge API", description = "냉장고 관련 API")
public class FridgeController {
	
	@Autowired
	FridgeService fridgeService;
	@Autowired
	UserService userService;
	
	
	
	
	// 200 FRIDGE_READ_LIST_SUCCESS
	// 400 FRIDGE_READ_LIST_FAIL
	// 400 FRIDGE_INVALID_INPUT
	// 500 INTENER_SERVER_ERROR
	
	// 냉장고 목록 조회
	@GetMapping("/fridges")
	public ResponseEntity<?> readFridge(
			@Parameter(description = "카테고리", required = false) @RequestParam(required = false) String category,
			@Parameter(description = "검색어", required = false) @RequestParam(required = false) String keyword,
			@Parameter(description = "정렬", required = false) @RequestParam(required = false) String sort,
		    @Parameter(description = "페이지 번호") @RequestParam int page,
		    @Parameter(description = "페이지 크기") @RequestParam int size) {

		Pageable pageable = PageRequest.of(page, size);
		Page<FridgeReadResponseDto> fridgePage = fridgeService.readFridgePage(category, keyword, sort, pageable);

		return ResponseEntity.status(FridgeSuccessCode.FRIDGE_READ_LIST_SUCCESS.getStatus())
				.body(ApiResponse.success(FridgeSuccessCode.FRIDGE_READ_LIST_SUCCESS, fridgePage));
	}
	
	
	
	
	
	// 201 FRIDGE_CREATE_SUCCESS
	// 400 FRIDGE_CREATE_FAIL
	// 400 FRIDGE_INVALID_INPUT
	// 400 USER_INVALID_INPUT
	// 401 FRIDGE_UNAUTHORIZED_ACCESS
	// 404 USER_NOT_FOUND
	// 500
	
	// 냉장고 작성
	@PostMapping("/fridges")
	public ResponseEntity<?> createFridge(
			@Parameter(description = "냉장고 작성 요청 정보") @RequestBody FridgeCreateRequestDto fridgeRequestDto) {
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
		    throw new ApiException(FridgeErrorCode.FRIDGE_UNAUTHORIZED_ACCESS);
		}
		fridgeRequestDto.setUserId(userService.readUserByUsername(authentication.getName()).getId());
		
		FridgeCreateResponseDto fridgeResponseDto = fridgeService.createFridge(fridgeRequestDto);
		
		return ResponseEntity.status(FridgeSuccessCode.FRIDGE_CREATE_SUCCESS.getStatus())
				.body(ApiResponse.success(FridgeSuccessCode.FRIDGE_CREATE_SUCCESS, fridgeResponseDto));
	}
	
	
	
	
	
	// 냉장고 수정
	@PatchMapping("/fridges/{id}")
	public ResponseEntity<?> updateFridge(
			@Parameter(description = "냉장고의 일련번호") @PathVariable int id,
			@Parameter(description = "냉장고 수정 요청 정보") @RequestPart FridgeUpdateRequestDto fridgeRequestDto,
			@Parameter(description = "이미지 파일", required = false) @RequestPart(required = false) MultipartFile file) {
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
		    throw new ApiException(FridgeErrorCode.FRIDGE_UNAUTHORIZED_ACCESS);
		}
		
		FridgeUpdateResponseDto fridgeResponseDto = fridgeService.updateFridge(fridgeRequestDto, file);
		
		return ResponseEntity.status(FridgeSuccessCode.FRIDGE_UPDATE_SUCCESS.getStatus())
				.body(ApiResponse.success(FridgeSuccessCode.FRIDGE_UPDATE_SUCCESS, fridgeResponseDto));
	}
	
	
	
	
	
	// 냉장고 삭제
	@DeleteMapping("/fridges/{id}")
	public ResponseEntity<?> deleteFridge(
			@Parameter(description = "냉장고의 일련번호") @PathVariable int id) {
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
		    throw new ApiException(FridgeErrorCode.FRIDGE_UNAUTHORIZED_ACCESS);
		}
		
		fridgeService.deleteFridge(id);
		
		return ResponseEntity.status(FridgeSuccessCode.FRIDGE_DELETE_SUCCESS.getStatus())
				.body(ApiResponse.success(FridgeSuccessCode.FRIDGE_DELETE_SUCCESS, null));
	}
	
	
	
	
	
	// 사용자 냉장고 조회
	@GetMapping("/users/{id}/fridges")
	public ResponseEntity<?> readUserFridge(
			@Parameter(description = "사용자의 일련번호") @PathVariable Integer id) {
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
		    throw new ApiException(FridgeErrorCode.FRIDGE_UNAUTHORIZED_ACCESS);
		}
		
		List<UserFridgeReadResponseDto> fridgeList = fridgeService.readUserFridgeList(id);

		return ResponseEntity.status(FridgeSuccessCode.USER_FRIDGE_READ_LIST_SUCCESS.getStatus())
				.body(ApiResponse.success(FridgeSuccessCode.USER_FRIDGE_READ_LIST_SUCCESS, fridgeList));
	}
	
	
	
	
	
	
	
	
	
	// 냉장고 파먹기
	@GetMapping("/fridges/pick")
	public ResponseEntity<?> pickFridge() {
		
		// 인증 여부 확인 (비로그인)
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
		    throw new ApiException(FridgeErrorCode.FRIDGE_UNAUTHORIZED_ACCESS);
		}
		
		// 로그인 정보
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		int userId = userService.readUserByUsername(username).getId();
		
		// List<RecipeReadResponseDto> recipeList = fridgeService.readPickList(userId);
		List<RecipeReadResponseDto> recipeList = null;
		
		return ResponseEntity.status(FridgeSuccessCode.FRIDGE_PICK_LIST_SUCCESS.getStatus())
				.body(ApiResponse.success(FridgeSuccessCode.FRIDGE_PICK_LIST_SUCCESS, recipeList));
		
	}
	
	
	
	
	
	
	
	
	
	
	

}
