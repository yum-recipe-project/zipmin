package com.project.zipmin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.zipmin.api.ApiException;
import com.project.zipmin.api.ApiResponse;
import com.project.zipmin.api.CommentErrorCode;
import com.project.zipmin.api.KitchenErrorCode;
import com.project.zipmin.api.KitchenSuccessCode;
import com.project.zipmin.api.RecipeErrorCode;
import com.project.zipmin.api.RecipeSuccessCode;
import com.project.zipmin.api.VoteErrorCode;
import com.project.zipmin.dto.LikeCreateRequestDto;
import com.project.zipmin.dto.LikeCreateResponseDto;
import com.project.zipmin.dto.RecipeCreateRequestDto;
import com.project.zipmin.dto.RecipeCreateResponseDto;
import com.project.zipmin.dto.RecipeReadResponseDto;
import com.project.zipmin.service.RecipeService;
import com.project.zipmin.service.UserService;
import com.project.zipmin.swagger.InternalServerErrorResponse;
import com.project.zipmin.swagger.RecipeCategoryCreateFailResponse;
import com.project.zipmin.swagger.RecipeCategoryInvalidInputResponse;
import com.project.zipmin.swagger.RecipeCategoryReadListFailResponse;
import com.project.zipmin.swagger.RecipeCreateFailResponse;
import com.project.zipmin.swagger.RecipeCreateSuccessResponse;
import com.project.zipmin.swagger.RecipeDeleteFailResponse;
import com.project.zipmin.swagger.RecipeDeleteSuccessResponse;
import com.project.zipmin.swagger.RecipeFileUploadFailResponse;
import com.project.zipmin.swagger.RecipeForbiddenResponse;
import com.project.zipmin.swagger.RecipeInvalidInputResponse;
import com.project.zipmin.swagger.RecipeNotFoundResponse;
import com.project.zipmin.swagger.RecipeReadListFailResponse;
import com.project.zipmin.swagger.RecipeReadListSuccessResponse;
import com.project.zipmin.swagger.RecipeReadSuccessResponse;
import com.project.zipmin.swagger.RecipeStepCreateFailResponse;
import com.project.zipmin.swagger.RecipeStepFileUploadFailResponse;
import com.project.zipmin.swagger.RecipeStepInvalidInputResponse;
import com.project.zipmin.swagger.RecipeStepReadListFailResponse;
import com.project.zipmin.swagger.RecipeStockCreateFailResponse;
import com.project.zipmin.swagger.RecipeStockInvalidInputResponse;
import com.project.zipmin.swagger.RecipeStockReadListFailResponse;
import com.project.zipmin.swagger.RecipeUnauthorizedAccessResponse;
import com.project.zipmin.swagger.UserInvalidInputResponse;
import com.project.zipmin.swagger.UserNotFoundResponse;
import com.project.zipmin.swagger.VoteUpdateSuccessResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
public class RecipeController {
	
	@Autowired
	RecipeService recipeService;
	
	@Autowired
	UserService userService;
	
	

	
	
	@Operation(
	    summary = "레시피 목록 조회"
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "200",
				description = "레시피 목록 조회 성공",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = RecipeReadListSuccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "레시피 목록 조회 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = RecipeReadListFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = RecipeInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "500",
				description = "서버 내부 오류",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = InternalServerErrorResponse.class)))
	})
	// 레시피 목록 조회
	@GetMapping("/recipes")
	public ResponseEntity<?> readRecipe(
			@Parameter(description = "카테고리", required = false) @RequestParam(required = false) List<String> categoryList,
			@Parameter(description = "검색어", required = false) @RequestParam(required = false) String keyword,
			@Parameter(description = "정렬", required = false) @RequestParam(required = false) String sort,
			@RequestParam int page,
			@RequestParam int size) {
		
		Pageable pageable = PageRequest.of(page, size);
		Page<RecipeReadResponseDto> recipePage = recipeService.readRecipePage(categoryList, keyword, sort, pageable);
		
		return ResponseEntity.status(RecipeSuccessCode.RECIPE_READ_LIST_SUCCESS.getStatus())
				.body(ApiResponse.success(RecipeSuccessCode.RECIPE_READ_LIST_SUCCESS, recipePage));
	}
	
	
	
	
	
	@Operation(
	    summary = "레시피 조회"
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "200",
				description = "레시피 조회 성공",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = RecipeReadSuccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "레시피 카테고리 조회 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = RecipeCategoryReadListFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "레시피 재료 조회 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = RecipeStockReadListFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "레시피 조리 순서 조회 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = RecipeStepReadListFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = RecipeInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 레시피를 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = RecipeNotFoundResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 사용자를 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserNotFoundResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "500",
				description = "서버 내부 오류",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = InternalServerErrorResponse.class)))
	})
	// 레시피 조회
	@GetMapping("/recipes/{id}")
	public ResponseEntity<?> viewRecipe(@Parameter(description = "레시피의 일련번호") @PathVariable int id) {
		
		RecipeReadResponseDto recipeDto = recipeService.readRecipdById(id);
		
		return ResponseEntity.status(RecipeSuccessCode.RECIPE_READ_SUCCESS.getStatus())
				.body(ApiResponse.success(RecipeSuccessCode.RECIPE_READ_SUCCESS, recipeDto));
	}
	
	
	

	
	@Operation(
	    summary = "레시피 작성"
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "201",
				description = "레시피 작성 성공",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = RecipeCreateSuccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "레시피 작성 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = RecipeCreateFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "레시피 카테고리 작성 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = RecipeCategoryCreateFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "레시피 재료 작성 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = RecipeStockCreateFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "레시피 작성 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = RecipeStepCreateFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = RecipeInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = RecipeCategoryInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = RecipeStockInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = RecipeStepInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "401",
				description = "로그인 되지 않은 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = RecipeUnauthorizedAccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 사용자를 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserNotFoundResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "500",
				description = "레시피 파일 업로드 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = RecipeFileUploadFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "500",
				description = "레시피 조리 순서 파일 업로드 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = RecipeStepFileUploadFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "500",
				description = "서버 내부 오류",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = InternalServerErrorResponse.class)))
	})
	// 레시피 작성
	@PostMapping(value = "/recipes", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> writeRecipe(
			@Parameter(description = "레시피 작성 요청 정보") @RequestPart RecipeCreateRequestDto recipeRequestDto,
			@Parameter(description = "레시피 이미지 파일") @RequestPart MultipartFile recipeImage,
			@Parameter(description = "조리 과정 이미지 파일") @RequestParam(required = false) MultiValueMap<String, MultipartFile> stepImageMap) {
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
		    throw new ApiException(RecipeErrorCode.RECIPE_UNAUTHORIZED_ACCESS);
		}
		recipeRequestDto.setUserId(userService.readUserByUsername(authentication.getName()).getId());
		
		RecipeCreateResponseDto recipeResponseDto = recipeService.createRecipe(recipeRequestDto, recipeImage, stepImageMap);
		
		return ResponseEntity.status(RecipeSuccessCode.RECIPE_CREATE_SUCCESS.getStatus())
				.body(ApiResponse.success(RecipeSuccessCode.RECIPE_CREATE_SUCCESS, recipeResponseDto));
	}
	

	
	
	
	@Operation(
	    summary = "레시피 조회"
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "200",
				description = "레시피 삭제 성공",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = RecipeDeleteSuccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "레시피 삭제 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = RecipeDeleteFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = RecipeInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "401",
				description = "로그인 되지 않은 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = RecipeUnauthorizedAccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "403",
				description = "권한 없는 사용자의 접근",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = RecipeForbiddenResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 레시피를 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = RecipeNotFoundResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 사용자를 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserNotFoundResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "500",
				description = "서버 내부 오류",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = InternalServerErrorResponse.class)))
	})
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	// 특정 레시피 좋아요 (저장)
	@PostMapping("/recipes/{id}/likes")
	public ResponseEntity<?> likeRecipes(
			@PathVariable("id") int recipeId,
			@RequestBody LikeCreateRequestDto likeRequestDto) {
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
		    throw new ApiException(RecipeErrorCode.RECIPE_UNAUTHORIZED_ACCESS);
		}
		
		
		likeRequestDto.setUserId(userService.readUserByUsername(authentication.getName()).getId());
		LikeCreateResponseDto likeResponseDto = recipeService.likeRecipe(likeRequestDto);
					
		
		System.err.println("레시피 저장 성공");
		return ResponseEntity.status(RecipeSuccessCode.RECIPE_LIKE_SUCCESS.getStatus())
				.body(ApiResponse.success(RecipeSuccessCode.RECIPE_LIKE_SUCCESS, likeResponseDto));
	}
	
	
}
