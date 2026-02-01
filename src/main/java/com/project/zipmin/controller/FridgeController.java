package com.project.zipmin.controller;

import java.util.List;

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
import com.project.zipmin.dto.fridge.FridgeCreateRequestDto;
import com.project.zipmin.dto.fridge.FridgeCreateResponseDto;
import com.project.zipmin.dto.fridge.FridgeReadResponseDto;
import com.project.zipmin.dto.fridge.FridgeStorageCreateRequestDto;
import com.project.zipmin.dto.fridge.FridgeStorageCreateResponseDto;
import com.project.zipmin.dto.fridge.FridgeStorageReadResponseDto;
import com.project.zipmin.dto.fridge.FridgeStorageUpdateRequestDto;
import com.project.zipmin.dto.fridge.FridgeStorageUpdateResponseDto;
import com.project.zipmin.dto.fridge.FridgeUpdateRequestDto;
import com.project.zipmin.dto.fridge.FridgeUpdateResponseDto;
import com.project.zipmin.dto.fridge.MemoCreateRequestDto;
import com.project.zipmin.dto.fridge.MemoCreateResponseDto;
import com.project.zipmin.dto.fridge.MemoReadResponseDto;
import com.project.zipmin.dto.fridge.MemoUpdateRequestDto;
import com.project.zipmin.dto.fridge.MemoUpdateResponseDto;
import com.project.zipmin.dto.like.LikeCreateRequestDto;
import com.project.zipmin.dto.like.LikeCreateResponseDto;
import com.project.zipmin.dto.like.LikeDeleteRequestDto;
import com.project.zipmin.dto.recipe.RecipeReadResponseDto;
import com.project.zipmin.entity.Role;
import com.project.zipmin.service.FridgeService;
import com.project.zipmin.service.UserService;
import com.project.zipmin.swagger.InternalServerErrorResponse;
import com.project.zipmin.swagger.fridge.FridgeCreateFailResponse;
import com.project.zipmin.swagger.fridge.FridgeCreateSuccessResponse;
import com.project.zipmin.swagger.fridge.FridgeDeleteFailResponse;
import com.project.zipmin.swagger.fridge.FridgeDeleteSuccessResponse;
import com.project.zipmin.swagger.fridge.FridgeForbiddenResponse;
import com.project.zipmin.swagger.fridge.FridgeInvalidInputResponse;
import com.project.zipmin.swagger.fridge.FridgeLikeFailResponse;
import com.project.zipmin.swagger.fridge.FridgeLikeSuccessResponse;
import com.project.zipmin.swagger.fridge.FridgeMemoDeleteFailResponse;
import com.project.zipmin.swagger.fridge.FridgeMemoDeleteSuccessResponse;
import com.project.zipmin.swagger.fridge.FridgeMemoInvalidInputResponse;
import com.project.zipmin.swagger.fridge.FridgeMemoNotFoundResponse;
import com.project.zipmin.swagger.fridge.FridgeMemoReadListFailResponse;
import com.project.zipmin.swagger.fridge.FridgeMemoReadListSuccessResponse;
import com.project.zipmin.swagger.fridge.FridgeMemoUpdateFailResponse;
import com.project.zipmin.swagger.fridge.FridgeMemoUpdateSuccessResponse;
import com.project.zipmin.swagger.fridge.FridgeNotFoundResponse;
import com.project.zipmin.swagger.fridge.FridgeReadListFailResponse;
import com.project.zipmin.swagger.fridge.FridgeReadListSuccessResponse;
import com.project.zipmin.swagger.fridge.FridgeStorageCreateFailResponse;
import com.project.zipmin.swagger.fridge.FridgeStorageCreateSuccessResponse;
import com.project.zipmin.swagger.fridge.FridgeStorageDeleteFailResponse;
import com.project.zipmin.swagger.fridge.FridgeStorageDeleteSuccessResponse;
import com.project.zipmin.swagger.fridge.FridgeStorageInvalidInputResponse;
import com.project.zipmin.swagger.fridge.FridgeStorageNotFoundResponse;
import com.project.zipmin.swagger.fridge.FridgeStorageReadListFailResponse;
import com.project.zipmin.swagger.fridge.FridgeStorageUpdateFailResponse;
import com.project.zipmin.swagger.fridge.FridgeStorageUpdateSuccessResponse;
import com.project.zipmin.swagger.fridge.FridgeUnauthorizedResponse;
import com.project.zipmin.swagger.fridge.FridgeUnlikeFailResponse;
import com.project.zipmin.swagger.fridge.FridgeUnlikeSuccessResponse;
import com.project.zipmin.swagger.fridge.FridgeUpdateFailResponse;
import com.project.zipmin.swagger.fridge.FridgeUpdateSuccessResponse;
import com.project.zipmin.swagger.like.LikeCreateFailResponse;
import com.project.zipmin.swagger.like.LikeDeleteFailResponse;
import com.project.zipmin.swagger.like.LikeDuplicatedResponse;
import com.project.zipmin.swagger.like.LikeForbiddenResponse;
import com.project.zipmin.swagger.like.LikeInvalidInputResponse;
import com.project.zipmin.swagger.like.LikeNotFoundResponse;
import com.project.zipmin.swagger.user.UserInvalidInputResponse;
import com.project.zipmin.swagger.user.UserNotFoundResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name = "Fridge API", description = "냉장고 관련 API")
public class FridgeController {
	
	private final FridgeService fridgeService;
	private final UserService userService;
	
	
	
	
	
	// 냉장고 목록 조회
	@Operation(
	    summary = "냉장고 목록 조회"
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "200",
				description = "냉장고 목록 조회 성공",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeReadListSuccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "냉장고 목록 조회 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeReadListFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserInvalidInputResponse.class))),
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
	// 냉장고 목록 조회
	@GetMapping("/fridges")
	public ResponseEntity<?> readFridge(
			@Parameter(description = "검색어", required = false) @RequestParam(required = false) String keyword,
			@Parameter(description = "사용자의 일련번호") int userId) {

		// 냉장고 목록 조회
		List<FridgeReadResponseDto> fridgePage = fridgeService.readFridgeList(keyword, userId);

		return ResponseEntity.status(FridgeSuccessCode.FRIDGE_READ_LIST_SUCCESS.getStatus())
				.body(ApiResponse.success(FridgeSuccessCode.FRIDGE_READ_LIST_SUCCESS, fridgePage));
	}
	
	
	
	
	
	// 사용자의 냉장고 목록 조회
	@Operation(
	    summary = "사용자의 냉장고 목록 조회"
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "200",
				description = "냉장고 목록 조회 성공",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeReadListSuccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "냉장고 목록 조회 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeReadListFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "401",
				description = "로그인되지 않은 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeUnauthorizedResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "403",
				description = "권한 없는 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeForbiddenResponse.class))),
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
	// 사용자의 냉장고 목록 조회
	@GetMapping("/users/{id}/fridges")
	public ResponseEntity<?> listAddFridgeList(
			@Parameter(description = "사용자의 일련번호") @PathVariable int id) {
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
		    throw new ApiException(FridgeErrorCode.FRIDGE_UNAUTHORIZED);
		}
		
		// 사용자의 냉장고 목록 조회
		List<FridgeReadResponseDto> fridgeList = fridgeService.readFridgePageByUserId(id);
		
		return ResponseEntity.status(FridgeSuccessCode.FRIDGE_READ_LIST_SUCCESS.getStatus())
				.body(ApiResponse.success(FridgeSuccessCode.FRIDGE_READ_LIST_SUCCESS, fridgeList));
	}
	
	
	
	
	
	// 좋아요한 냉장고 목록 조회
	@Operation(
	    summary = "좋아요한 냉장고 목록 조회"
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "200",
				description = "냉장고 목록 조회 성공",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeReadListSuccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "냉장고 목록 조회 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeReadListFailResponse.class))),
		// 400 LIKE_READ_LIST_FAIL
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeInvalidInputResponse.class))),
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
						schema = @Schema(implementation = LikeInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "401",
				description = "로그인되지 않은 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeUnauthorizedResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "403",
				description = "권한 없는 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeForbiddenResponse.class))),
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
			@Parameter(description = "사용자의 일련번호") @PathVariable int id) {
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
		    throw new ApiException(FridgeErrorCode.FRIDGE_UNAUTHORIZED);
		}
		
		// 좋아요한 냉장고 목록 조회
		List<FridgeReadResponseDto> fridgeList = fridgeService.readLikedFridgeListByUserId(id);
		
		return ResponseEntity.status(FridgeSuccessCode.FRIDGE_READ_LIST_SUCCESS.getStatus())
				.body(ApiResponse.success(FridgeSuccessCode.FRIDGE_READ_LIST_SUCCESS, fridgeList));
	}
	
	
	
	
	
	// 나의 냉장고 목록 조회
	@Operation(
	    summary = "나의 냉장고 목록 조회"
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "200",
				description = "나의 냉장고 목록 조회 성공",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeStorageReadListFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "나의 냉장고 목록 조회 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeStorageReadListFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeStorageInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "401",
				description = "로그인되지 않은 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeUnauthorizedResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "403",
				description = "권한 없는 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeForbiddenResponse.class))),
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
	// 나의 냉장고 목록 조회
	@GetMapping("/users/{id}/storages")
	public ResponseEntity<?> readUserFridge(
			@Parameter(description = "사용자의 일련번호") @PathVariable int id) {
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
		    throw new ApiException(FridgeErrorCode.FRIDGE_UNAUTHORIZED);
		}
		
		// 나의 냉장고 목록 조회
		List<FridgeStorageReadResponseDto> fridgeList = fridgeService.readStorageByUserId(id);

		return ResponseEntity.status(FridgeSuccessCode.FRIDGE_STORAGE_READ_LIST_SUCCESS.getStatus())
				.body(ApiResponse.success(FridgeSuccessCode.FRIDGE_STORAGE_READ_LIST_SUCCESS, fridgeList));
	}
	
	
	
	
	// TODO : 구현 및 API 문서 작성
	@Operation(
	    summary = "냉장고 파먹기 목록 조회"
	)
	@ApiResponses(value = {
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
	// 냉장고 파먹기 목록 조회
	@GetMapping("/users/{id}/picked-fridges")
	public ResponseEntity<?> pickFridge(
			@Parameter(description = "사용자의 일련번호") @PathVariable int id) {
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
		    throw new ApiException(FridgeErrorCode.FRIDGE_UNAUTHORIZED);
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
		
		List<RecipeReadResponseDto> recipeList = fridgeService.readPickList(id);
		
		return ResponseEntity.status(FridgeSuccessCode.FRIDGE_READ_LIST_SUCCESS.getStatus())
				.body(ApiResponse.success(FridgeSuccessCode.FRIDGE_READ_LIST_SUCCESS, recipeList));
		
	}
	
	
	
	
	
	// 장보기 메모 조회
	@Operation(
	    summary = "장보기 메모 목록 조회"
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "200",
				description = "장보기 메모 목록 조회 성공",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeMemoReadListSuccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "장보기 메모 목록 조회 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeMemoReadListFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeMemoInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "401",
				description = "로그인되지 않은 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeUnauthorizedResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "403",
				description = "권한 없는 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeForbiddenResponse.class))),
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
	// 장보기 메모 목록 조회
	@GetMapping("/users/{id}/memos")
	public ResponseEntity<?> readMemoList (
			@Parameter(description = "사용자의 일련번호") @PathVariable int id) {

		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
		    throw new ApiException(FridgeErrorCode.FRIDGE_UNAUTHORIZED);
		}

		// 장보기 메모 목록 조회
	    List<MemoReadResponseDto> memoList = fridgeService.readMemoListByUserId(id);

	    return ResponseEntity.status(FridgeSuccessCode.FRIDGE_MEMO_READ_LIST_SUCCESS.getStatus())
	            .body(ApiResponse.success(FridgeSuccessCode.FRIDGE_MEMO_READ_LIST_SUCCESS, memoList));
	}
	
	
	
	
	
	// 냉장고 작성
	@Operation(
	    summary = "냉장고 작성"
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "201",
				description = "냉장고 작성 성공",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeCreateSuccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "냉장고 작성 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeCreateFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "401",
				description = "로그인되지 않은 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeUnauthorizedResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "403",
				description = "권한 없는 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeForbiddenResponse.class))),
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
		    throw new ApiException(FridgeErrorCode.FRIDGE_UNAUTHORIZED);
		}
	
		// 냉장고 작성
		FridgeCreateResponseDto fridgeResponseDto = fridgeService.createFridge(fridgeRequestDto);
		
		return ResponseEntity.status(FridgeSuccessCode.FRIDGE_CREATE_SUCCESS.getStatus())
				.body(ApiResponse.success(FridgeSuccessCode.FRIDGE_CREATE_SUCCESS, fridgeResponseDto));
	}
	
	
	
	
	
	// 나의 냉장고 작성
	@Operation(
	    summary = "나의 냉장고 작성"
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "나의 냉장고 작성 성공",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeStorageCreateSuccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "나의 냉장고 작성 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeStorageCreateFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeStorageInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "401",
				description = "로그인되지 않은 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeUnauthorizedResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "403",
				description = "권한 없는 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeForbiddenResponse.class))),
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
	// 나의 냉장고 작성
	@PostMapping("/storages")
	public ResponseEntity<?> createUserFridge(
			@Parameter(description = "사용자 냉장고 작성 요청 정보") @RequestBody FridgeStorageCreateRequestDto fridgeRequestDto) {
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
		    throw new ApiException(FridgeErrorCode.FRIDGE_UNAUTHORIZED);
		}
		
		// 나의 냉장고 작성
		FridgeStorageCreateResponseDto fridgeResponseDto = fridgeService.createStorage(fridgeRequestDto);
		
		return ResponseEntity.status(FridgeSuccessCode.FRIDGE_STORAGE_CREATE_SUCCESS.getStatus())
				.body(ApiResponse.success(FridgeSuccessCode.FRIDGE_STORAGE_CREATE_SUCCESS, fridgeResponseDto));
	}
	
	
	

	
	// 장보기 메모 작성
	@Operation(
	    summary = "장보기 메모 작성"
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "냉장고 메모 작성 성공",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeStorageCreateSuccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "냉장고 메모 작성 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeStorageCreateFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeMemoInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "401",
				description = "로그인되지 않은 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeUnauthorizedResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "403",
				description = "권한 없는 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeForbiddenResponse.class))),
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
	// 장보기 메모 작성
	@PostMapping("/memos")
	public ResponseEntity<?> writeMemo(
			@Parameter(description = "장보기 메모 작성 요청 정보") @RequestBody MemoCreateRequestDto memoRequestDto) {
	    
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
		    throw new ApiException(FridgeErrorCode.FRIDGE_UNAUTHORIZED);
		}
		
		// 장보기 메모 작성
		MemoCreateResponseDto memoResponseDto = fridgeService.createMemo(memoRequestDto);
		
		return ResponseEntity.status(FridgeSuccessCode.FRIDGE_STORAGE_CREATE_SUCCESS.getStatus())
				.body(ApiResponse.success(FridgeSuccessCode.FRIDGE_STORAGE_CREATE_SUCCESS, memoResponseDto));
		
	}	
	
	
	
	
	
	// 냉장고 수정
	@Operation(
	    summary = "냉장고 수정"
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "200",
				description = "냉장고 수정 성공",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeUpdateSuccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "200",
				description = "냉장고 수정 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeUpdateFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "401",
				description = "로그인되지 않은 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeUnauthorizedResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "403",
				description = "권한 없는 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeForbiddenResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 냉장고를 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeNotFoundResponse.class))),
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
		    throw new ApiException(FridgeErrorCode.FRIDGE_UNAUTHORIZED);
		}
		
		// 냉장고 수정
		FridgeUpdateResponseDto fridgeResponseDto = fridgeService.updateFridge(fridgeRequestDto);
		
		return ResponseEntity.status(FridgeSuccessCode.FRIDGE_UPDATE_SUCCESS.getStatus())
				.body(ApiResponse.success(FridgeSuccessCode.FRIDGE_UPDATE_SUCCESS, fridgeResponseDto));
	}
	
	
	
	

	// 나의 냉장고 수정
	@Operation(
	    summary = "나의 냉장고 수정"
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "200",
				description = "나의 냉장고 수정 성공",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeStorageUpdateSuccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "나의 냉장고 수정 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeStorageUpdateFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeStorageInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "401",
				description = "로그인되지 않은 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeUnauthorizedResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "403",
				description = "권한 없는 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeForbiddenResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 나의 냉장고를 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeStorageNotFoundResponse.class))),
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
	// 나의 냉장고 수정
	@PatchMapping("/storages/{id}")
	public ResponseEntity<?> updateUserFridge(
			@Parameter(description = "냉장고의 일련번호") @PathVariable int id,
			@Parameter(description = "사용자 냉장고 수정 요청 정보") @RequestBody FridgeStorageUpdateRequestDto storageRequestDto) {
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
		    throw new ApiException(FridgeErrorCode.FRIDGE_UNAUTHORIZED);
		}
		
		// 나의 냉장고 수정
		FridgeStorageUpdateResponseDto storageResponseDto = fridgeService.updateStorage(storageRequestDto);
		
		return ResponseEntity.status(FridgeSuccessCode.FRIDGE_STORAGE_UPDATE_SUCCESS.getStatus())
				.body(ApiResponse.success(FridgeSuccessCode.FRIDGE_STORAGE_UPDATE_SUCCESS, storageResponseDto));
	}
	
	
	

	
	// 장보기 메모 수정
	@Operation(
	    summary = "장보기 메모 수정"
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "200",
				description = "장보기 메모 수정 성공",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeMemoUpdateSuccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "장보기 메모 수정 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeMemoUpdateFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeMemoInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "401",
				description = "로그인되지 않은 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeUnauthorizedResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "403",
				description = "권한 없는 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeForbiddenResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 장보기 메모를 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeMemoNotFoundResponse.class))),
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
	// 장보기 메모 수정
	@PatchMapping("/memos/{id}")
	public ResponseEntity<?> updateMemo (
			@Parameter(description = "메모의 일련번호") @PathVariable int id,
			@Parameter(description = "메모 수정 요청 정보") @RequestBody MemoUpdateRequestDto memoRequestDto) {
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
		    throw new ApiException(FridgeErrorCode.FRIDGE_UNAUTHORIZED);
		}
		
		// 장보기 메모 수정
		MemoUpdateResponseDto memoResponseDto = fridgeService.updateMemo(memoRequestDto);
		
		return ResponseEntity.status(FridgeSuccessCode.FRIDGE_MEMO_UPDATE_SUCCESS.getStatus())
				.body(ApiResponse.success(FridgeSuccessCode.FRIDGE_MEMO_UPDATE_SUCCESS, memoResponseDto));
	}
	
	
	
	

	// 냉장고 삭제
	@Operation(
	    summary = "냉장고 삭제"
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "200",
				description = "냉장고 삭제 성공",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeDeleteSuccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "200",
				description = "냉장고 삭제 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeDeleteFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "401",
				description = "로그인되지 않은 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeUnauthorizedResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "403",
				description = "권한 없는 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeForbiddenResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 냉장고를 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeNotFoundResponse.class))),
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
		    throw new ApiException(FridgeErrorCode.FRIDGE_UNAUTHORIZED);
		}
		
		// 냉장고 삭제
		fridgeService.deleteFridge(id);
		
		return ResponseEntity.status(FridgeSuccessCode.FRIDGE_DELETE_SUCCESS.getStatus())
				.body(ApiResponse.success(FridgeSuccessCode.FRIDGE_DELETE_SUCCESS, null));
	}
	
	
	
	
	
	// 나의 냉장고 삭제
	@Operation(
	    summary = "나의 냉장고 삭제"
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "200",
				description = "나의 냉장고 삭제 성공",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeStorageDeleteSuccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "나의 냉장고 삭제 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeStorageDeleteFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeStorageInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "401",
				description = "로그인되지 않은 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeUnauthorizedResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "403",
				description = "권한 없는 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeForbiddenResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 나의 냉장고를 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeStorageNotFoundResponse.class))),
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
	// 나의 냉장고 삭제
	@DeleteMapping("/storages/{id}")
	public ResponseEntity<?> deleteFridgeStorage(
			@Parameter(description = "냉장고의 일련번호") @PathVariable int id) {
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
		    throw new ApiException(FridgeErrorCode.FRIDGE_UNAUTHORIZED);
		}
		
		// 나의 냉장고 삭제
		fridgeService.deleteStorage(id);
		
		return ResponseEntity.status(FridgeSuccessCode.FRIDGE_STORAGE_DELETE_SUCCESS.getStatus())
				.body(ApiResponse.success(FridgeSuccessCode.FRIDGE_STORAGE_DELETE_SUCCESS, null));
	}
	
	
	
	
	
	// 장보기 메모 삭제
	@Operation(
	    summary = "장보기 메모 삭제"
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "200",
				description = "장보기 메모 삭제 성공",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeMemoDeleteSuccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "장보기 메모 삭제 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeMemoDeleteFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeMemoInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "401",
				description = "로그인되지 않은 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeUnauthorizedResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "403",
				description = "권한 없는 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeForbiddenResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "장보기 메모를 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeMemoNotFoundResponse.class))),
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
	// 장보기 메모 삭제
	@DeleteMapping("/memos/{id}")
	public ResponseEntity<?> deleteFridgeMemo(
			@Parameter(description = "메모의 일련번호") @PathVariable int id) {
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
		    throw new ApiException(FridgeErrorCode.FRIDGE_UNAUTHORIZED);
		}
		
		// 장보기 메모 삭제
		fridgeService.deleteMemo(id);
		
		return ResponseEntity.status(FridgeSuccessCode.FRIDGE_MEMO_DELETE_SUCCESS.getStatus())
				.body(ApiResponse.success(FridgeSuccessCode.FRIDGE_MEMO_DELETE_SUCCESS, null));
		
	}
	

	
	
	
	// 냉장고 좋아요
	@Operation(
	    summary = "냉장고 좋아요"
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "200",
				description = "냉장고 좋아요 성공",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeLikeSuccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "냉장고 좋아요 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeLikeFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "좋아요 작성 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = LikeCreateFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = LikeInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = LikeInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "401",
				description = "로그인되지 않은 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeUnauthorizedResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "403",
				description = "권한 없는 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeForbiddenResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 냉장고를 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeNotFoundResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 사용자를 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserNotFoundResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "409",
				description = "좋아요 중복 시도",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = LikeDuplicatedResponse.class))),
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
		    throw new ApiException(FridgeErrorCode.FRIDGE_UNAUTHORIZED);
		}
		
		LikeCreateResponseDto likeResponseDto = fridgeService.likeFridge(likeRequestDto);
		
		return ResponseEntity.status(FridgeSuccessCode.FRIDGE_LIKE_SUCCESS.getStatus())
				.body(ApiResponse.success(FridgeSuccessCode.FRIDGE_LIKE_SUCCESS, likeResponseDto));
	}
	
	
	
	
	
	// 냉장고 좋아요 취소
	@Operation(
	    summary = "냉장고 좋아요 취소"
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "200",
				description = "냉장고 좋아요 취소 성공",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeUnlikeSuccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "냉장고 좋아요 취소 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeUnlikeFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "좋아요 삭제 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = LikeDeleteFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = LikeInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "401",
				description = "로그인되지 않은 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeUnauthorizedResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "403",
				description = "권한 없는 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeForbiddenResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "403",
				description = "권한 없는 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = LikeForbiddenResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 냉장고를 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = FridgeNotFoundResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 좋아요를 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = LikeNotFoundResponse.class))),
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
	// 냉장고 좋아요 취소
	@DeleteMapping("/fridges/{id}/likes")
	public ResponseEntity<?> unlikeFridge(
			@Parameter(description = "냉장고의 일련번호") @PathVariable int id,
			@Parameter(description = "냉장고 좋아요 삭제 요청 정보") @RequestBody LikeDeleteRequestDto likeDto) {
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
		    throw new ApiException(FridgeErrorCode.FRIDGE_UNAUTHORIZED);
		}
		
		// 냉장고 좋아요 취소
		fridgeService.unlikeFridge(likeDto);
		
		return ResponseEntity.status(FridgeSuccessCode.FRIDGE_UNLIKE_SUCCESS.getStatus())
				.body(ApiResponse.success(FridgeSuccessCode.FRIDGE_UNLIKE_SUCCESS, null));
	}

}
