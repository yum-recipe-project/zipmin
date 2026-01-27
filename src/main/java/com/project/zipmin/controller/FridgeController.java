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
import org.springframework.web.bind.annotation.RestController;

import com.project.zipmin.api.ApiException;
import com.project.zipmin.api.ApiResponse;
import com.project.zipmin.api.FridgeErrorCode;
import com.project.zipmin.api.FridgeSuccessCode;
import com.project.zipmin.dto.FridgeCreateRequestDto;
import com.project.zipmin.dto.FridgeCreateResponseDto;
import com.project.zipmin.dto.FridgeReadResponseDto;
import com.project.zipmin.dto.FridgeUpdateRequestDto;
import com.project.zipmin.dto.FridgeUpdateResponseDto;
import com.project.zipmin.dto.UserFridgeCreateRequestDto;
import com.project.zipmin.dto.UserFridgeCreateResponseDto;
import com.project.zipmin.dto.UserFridgeReadResponseDto;
import com.project.zipmin.dto.UserFridgeUpdateRequestDto;
import com.project.zipmin.dto.UserFridgeUpdateResponseDto;
import com.project.zipmin.dto.like.LikeCreateRequestDto;
import com.project.zipmin.dto.like.LikeCreateResponseDto;
import com.project.zipmin.dto.like.LikeDeleteRequestDto;
import com.project.zipmin.dto.recipe.RecipeReadResponseDto;
import com.project.zipmin.entity.Role;
import com.project.zipmin.service.FridgeService;
import com.project.zipmin.service.UserService;
import com.project.zipmin.swagger.InternalServerErrorResponse;
import com.project.zipmin.swagger.UserInvalidInputResponse;
import com.project.zipmin.swagger.UserNotFoundResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Fridge API", description = "냉장고 관련 API")
public class FridgeController {
	
	@Autowired
	FridgeService fridgeService;
	
	@Autowired
	UserService userService;
	
	
	
	
	
	@Operation(
	    summary = "냉장고 목록 조회"
	)
	@ApiResponses(value = {
		// 200 FRIDGE_READ_LIST_SUCCESS
		// 400 FRIDGE_READ_LIST_FAIL
		// 400 FRIDGE_INVALID_INPUT
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "500",
				description = "서버 내부 오류",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = InternalServerErrorResponse.class)))
	})
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
	
	
	
	
	
	@Operation(
	    summary = "사용자 냉장고 목록 조회"
	)
	@ApiResponses(value = {
		// 200 USER_FRIDGE_READ_LIST_SUCCESS
		// 400 USER_FRIDGE_READ_LIST_FAIL
		// 400 USER_FRIDGE_INVALID_INPUT
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserInvalidInputResponse.class))),
		// 401 USER_FRIDGE_UNAUTHORIZED_ACCESS
		// 403 USER_FRIDGE_FORBIDDEN
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
	// 사용자 냉장고 목록 조회
	@GetMapping("/users/{id}/fridges")
	public ResponseEntity<?> readUserFridge(
			@Parameter(description = "사용자의 일련번호") @PathVariable Integer id) {
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
		    throw new ApiException(FridgeErrorCode.USER_FRIDGE_UNAUTHORIZED_ACCESS);
		}
		
		List<UserFridgeReadResponseDto> fridgeList = fridgeService.readUserFridgeList(id);

		return ResponseEntity.status(FridgeSuccessCode.USER_FRIDGE_READ_LIST_SUCCESS.getStatus())
				.body(ApiResponse.success(FridgeSuccessCode.USER_FRIDGE_READ_LIST_SUCCESS, fridgeList));
	}
	
	
	
	
	
	@Operation(
	    summary = "작성한 냉장고 목록 조회"
	)
	@ApiResponses(value = {
		// 200 FRIDGE_READ_LIST_SUCCESS
		// 400 FRIDGE_READ_LIST_FAIL
		// 400 FRIDGE_INVALID_INPUT
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserInvalidInputResponse.class))),
		// 401 FRIDGE_UNAUTHORIZED_ACCESS
		// 403 FRIDGE_FORBIDDEN
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
	// 작성한 냉장고 목록 조회
	@GetMapping("/users/{id}/created-fridges")
	public ResponseEntity<?> listAddFridgeList(
			@Parameter(description = "사용자의 일련번호") @PathVariable Integer id) {
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
		    throw new ApiException(FridgeErrorCode.FRIDGE_UNAUTHORIZED_ACCESS);
		}
		
		// 권한 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		if (!userService.readUserByUsername(username).getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			if (!userService.readUserByUsername(username).getRole().equals(Role.ROLE_ADMIN.name())) {
				if (userService.readUserByUsername(username).getId() != id) {
					throw new ApiException(FridgeErrorCode.FRIDGE_FORBIDDEN);
				}
			}
		}
		
		List<FridgeReadResponseDto> fridgeList = fridgeService.readCreatedFridgeList(id);
		
		return ResponseEntity.status(FridgeSuccessCode.FRIDGE_READ_LIST_SUCCESS.getStatus())
				.body(ApiResponse.success(FridgeSuccessCode.FRIDGE_READ_LIST_SUCCESS, fridgeList));
	}
	
	
	
	
	
	@Operation(
	    summary = "좋아요한 냉장고 목록 조회"
	)
	@ApiResponses(value = {
		// 200 FRIDGE_READ_LIST_SUCCESS
		// 400 FRIDGE_READ_LIST_FAIL
		// 400 LIKE_READ_LIST_FAIL
		// 400 FRIDGE_INVALID_INPUT
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json", 
						schema = @Schema(implementation = UserInvalidInputResponse.class))),
		// 400 LIKE_INVALID_INPUT
		// 401 FRIDGE_UNAUTHORIZED_ACCESS
		// 403 FRIDGE_FOBIDDEN
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
	// 좋아요한 냉장고 목록 조회
	@GetMapping("/users/{id}/liked-fridges")
	public ResponseEntity<?> listLikedFridgeList(
			@Parameter(description = "사용자의 일련번호") @PathVariable Integer id) {
		
		// 입력값 검증
		if (id == null) {
			throw new ApiException(FridgeErrorCode.FRIDGE_INVALID_INPUT);
		}
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
		    throw new ApiException(FridgeErrorCode.FRIDGE_UNAUTHORIZED_ACCESS);
		}
		
		// 권한 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		if (!userService.readUserByUsername(username).getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			if (!userService.readUserByUsername(username).getRole().equals(Role.ROLE_ADMIN.name())) {
				if (userService.readUserByUsername(username).getId() != id) {
					throw new ApiException(FridgeErrorCode.FRIDGE_FORBIDDEN);
				}
			}
		}
		
		List<FridgeReadResponseDto> fridgeList = fridgeService.readLikedFridgeList(id);
		
		return ResponseEntity.status(FridgeSuccessCode.FRIDGE_READ_LIST_SUCCESS.getStatus())
				.body(ApiResponse.success(FridgeSuccessCode.FRIDGE_READ_LIST_SUCCESS, fridgeList));
	}
	
	
	
	
	// FRIDGE_PICK_LIST_SUCCESS
	// USER_FRIDGE_READ_LIST_FAIL
	// USER_FRIDGE_UNAUTHORIZED_ACCESS
	// USER_INVALID_INPUT
	// USER_NOT_FOUND
	// USER_FRDIGE_INVALID_INPUT
	// RECIPE_READ_LIST_FAIL
	// RECIPE_STOCK_READ_LIST_FAIL
	
	// 냉장고 파먹기 목록 조회
	@GetMapping("/users/{id}/picked-fridges")
	public ResponseEntity<?> pickFridge(
			@Parameter(description = "사용자의 일련번호") @PathVariable Integer id) {
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
		    throw new ApiException(FridgeErrorCode.USER_FRIDGE_UNAUTHORIZED_ACCESS);
		}
		
		// 권한 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		if (!userService.readUserByUsername(username).getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			if (!userService.readUserByUsername(username).getRole().equals(Role.ROLE_ADMIN.name())) {
				if (userService.readUserByUsername(username).getId() != id) {
					throw new ApiException(FridgeErrorCode.USER_FRIDGE_FORBIDDEN);
				}
			}
		}
		
		List<RecipeReadResponseDto> recipeList = fridgeService.readPickList(id);
		
		return ResponseEntity.status(FridgeSuccessCode.FRIDGE_PICK_LIST_SUCCESS.getStatus())
				.body(ApiResponse.success(FridgeSuccessCode.FRIDGE_PICK_LIST_SUCCESS, recipeList));
		
	}
	
	
	
	
	
	@Operation(
	    summary = "냉장고 작성"
	)
	@ApiResponses(value = {
		// 201 FRIDGE_CREATE_SUCCESS
		// 400 FRIDGE_CREATE_FAIL
		// 400 FRIDGE_INVALID_INPUT
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserInvalidInputResponse.class))),
		// 401 FRIDGE_UNAUTHORIZED_ACCESS
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
	
	
	
	
	
	// USER_FRIDGE_CREATE_SUCCESS
	// USER_FRIDGE_CREATE_FAIL
	// USER_FRIDGE_INVALID_INPUT
	// USER_INVALID_INPUT
	// USER_FRIDGE_UNAUTHORIZED_ACCESS
	// USER_FRIDGE_FORBIDDEN
	// USER_NOT_FOUND
	
	// 사용자 냉장고 작성
	@PostMapping("/users/{id}/fridges")
	public ResponseEntity<?> createUserFridge(
			@Parameter(description = "사용자의 일련번호") @PathVariable Integer id,
			@Parameter(description = "사용자 냉장고 작성 요청 정보") @RequestBody UserFridgeCreateRequestDto fridgeRequestDto) {
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
		    throw new ApiException(FridgeErrorCode.USER_FRIDGE_UNAUTHORIZED_ACCESS);
		}
		
		// 권한 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		if (!userService.readUserByUsername(username).getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			if (!userService.readUserByUsername(username).getRole().equals(Role.ROLE_ADMIN.name())) {
				if (userService.readUserByUsername(username).getId() != id) {
					throw new ApiException(FridgeErrorCode.USER_FRIDGE_FORBIDDEN);
				}
			}
		}
		fridgeRequestDto.setUserId(id);
		
		UserFridgeCreateResponseDto fridgeResponseDto = fridgeService.createUserFridge(fridgeRequestDto);
		
		return ResponseEntity.status(FridgeSuccessCode.USER_FRIDGE_CREATE_SUCCESS.getStatus())
				.body(ApiResponse.success(FridgeSuccessCode.USER_FRIDGE_CREATE_SUCCESS, fridgeResponseDto));
	}
	
	
	

	
	@Operation(
	    summary = "냉장고 수정"
	)
	@ApiResponses(value = {
		// 200 FRIDGE_UPDATE_SUCCESS
		// 400 FRIDGE_UPDATE_FAIL
		// 400 FRIDGE_INVALID_INPUT
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserInvalidInputResponse.class))),
		// 401 FRIDGE_UNAUTHORIZED_ACCESS
		// 404 FRIDGE_FORDBIDDEN
		// 404 FRIDGE_NOT_FOUND
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
	// 냉장고 수정
	@PatchMapping("/fridges/{id}")
	public ResponseEntity<?> updateFridge(
			@Parameter(description = "냉장고의 일련번호") @PathVariable int id,
			@Parameter(description = "냉장고 수정 요청 정보") @RequestBody FridgeUpdateRequestDto fridgeRequestDto) {
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
		    throw new ApiException(FridgeErrorCode.FRIDGE_UNAUTHORIZED_ACCESS);
		}
		
		FridgeUpdateResponseDto fridgeResponseDto = fridgeService.updateFridge(fridgeRequestDto);
		
		return ResponseEntity.status(FridgeSuccessCode.FRIDGE_UPDATE_SUCCESS.getStatus())
				.body(ApiResponse.success(FridgeSuccessCode.FRIDGE_UPDATE_SUCCESS, fridgeResponseDto));
	}
	
	
	
	
	
	// USER_FRIDGE_UPDATE_FAIL
	// USER_FRIDGE_INVALID_INPUT
	// USER_INVALID_INPUT
	// USER_FRIDGE_UNAUTHORIZED_ACCESS
	// USER_FRIDGE_FORBIDDEN
	// USER_FRIDGE_NOT_FOUND
	// USER_NOT_FOUND
	// INTERNAL_SERVER_ERROR
	
	// 사용자 냉장고 수정
	@PatchMapping("/users/{userId}/fridges/{fridgeId}")
	public ResponseEntity<?> updateUserFridge(
			@Parameter(description = "사용자의 일련번호") @PathVariable Integer userId,
			@Parameter(description = "냉장고의 일련번호") @PathVariable int fridgeId,
			@Parameter(description = "사용자 냉장고 수정 요청 정보") @RequestBody UserFridgeUpdateRequestDto userFridgeRequestDto) {
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
		    throw new ApiException(FridgeErrorCode.USER_FRIDGE_UNAUTHORIZED_ACCESS);
		}
		userFridgeRequestDto.setUserId(userId);
		
		UserFridgeUpdateResponseDto userFridgeResponseDto = fridgeService.updateUserFridge(userFridgeRequestDto);
		
		return ResponseEntity.status(FridgeSuccessCode.USER_FRIDGE_UPDATE_SUCCESS.getStatus())
				.body(ApiResponse.success(FridgeSuccessCode.USER_FRIDGE_UPDATE_SUCCESS, userFridgeResponseDto));
	}
	
	
	
	

	@Operation(
	    summary = "냉장고 삭제"
	)
	@ApiResponses(value = {
		// 200 FRIDGE_DELETE_SUCCESS
		// 400 FRIDGE_DELETE_FAIL
		// 400 FRIDGE_INVALID_INPUT
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserInvalidInputResponse.class))),
		// 401 FRIDGE_UNAUTHORIZED_ACCESS
		// 404 FRIDGE_FORDBIDDEN
		// 404 FRIDGE_NOT_FOUND
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
	
	
	
	
	
	
	// 사용자 냉장고 삭제
	@DeleteMapping("/users/{userId}/fridges/{fridgeId}")
	public ResponseEntity<?> deleteFridge(
			@Parameter(description = "사용자의 일련번호") @PathVariable Integer userId,
			@Parameter(description = "냉장고의 일련번호") @PathVariable int fridgeId) {
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
		    throw new ApiException(FridgeErrorCode.USER_FRIDGE_UNAUTHORIZED_ACCESS);
		}
		
		fridgeService.deleteUserFridge(fridgeId);
		
		return ResponseEntity.status(FridgeSuccessCode.USER_FRIDGE_DELETE_SUCCESS.getStatus())
				.body(ApiResponse.success(FridgeSuccessCode.USER_FRIDGE_DELETE_SUCCESS, null));
	}
	

	
	
	
	@Operation(
	    summary = "냉장고 좋아요"
	)
	@ApiResponses(value = {
		// 200 FRIDGE_LIKE_SUCCESS
		// 400 FRIDGE_LIKE_FAIL
		// 400 LIKE_CREATE_FAIL
		// 400 FRIDGE_INVALID_INPUT
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserInvalidInputResponse.class))),
		// 400 LIKE_INVALID_INPUT
		// 401 FRIDGE_UNAUTHORIZED_ACCESS
		// 404 FRIDGE_NOT_FOUND
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 사용자를 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserNotFoundResponse.class))),
		// 409 LIKE_DUPLICATE
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "500",
				description = "서버 내부 오류",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = InternalServerErrorResponse.class)))
	})
	// 냉장고 좋아요
	@PostMapping("/fridges/{id}/likes")
	public ResponseEntity<?> likeFridge(
			@Parameter(description = "냉장고의 일련번호") @PathVariable int id,
			@Parameter(description = "냉장고 좋아요 작성 요청 정보") @RequestBody LikeCreateRequestDto likeRequestDto) {
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
		    throw new ApiException(FridgeErrorCode.FRIDGE_UNAUTHORIZED_ACCESS);
		}
		likeRequestDto.setUserId(userService.readUserByUsername(authentication.getName()).getId());
		
		LikeCreateResponseDto likeResponseDto = fridgeService.likeFridge(likeRequestDto);
		
		return ResponseEntity.status(FridgeSuccessCode.FRIDGE_LIKE_SUCCESS.getStatus())
				.body(ApiResponse.success(FridgeSuccessCode.FRIDGE_LIKE_SUCCESS, likeResponseDto));
	}
	
	
	
	
	
	// FRIDGE_UNLIKE_SUCCESS
	// FRIDGE_UNLIKE_FAIL
	// LIKE_DELETE_FAIL
	// FRIDGE_INVALID_INPUT
	// USER_INVALID_INPUT
	// LIKE_INVALID_INPUT
	// FRIDGE_UNAUTHORIZED_ACCESS
	// LIKE_FORBIDDEN
	// FRIDGE_NOT_FOUND
	// USER_NOT_FOUND
	// LIKE_NOT_FOUND
	
	// 냉장고 좋아요 취소
	@DeleteMapping("/fridges/{id}/likes")
	public ResponseEntity<?> unlikeFridge(
			@Parameter(description = "냉장고의 일련번호") @PathVariable int id,
			@Parameter(description = "냉장고 좋아요 삭제 요청 정보") @RequestBody LikeDeleteRequestDto likeDto) {
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
		    throw new ApiException(FridgeErrorCode.FRIDGE_UNAUTHORIZED_ACCESS);
		}
		likeDto.setUserId(userService.readUserByUsername(authentication.getName()).getId());
		
		fridgeService.unlikeFridge(likeDto);
		
		return ResponseEntity.status(FridgeSuccessCode.FRIDGE_UNLIKE_SUCCESS.getStatus())
				.body(ApiResponse.success(FridgeSuccessCode.FRIDGE_UNLIKE_SUCCESS, null));
	}
	
	

	
	
	
	// 장보기 메모 
//	@GetMapping("/users/{id}/fridge-memo")
//	public ResponseEntity<?> listMemo(
//		    @RequestParam int page,
//		    @RequestParam int size) {
//		
//		Pageable pageable = PageRequest.of(page, size);
//		Page<MemoReadResponseDto> guidePage = null;
//		
//		MemoPage = fridgeService.readMemoPage(pageable);
//		
//        return ResponseEntity.status(FridgeSuccessCode.memo_READ_LIST_SUCCESS.getStatus())
//                .body(ApiResponse.success(FridgeSuccessCode.KITCHEN_READ_LIST_SUCCESS, guidePage));
//	}
		
	

}
