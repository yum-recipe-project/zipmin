package com.project.zipmin.controller;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.zipmin.api.ApiException;
import com.project.zipmin.api.ApiResponse;
import com.project.zipmin.api.FridgeErrorCode;
import com.project.zipmin.api.FridgeSuccessCode;
import com.project.zipmin.dto.FridgeCreateRequestDto;
import com.project.zipmin.dto.FridgeCreateResponseDto;
import com.project.zipmin.dto.FridgeDeleteRequestDto;
import com.project.zipmin.dto.FridgeReadResponseDto;
import com.project.zipmin.dto.FridgeUpdateRequestDto;
import com.project.zipmin.dto.FridgeUpdateResponseDto;
import com.project.zipmin.dto.RecipeReadResponseDto;
import com.project.zipmin.dto.UserReadResponseDto;
import com.project.zipmin.service.FridgeService;
import com.project.zipmin.service.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Fridge API", description = "냉장고 관련 API")
public class FridgeController {
	
	@Autowired
	FridgeService fridgeService;
	@Autowired
	UserService userService;
	
	
	
	// 냉장고 목록 조회
	@GetMapping("/fridges")
	public ResponseEntity<?> readFridge() {
		
		// 인증 여부 확인 (비로그인)
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
		    throw new ApiException(FridgeErrorCode.FRIDGE_UNAUTHORIZED_ACCESS);
		}
		
		// 로그인 정보
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserReadResponseDto userDto = userService.readUserByUsername(username);

		List<FridgeReadResponseDto> fridgeList = fridgeService.readFridgeList(userDto.getId());
		
		return ResponseEntity.status(FridgeSuccessCode.FRIDGE_READ_LIST_SUCCESS.getStatus())
				.body(ApiResponse.success(FridgeSuccessCode.FRIDGE_READ_LIST_SUCCESS, fridgeList));
	}
	
	
	
	// 냉장고 작성
	@PostMapping("/fridges")
	public ResponseEntity<?> createFridge(@RequestBody FridgeCreateRequestDto fridgeRequestDto) {
		
		// 인증 여부 확인 (비로그인)
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
		    throw new ApiException(FridgeErrorCode.FRIDGE_UNAUTHORIZED_ACCESS);
		}
		
		// 로그인 정보
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		fridgeRequestDto.setUserId(userService.readUserByUsername(username).getId());
		
		FridgeCreateResponseDto fridgeResponseDto = fridgeService.createFridge(fridgeRequestDto);
		
		return ResponseEntity.status(FridgeSuccessCode.FRIDGE_CREATE_SUCCESS.getStatus())
				.body(ApiResponse.success(FridgeSuccessCode.FRIDGE_CREATE_SUCCESS, fridgeResponseDto));
	}
	
	
	
	// 냉장고 수정
	@PatchMapping("/fridges/{id}")
	public ResponseEntity<?> updateFridge(
			@PathVariable int id,
			@RequestBody FridgeUpdateRequestDto fridgeRequestDto) {
		
		// 인증 여부 확인 (비로그인)
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
		    throw new ApiException(FridgeErrorCode.FRIDGE_UNAUTHORIZED_ACCESS);
		}
		
		// 로그인 정보
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		fridgeRequestDto.setUserId(userService.readUserByUsername(username).getId());
		
		FridgeUpdateResponseDto fridgeResponseDto = fridgeService.updateFridge(fridgeRequestDto);
		
		return ResponseEntity.status(FridgeSuccessCode.FRIDGE_UPDATE_SUCCESS.getStatus())
				.body(ApiResponse.success(FridgeSuccessCode.FRIDGE_UPDATE_SUCCESS, fridgeResponseDto));
	}
	
	
	
	// 냉장고 삭제
	@DeleteMapping("/fridges/{id}")
	public ResponseEntity<?> deleteFridge(
			@PathVariable int id,
			@RequestBody FridgeDeleteRequestDto fridgeDto) {
		
		// 인증 여부 확인 (비로그인)
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
		    throw new ApiException(FridgeErrorCode.FRIDGE_UNAUTHORIZED_ACCESS);
		}
		
		// 로그인 정보
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		fridgeDto.setUserId(userService.readUserByUsername(username).getId());
		
		fridgeService.deleteFridge(fridgeDto);
		
		return ResponseEntity.status(FridgeSuccessCode.FRIDGE_DELETE_SUCCESS.getStatus())
				.body(ApiResponse.success(FridgeSuccessCode.FRIDGE_DELETE_SUCCESS, null));
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
		
		List<RecipeReadResponseDto> recipeList = fridgeService.readPickList(userId);
		
		return ResponseEntity.status(FridgeSuccessCode.FRIDGE_PICK_LIST_SUCCESS.getStatus())
				.body(ApiResponse.success(FridgeSuccessCode.FRIDGE_PICK_LIST_SUCCESS, recipeList));
		
	}
	
	
	
	
	
	
	
	
	
	
	

}
