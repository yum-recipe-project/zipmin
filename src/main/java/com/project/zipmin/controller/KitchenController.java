package com.project.zipmin.controller;

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
import com.project.zipmin.api.KitchenErrorCode;
import com.project.zipmin.api.KitchenSuccessCode;
import com.project.zipmin.dto.UserReadResponseDto;
import com.project.zipmin.dto.kitchen.GuideCreateRequestDto;
import com.project.zipmin.dto.kitchen.GuideCreateResponseDto;
import com.project.zipmin.dto.kitchen.GuideReadResponseDto;
import com.project.zipmin.dto.kitchen.GuideUpdateRequestDto;
import com.project.zipmin.dto.kitchen.GuideUpdateResponseDto;
import com.project.zipmin.dto.like.LikeCreateRequestDto;
import com.project.zipmin.dto.like.LikeCreateResponseDto;
import com.project.zipmin.dto.like.LikeDeleteRequestDto;
import com.project.zipmin.entity.Role;
import com.project.zipmin.service.KitchenService;
import com.project.zipmin.service.UserService;
import com.project.zipmin.swagger.InternalServerErrorResponse;
import com.project.zipmin.swagger.UserInvalidInputResponse;
import com.project.zipmin.swagger.UserNotFoundResponse;
import com.project.zipmin.swagger.kitchen.KitchenCreateFailResponse;
import com.project.zipmin.swagger.kitchen.KitchenCreateSuccessResponse;
import com.project.zipmin.swagger.kitchen.KitchenDeleteFailResponse;
import com.project.zipmin.swagger.kitchen.KitchenDeleteSuccessResponse;
import com.project.zipmin.swagger.kitchen.KitchenForbiddenResponse;
import com.project.zipmin.swagger.kitchen.KitchenInvalidInputResponse;
import com.project.zipmin.swagger.kitchen.KitchenLikeFailResponse;
import com.project.zipmin.swagger.kitchen.KitchenLikeSuccessResponse;
import com.project.zipmin.swagger.kitchen.KitchenNotFoundResponse;
import com.project.zipmin.swagger.kitchen.KitchenReadListFailResponse;
import com.project.zipmin.swagger.kitchen.KitchenReadListSuccessResponse;
import com.project.zipmin.swagger.kitchen.KitchenReadSuccessResponse;
import com.project.zipmin.swagger.kitchen.KitchenUnauthorizedResponse;
import com.project.zipmin.swagger.kitchen.KitchenUnlikeFailResponse;
import com.project.zipmin.swagger.kitchen.KitchenUnlikeSuccessResponse;
import com.project.zipmin.swagger.kitchen.KitchenUpdateFailResponse;
import com.project.zipmin.swagger.kitchen.KitchenUpdateSuccessResponse;
import com.project.zipmin.swagger.like.LikeDeleteFailResponse;
import com.project.zipmin.swagger.like.LikeExistFailResponse;
import com.project.zipmin.swagger.like.LikeForbiddenResponse;
import com.project.zipmin.swagger.like.LikeInvalidInputResponse;
import com.project.zipmin.swagger.like.LikeNotFoundResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name = "Kitchen API", description = "키친가이드 관련 API")
public class KitchenController {
	
	private final KitchenService kitchenService;
	private final UserService userService;

	
	
	
	
	// 키친가이드 목록 조회
	@Operation(
		summary = "키친가이드 목록 조회"
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "200",
				description = "키친가이드 목록 조회 성공",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = KitchenReadListSuccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "키친가이드 목록 조회 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = KitchenReadListFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "좋아요 여부 확인 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = LikeExistFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = KitchenInvalidInputResponse.class))),
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
	// 키친가이드 목록 조회
	@GetMapping("/guides")
	public ResponseEntity<?> listGuide(
			@Parameter(description = "검색어", required = false) @RequestParam(required = false) String keyword, 
			@Parameter(description = "카테고리", required = false) @RequestParam(required = false) String category,
			@Parameter(description = "정렬", required = false) @RequestParam(required = false) String sort,
			@Parameter(description = "페이지 번호") @RequestParam int page,
			@Parameter(description = "페이지 크기") @RequestParam int size) {
		
		Pageable pageable = PageRequest.of(page, size);
		Page<GuideReadResponseDto> guidePage = null;
		
		guidePage = kitchenService.readGuidePage(keyword, category, sort, pageable);
		
		return ResponseEntity.status(KitchenSuccessCode.KITCHEN_READ_LIST_SUCCESS.getStatus())
				.body(ApiResponse.success(KitchenSuccessCode.KITCHEN_READ_LIST_SUCCESS, guidePage));
	}
	
	
	
	
	
	// 키친가이드 상세 조회
	@Operation(
		summary = "키친가이드 상세 조회"
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "200",
				description = "키친가이드 상세 조회 성공",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = KitchenReadSuccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "가이드 상세 조회 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = KitchenNotFoundResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "좋아요 여부 확인 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = LikeExistFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = KitchenInvalidInputResponse.class))),
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
				responseCode = "404",
				description = "해당 키친가이드를 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = KitchenNotFoundResponse.class))),
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
	// 키친가이드 조회
	@GetMapping("/guides/{id}")
	public ResponseEntity<?> viewGuide(
			@Parameter(description = "키친가이드의 일련번호") @PathVariable int id) {
		
		GuideReadResponseDto guide = kitchenService.readGuideById(id);		
		
		return ResponseEntity.status(KitchenSuccessCode.KITCHEN_READ_SUCCESS.getStatus())
				.body(ApiResponse.success(KitchenSuccessCode.KITCHEN_READ_SUCCESS, guide));
	}
	
	

	
	
	// 키친가이드 작성 (관리자)
	@Operation(
		summary = "키친가이드 작성"
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "201",
				description = "가이드 작성 성공",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = KitchenCreateSuccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "키친가이드 작성 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = KitchenCreateFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = KitchenInvalidInputResponse.class))),
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
						schema = @Schema(implementation = KitchenUnauthorizedResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "403",
				description = "권한 없는 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = KitchenForbiddenResponse.class))),
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
						schema = @Schema(implementation = KitchenCreateFailResponse.class)))
	})
	// 키친가이드 작성 (관리자)
	@PostMapping("/guides")
	public ResponseEntity<?> writeGuide(
			@Parameter(description = "키친가이드 작성 요청 정보") @RequestBody GuideCreateRequestDto guideRequestDto) {
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
			throw new ApiException(KitchenErrorCode.KITCHEN_UNAUTHORIZED);
		}
		
		// 권한 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserReadResponseDto currentUser = userService.readUserByUsername(username);
		
		if (!currentUser.getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			if (!currentUser.getRole().equals(Role.ROLE_ADMIN.name())) {
				throw new ApiException(KitchenErrorCode.KITCHEN_FORBIDDEN);
			}
		}
		guideRequestDto.setUserId(currentUser.getId());

		GuideCreateResponseDto guideResponseDto = kitchenService.createGuide(guideRequestDto);

		return ResponseEntity.status(KitchenSuccessCode.KITCHEN_CREATE_SUCCESS.getStatus())
				.body(ApiResponse.success(KitchenSuccessCode.KITCHEN_CREATE_SUCCESS, guideResponseDto));
	}
	
	
	
	
	
	// 키친가이드 수정 (관리자)
	@Operation(
		summary = "키친가이드 수정"
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "200",
				description = "가이드 수정 성공",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = KitchenUpdateSuccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "가이드 수정 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = KitchenUpdateFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = KitchenInvalidInputResponse.class))),
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
						schema = @Schema(implementation = KitchenUnauthorizedResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "403",
				description = "권한 없는 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = KitchenForbiddenResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 키친가이드를 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = KitchenNotFoundResponse.class))),
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
	// 키친가이드 수정 (관리자)
	@PatchMapping("/guides/{id}")
	public ResponseEntity<?> editGuide(
			@Parameter(description = "키친가이드의 일련번호") @PathVariable int id,
			@Parameter(description = "키친가이드 수정 요청 정보") @RequestBody GuideUpdateRequestDto guideRequestDto) {
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
			throw new ApiException(KitchenErrorCode.KITCHEN_UNAUTHORIZED);
		}
		
		GuideUpdateResponseDto guideResponseDto = kitchenService.updateGuide(guideRequestDto);
		
		return ResponseEntity.status(KitchenSuccessCode.KITCHEN_UPDATE_SUCCESS.getStatus())
				.body(ApiResponse.success(KitchenSuccessCode.KITCHEN_UPDATE_SUCCESS, guideResponseDto));
	}
	
	
	

	
	// 키친가이드 삭제 (관리자)
	@Operation(
		summary = "가이드 삭제"
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "200",
				description = "가이드 삭제 성공",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = KitchenDeleteSuccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "가이드 삭제 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = KitchenDeleteFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = KitchenInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "401",
				description = "로그인되지 않은 사용자 접근",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = KitchenUnauthorizedResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "403",
				description = "권한 없는 사용자의 접근",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = KitchenForbiddenResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 키친가이드를 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = KitchenNotFoundResponse.class))),
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
	// 키친가이드 삭제 (관리자)
	@DeleteMapping("/guides/{id}")
	public ResponseEntity<?> deleteGuide(
			@Parameter(description = "키친가이드의 일련번호") @PathVariable int id) {
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
			throw new ApiException(KitchenErrorCode.KITCHEN_UNAUTHORIZED);
		}
		
		kitchenService.deleteGuide(id);
		
		return ResponseEntity.status(KitchenSuccessCode.KITCHEN_DELETE_SUCCESS.getStatus())
				.body(ApiResponse.success(KitchenSuccessCode.KITCHEN_DELETE_SUCCESS, null));
	}


	

	
	// 키친가이드 좋아요
	@Operation(
		summary = "키친가이드 좋아요"
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "200",
				description = "가이드 좋아요 성공",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = KitchenLikeSuccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "가이드 좋아요 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = KitchenLikeFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = KitchenInvalidInputResponse.class))),
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
				description = "로그인되지 않은 사용자 접근",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = KitchenUnauthorizedResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "403",
				description = "권한 없는 사용자의 접근",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = LikeForbiddenResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 키친가이드를 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = KitchenNotFoundResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "500",
				description = "서버 내부 오류",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = InternalServerErrorResponse.class)))
	})
	// 키친가이드 좋아요
	@PostMapping("/guides/{id}/likes")
	public ResponseEntity<?> likeGuide(
			@Parameter(description = "키친가이드의 일련번호") @PathVariable int id,
			@Parameter(description = "좋아요 작성 요청 정보") @RequestBody LikeCreateRequestDto likeRequestDto) {
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
			throw new ApiException(KitchenErrorCode.KITCHEN_UNAUTHORIZED);
		}
		likeRequestDto.setUserId(userService.readUserByUsername(authentication.getName()).getId());
		
		LikeCreateResponseDto likeResponseDto = kitchenService.likeGuide(likeRequestDto);
					
		return ResponseEntity.status(KitchenSuccessCode.KITCHEN_LIKE_SUCCESS.getStatus())
				.body(ApiResponse.success(KitchenSuccessCode.KITCHEN_LIKE_SUCCESS, likeResponseDto));
	}

	
	

	
	// 키친가이드 좋아요 취소
	@Operation(
		summary = "키친가이드 좋아요 취소"
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "200",
				description = "가이드 좋아요 취소 성공",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = KitchenUnlikeSuccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "가이드 좋아요 취소 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = KitchenUnlikeFailResponse.class))),
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
						schema = @Schema(implementation = KitchenInvalidInputResponse.class))),
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
				description = "로그인 되지 않은 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = KitchenUnauthorizedResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "403",
				description = "권한 없는 사용자의 접근",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = LikeForbiddenResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 키친가이드를 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = KitchenNotFoundResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 좋아요를 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = LikeNotFoundResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "500",
				description = "서버 내부 오류",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = InternalServerErrorResponse.class)))
	})
	// 키친가이드 좋아요 취소
	@DeleteMapping("/guides/{guideId}/likes")
	public ResponseEntity<?> unlikeGuide(
			@Parameter(description = "키친가이드의 일련번호") @PathVariable int guideId,
			@Parameter(description = "좋아요 삭제 요청 정보") @RequestBody LikeDeleteRequestDto likeDto) {

		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
			throw new ApiException(KitchenErrorCode.KITCHEN_UNAUTHORIZED);
		}
		likeDto.setUserId(userService.readUserByUsername(authentication.getName()).getId());

		kitchenService.unlikeGuide(likeDto);

		return ResponseEntity.status(KitchenSuccessCode.KITCHEN_UNLIKE_SUCCESS.getStatus())
				.body(ApiResponse.success(KitchenSuccessCode.KITCHEN_UNLIKE_SUCCESS, null));
	}
	
	
	
	
	
	// 사용자가 저장한 키친가이드 목록 조회
	@Operation(
		summary = "사용자가 저장한 키친가이드 목록 조회"
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "200",
				description = "키친가이드 목록 조회 성공",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = KitchenReadListSuccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "키친가이드 목록 조회 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = KitchenReadListFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = KitchenInvalidInputResponse.class))),
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
						schema = @Schema(implementation = KitchenUnauthorizedResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "403",
				description = "권한 없는 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = KitchenForbiddenResponse.class))),
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
	// 사용자가 저장한 키친가이드 목록 조회
	@GetMapping("/users/{id}/guides/likes")
	public ResponseEntity<?> readUserSavedGuideList(
			@Parameter(description = "사용자의 일련번호") @PathVariable int id,
			@Parameter(description = "페이지 번호") @RequestParam int page,
			@Parameter(description = "페이지 크기") @RequestParam int size) {
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
			throw new ApiException(KitchenErrorCode.KITCHEN_UNAUTHORIZED);
		}
		
		// 권한 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserReadResponseDto currentUser = userService.readUserByUsername(username);
		
		if (!currentUser.getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			if (currentUser.getRole().equals(Role.ROLE_ADMIN.name())) {
				if (userService.readUserById(id).getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
					throw new ApiException(KitchenErrorCode.KITCHEN_FORBIDDEN);
				}
				if (userService.readUserById(id).getRole().equals(Role.ROLE_ADMIN.name()) && currentUser.getId() != id) {
					throw new ApiException(KitchenErrorCode.KITCHEN_FORBIDDEN);
				}
			}
			else if (currentUser.getRole().equals(Role.ROLE_USER.name()) && currentUser.getId() != id) {
				throw new ApiException(KitchenErrorCode.KITCHEN_FORBIDDEN);
			}
		}
		
		Pageable pageable = PageRequest.of(page, size);
		Page<GuideReadResponseDto> guidePage = kitchenService.readLikedGuidePageByUserId(id, pageable);
		
		return ResponseEntity.status(KitchenErrorCode.KITCHEN_READ_LIST_FAIL.getStatus())
				.body(ApiResponse.success(KitchenErrorCode.KITCHEN_READ_LIST_FAIL, guidePage));
	}
	
}
