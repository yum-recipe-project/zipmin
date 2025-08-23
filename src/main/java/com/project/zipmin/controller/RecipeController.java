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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.project.zipmin.api.ApiException;
import com.project.zipmin.api.ApiResponse;
import com.project.zipmin.api.CommentErrorCode;
import com.project.zipmin.api.RecipeErrorCode;
import com.project.zipmin.api.RecipeSuccessCode;
import com.project.zipmin.api.VoteErrorCode;
import com.project.zipmin.dto.RecipeCreateRequestDto;
import com.project.zipmin.dto.RecipeCreateResponseDto;
import com.project.zipmin.dto.RecipeReadResponseDto;
import com.project.zipmin.service.RecipeService;
import com.project.zipmin.service.UserService;

import io.swagger.v3.oas.annotations.Parameter;

@RestController
public class RecipeController {
	
	@Autowired
	RecipeService recipeService;
	
	@Autowired
	UserService userService;
	
	
	
	
	
	// 레시피 목록 조회
	@GetMapping("/recipes")
	public ResponseEntity<?> readRecipe(
			@Parameter(description = "카테고리", required = false) @RequestParam(required = false) List<String> categoryList,
			@Parameter(description = "검색어", required = false) @RequestParam(required = false) String keyword,
			@Parameter(description = "정렬", required = false) @RequestParam(required = false) String sort,
			@RequestParam int page,
			@RequestParam int size) {
		
		// *** 팔로우만 보기 기능도 구현 필요 ***
		// *** 카테고리는 아마 배열이나 맵으로 처리해야 할듯 ***
		
		Pageable pageable = PageRequest.of(page, size);
		Page<RecipeReadResponseDto> recipePage = recipeService.readRecipePage(categoryList, keyword, sort, pageable);
		
		return ResponseEntity.status(RecipeSuccessCode.RECIPE_READ_LIST_SUCCESS.getStatus())
				.body(ApiResponse.success(RecipeSuccessCode.RECIPE_READ_LIST_SUCCESS, recipePage));
	}
	
	
	
	
	
	// 특정 레시피 조회
	@GetMapping("/recipes/{id}")
	public ResponseEntity<?> viewRecipe(@Parameter(description = "레시피의 일련번호") @PathVariable int id) {
		
		RecipeReadResponseDto recipeDto = recipeService.readRecipdById(id);
		
		return ResponseEntity.status(RecipeSuccessCode.RECIPE_READ_SUCCESS.getStatus())
				.body(ApiResponse.success(RecipeSuccessCode.RECIPE_READ_SUCCESS, recipeDto));
	}
	
	
	
	
	
	// 레시피 작성
	@PostMapping("/recipes")
	public ResponseEntity<?> writeRecipe(
			@Parameter(description = "투표 작성 요청 정보") @RequestPart @RequestBody RecipeCreateRequestDto recipeRequestDto
			// *** 여기에 파일 추가 ***
			) {
		
		// *** 파일 추가 필요 ***
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
		    throw new ApiException(RecipeErrorCode.RECIPE_UNAUTHORIZED_ACCESS);
		}
		recipeRequestDto.setUserId(userService.readUserByUsername(authentication.getName()).getId());
		
		RecipeCreateResponseDto recipeResponseDto = recipeService.createRecipe(recipeRequestDto);
		
		return ResponseEntity.status(RecipeSuccessCode.RECIPE_CREATE_SUCCESS.getStatus())
				.body(ApiResponse.success(RecipeSuccessCode.RECIPE_CREATE_SUCCESS, recipeResponseDto));
	}
	

	
	
	
	// 레시피 삭제 (관리자)
	@DeleteMapping("/recipes/{id}")
	public ResponseEntity<?> deleteRecipe(@Parameter(description = "레시피의 일련번호") @PathVariable int id) {
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
		    throw new ApiException(RecipeErrorCode.RECIPE_UNAUTHORIZED_ACCESS);
		}
		
		recipeService.deleteRecipe(id);
		
		return ResponseEntity.status(RecipeSuccessCode.RECIPE_DELETE_SUCCESS.getStatus())
				.body(ApiResponse.success(RecipeSuccessCode.RECIPE_DELETE_SUCCESS, null));
	}
	

}
