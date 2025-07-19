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
import org.springframework.web.bind.annotation.RestController;

import com.project.zipmin.api.ApiException;
import com.project.zipmin.api.ApiResponse;
import com.project.zipmin.api.CommentErrorCode;
import com.project.zipmin.api.RecipeSuccessCode;
import com.project.zipmin.dto.RecipeCreateRequestDto;
import com.project.zipmin.dto.RecipeCreateResponseDto;
import com.project.zipmin.dto.RecipeReadResponseDto;
import com.project.zipmin.service.RecipeService;
import com.project.zipmin.service.UserService;

@RestController
public class RecipeController {
	
	@Autowired
	RecipeService recipeService;
	@Autowired
	UserService userService;
	
	
	// 레시피 목록 조회
	@GetMapping("/recipes")
	public ResponseEntity<?> readRecipe(
			@RequestParam String sort,
			@RequestParam int page,
			@RequestParam int size) {
		
		Pageable pageable = PageRequest.of(page, size);
		Page<RecipeReadResponseDto> recipePage = null;
		
		// ****** 카테고리 검색 + 검색어 검색 추가할 것 (현재는 홈에서 사용하기 위해 정렬 처리만 되어 있음) ******
		// ****** 그리고 홈에서는 별점, 팔로우만 보기 등 필요 없어서 이런 세부적인 부분도 구현 안되어 있음 ******
		if (sort.equals("new")) {
			recipePage = recipeService.readRecipePageOrderByIdDesc(pageable);
		}
		else if (sort.equals("hot")) {
			recipePage = recipeService.readRecipePageOrderByLikecount(pageable);
		}
		else if (sort.equals("score")) {
			recipePage = recipeService.readRecipePageOrderByScore(pageable);
		}
		
		return ResponseEntity.status(RecipeSuccessCode.RECIPE_READ_LIST_SUCCESS.getStatus())
				.body(ApiResponse.success(RecipeSuccessCode.RECIPE_READ_LIST_SUCCESS, recipePage));
	}
	
	
	
	// 특정 레시피 조회 (테스트)
	@GetMapping("/recipes/{id}")
	public ResponseEntity<?> viewRecipe(@PathVariable int id) {
		
		
		return null;
	}
	
	
	
	// 레시피 작성
	@PostMapping("/recipes")
	public ResponseEntity<?> writeRecipe(
			@RequestBody RecipeCreateRequestDto recipeRequestDto) {
		
		// 인증 여부 확인 (비로그인)
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
		    throw new ApiException(CommentErrorCode.COMMENT_UNAUTHORIZED_ACCESS);
		}
		
		// 로그인 정보
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		recipeRequestDto.setUserId(userService.readUserByUsername(username).getId());
		
		RecipeCreateResponseDto recipeResponseDto = recipeService.createRecipe(recipeRequestDto);
		
		return ResponseEntity.status(RecipeSuccessCode.RECIPE_CREATE_SUCCESS.getStatus())
				.body(ApiResponse.success(RecipeSuccessCode.RECIPE_CREATE_SUCCESS, recipeResponseDto));
	}
	
	
	
	// 특정 레시피 수정
	@PutMapping("/recipes/{id}")
	public ResponseEntity<?> editRecipe(@PathVariable("recipeIdx") int id) {
		
		// 구현 필요 없을 듯
		
		return null;
	}
	
	
	
	// 특정 레시피 삭제
	@DeleteMapping("/recipes/{id}")
	public ResponseEntity<?> deleteRecipe(@PathVariable int id) {
		return null;
	}
	

}
