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
import com.project.zipmin.swagger.UserInvalidInputResponse;
import com.project.zipmin.swagger.UserNotFoundResponse;

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
	
	
	
	
	
	@Operation(
	    summary = "냉장고 목록 조회"
	)
	@ApiResponses(value = {
		// 200 FRIDGE_READ_LIST_SUCCESS
		// 400 FRIDGE_READ_LIST_FAIL
		// 400 FRIDGE_INVALID_INPUT
		// 400 USER_INVALID_INPUT
		// 404 USER_NOT_FOUND
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
	
	
	
	
	
	@Operation(
	    summary = "사용자의 냉장고 목록 조회"
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
		// 401 FRIDGE_UNAUTHORIZED
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
		// 401 FRIDGE_UNAUTHORIZED
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
	
	
	
	
	
	@Operation(
	    summary = "나의 냉장고 목록 조회"
	)
	@ApiResponses(value = {
		// 200 FRIDGE_STORAGE_READ_LIST_SUCCESS
		// 400 FRIDGE_STORAGE_READ_LIST_FAIL
		// 400 FRIDGE_STORAGE_INVALID_INPUT
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserInvalidInputResponse.class))),
		// 401 FRIDGE_UNAUTHORIZED
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
	
	
	
	
	// FRIDGE_PICK_LIST_SUCCESS
	// FRIDGE_STORAGE_READ_LIST_FAIL
	// FRIDGE_UNAUTHORIZED
	// USER_INVALID_INPUT
	// USER_NOT_FOUND
	// USER_FRDIGE_INVALID_INPUT
	// RECIPE_READ_LIST_FAIL
	// RECIPE_STOCK_READ_LIST_FAIL
	
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
	
	// FRIDGE_MEMO_READ_LIST_SUCCESS
	// FRIDGE_MEMO_READ_LIST_FAIL
	// FRIDGE_UNAUTHORIZED
	// FRIDGE_FORBIDDEN
	// 
	
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
		// 401 FRIDGE_UNAUTHORIZED
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
	
	
	
	
	
	// FRIDGE_STORAGE_CREATE_SUCCESS
	// FRIDGE_STORAGE_CREATE_FAIL
	// FRIDGE_STORAGE_INVALID_INPUT
	// USER_INVALID_INPUT
	// FRIDGE_UNAUTHORIZED
	// FRIDGE_FORBIDDEN
	// USER_NOT_FOUND
	
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
		// 401 FRIDGE_UNAUTHORIZED
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
		    throw new ApiException(FridgeErrorCode.FRIDGE_UNAUTHORIZED);
		}
		
		// 냉장고 수정
		FridgeUpdateResponseDto fridgeResponseDto = fridgeService.updateFridge(fridgeRequestDto);
		
		return ResponseEntity.status(FridgeSuccessCode.FRIDGE_UPDATE_SUCCESS.getStatus())
				.body(ApiResponse.success(FridgeSuccessCode.FRIDGE_UPDATE_SUCCESS, fridgeResponseDto));
	}
	
	
	
	
	
	// FRIDGE_STORAGE_UPDATE_FAIL
	// FRIDGE_STORAGE_INVALID_INPUT
	// USER_INVALID_INPUT
	// FRIDGE_UNAUTHORIZED
	// FRIDGE_FORBIDDEN
	// FRIDGE_STORAGE_NOT_FOUND
	// USER_NOT_FOUND
	// INTERNAL_SERVER_ERROR
	
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
		// 401 FRIDGE_UNAUTHORIZED
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
		    throw new ApiException(FridgeErrorCode.FRIDGE_UNAUTHORIZED);
		}
		
		// 냉장고 삭제
		fridgeService.deleteFridge(id);
		
		return ResponseEntity.status(FridgeSuccessCode.FRIDGE_DELETE_SUCCESS.getStatus())
				.body(ApiResponse.success(FridgeSuccessCode.FRIDGE_DELETE_SUCCESS, null));
	}
	
	
	
	
	
	
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
		// 401 FRIDGE_UNAUTHORIZED
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
		    throw new ApiException(FridgeErrorCode.FRIDGE_UNAUTHORIZED);
		}
		
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
	// FRIDGE_UNAUTHORIZED
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
		    throw new ApiException(FridgeErrorCode.FRIDGE_UNAUTHORIZED);
		}
		
		fridgeService.unlikeFridge(likeDto);
		
		return ResponseEntity.status(FridgeSuccessCode.FRIDGE_UNLIKE_SUCCESS.getStatus())
				.body(ApiResponse.success(FridgeSuccessCode.FRIDGE_UNLIKE_SUCCESS, null));
	}

}
