package com.project.zipmin.controller;

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
import com.project.zipmin.api.KitchenErrorCode;
import com.project.zipmin.api.KitchenSuccessCode;
import com.project.zipmin.api.UserErrorCode;
import com.project.zipmin.api.UserSuccessCode;
import com.project.zipmin.dto.GuideCreateRequestDto;
import com.project.zipmin.dto.GuideCreateResponseDto;
import com.project.zipmin.dto.GuideReadMySavedResponseDto;
import com.project.zipmin.dto.GuideReadResponseDto;
import com.project.zipmin.dto.GuideUpdateRequestDto;
import com.project.zipmin.dto.GuideUpdateResponseDto;
//import com.project.zipmin.dto.GuideResponseDTO;
import com.project.zipmin.dto.LikeCreateRequestDto;
import com.project.zipmin.dto.LikeCreateResponseDto;
import com.project.zipmin.dto.LikeDeleteRequestDto;
import com.project.zipmin.entity.Role;
import com.project.zipmin.service.KitchenService;
import com.project.zipmin.service.LikeService;
import com.project.zipmin.service.UserService;
import com.project.zipmin.swagger.GuideCreateFailResponse;
import com.project.zipmin.swagger.GuideCreateSuccessResponse;
import com.project.zipmin.swagger.GuideDeleteFailResponse;
import com.project.zipmin.swagger.GuideDeleteSuccessResponse;
import com.project.zipmin.swagger.GuideForbiddenResponse;
import com.project.zipmin.swagger.GuideInvalidInputResponse;
import com.project.zipmin.swagger.GuideLikeFailResponse;
import com.project.zipmin.swagger.GuideLikeSuccessResponse;
import com.project.zipmin.swagger.GuideNotFoundResponse;
import com.project.zipmin.swagger.GuideReadListFailResponse;
import com.project.zipmin.swagger.GuideReadListSuccessResponse;
import com.project.zipmin.swagger.GuideReadMySavedListFailResponse;
import com.project.zipmin.swagger.GuideReadMySavedListSuccessResponse;
import com.project.zipmin.swagger.GuideReadSuccessResponse;
import com.project.zipmin.swagger.GuideUnauthorizedAccessResponse;
import com.project.zipmin.swagger.GuideUnlikeFailResponse;
import com.project.zipmin.swagger.GuideUnlikeSuccessResponse;
import com.project.zipmin.swagger.GuideUpdateFailResponse;
import com.project.zipmin.swagger.GuideUpdateSuccessResponse;
import com.project.zipmin.swagger.InternalServerErrorResponse;
import com.project.zipmin.swagger.LikeCountFailResponse;
import com.project.zipmin.swagger.LikeDeleteFailResponse;
import com.project.zipmin.swagger.LikeExistFailResponse;
import com.project.zipmin.swagger.LikeForbiddenResponse;
import com.project.zipmin.swagger.LikeInvalidInputResponse;
import com.project.zipmin.swagger.LikeNotFoundResponse;
import com.project.zipmin.swagger.UserInvalidInputResponse;
import com.project.zipmin.swagger.UserNotFoundResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "KITCHEN API", description = "키친가이드 관련 API")
public class KitchenController {
	
	@Autowired
	KitchenService kitchenService;
	
	@Autowired
	LikeService likeService;
	
	@Autowired
	UserService userService;

	@Operation(
	    summary = "키친가이드 목록 조회"
	)
	@ApiResponses(value = {
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "200",
	            description = "가이드 목록 조회 성공",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = GuideReadListSuccessResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "가이드 목록 조회 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = GuideReadListFailResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "400",
	            description = "입력값이 유효하지 않음",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = GuideInvalidInputResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "500",
				description = "서버 내부 오류",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = InternalServerErrorResponse.class)))
		
	})
	// 가이드 목록 조회
	@GetMapping("/guides")
	public ResponseEntity<?> listGuide(
			@Parameter(description = "카테고리") @RequestParam(required = false) String category,
			@Parameter(description = "검색어") @RequestParam(required = false) String keyword, 
			@Parameter(description = "정렬") @RequestParam(required = false) String sort,
			@Parameter(description = "페이지 번호") @RequestParam int page,
			@Parameter(description = "페이지 크기") @RequestParam int size) {
		System.err.println("컨트롤러 진입");
		
		Pageable pageable = PageRequest.of(page, size);
		Page<GuideReadResponseDto> guidePage = null;
		
		guidePage = kitchenService.readGuidePage(category, keyword, sort, pageable);
		
        return ResponseEntity.status(KitchenSuccessCode.KITCHEN_READ_LIST_SUCCESS.getStatus())
                .body(ApiResponse.success(KitchenSuccessCode.KITCHEN_READ_LIST_SUCCESS, guidePage));
	}
	
	

	// 가이드 조회
	@Operation(
	    summary = "키친가이드 상세 조회"
	)
	@ApiResponses(value = {
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "200",
	            description = "가이드 상세 조회 성공",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = GuideReadSuccessResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "400",
	            description = "입력값이 유효하지 않음",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = GuideInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "좋아요 집계 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = LikeCountFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "좋아요 여부 확인 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = LikeExistFailResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "404",
	            description = "가이드 상세 조회 실패",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = GuideNotFoundResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "500",
	            description = "서버 내부 오류",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = InternalServerErrorResponse.class)))
	})
	// 가이드 조회
	@GetMapping("/guides/{id}")
	public ResponseEntity<?> viewGuide(
			@Parameter(description = "가이드 일련번호") @PathVariable int id) {
		GuideReadResponseDto guide = kitchenService.readGuideById(id);	    
	    
	    return ResponseEntity.status(KitchenSuccessCode.KITCHEN_READ_SUCCESS.getStatus())
	            .body(ApiResponse.success(KitchenSuccessCode.KITCHEN_READ_SUCCESS, guide));
	}


	
	
	@Operation(
	    summary = "새 키친가이드 등록 (관리자 전용)"
	)
	@ApiResponses(value = {
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "200",
	            description = "가이드 등록 성공",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = GuideCreateSuccessResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "400",
	            description = "입력값이 유효하지 않음",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = GuideInvalidInputResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "401",
	            description = "로그인되지 않은 사용자 접근",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = GuideUnauthorizedAccessResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "403",
	            description = "관리자 권한 없음",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = GuideForbiddenResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "500",
	            description = "가이드 등록 실패 (서버 내부 오류)",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = GuideCreateFailResponse.class)))
	})
	// 새 가이드 등록 (관리자)
	@PostMapping("/guides")
	public ResponseEntity<?> writeGuide(
			@Parameter(description = "키친가이드 작성 요청 정보") @RequestBody GuideCreateRequestDto guideRequestDto) {
	    
	    // 로그인 여부 확인
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
	        throw new ApiException(KitchenErrorCode.KITCHEN_UNAUTHORIZED_ACCESS);
	    }

	    GuideCreateResponseDto guideResponseDto = kitchenService.createGuide(guideRequestDto);

	    return ResponseEntity.status(KitchenSuccessCode.KITCHEN_CREATE_SUCCESS.getStatus())
	            .body(ApiResponse.success(KitchenSuccessCode.KITCHEN_CREATE_SUCCESS, guideResponseDto));
	}
	
	
	
	@Operation(
		    summary = "특정 가이드 수정 (관리자 전용)"
		)
		@ApiResponses(value = {
		    @io.swagger.v3.oas.annotations.responses.ApiResponse(
		            responseCode = "200",
		            description = "가이드 수정 성공",
		            content = @Content(
		                    mediaType = "application/json",
		                    schema = @Schema(implementation = GuideUpdateSuccessResponse.class))),
		    @io.swagger.v3.oas.annotations.responses.ApiResponse(
					responseCode = "400",
					description = "가이드 수정 실패",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = GuideUpdateFailResponse.class))),
		    @io.swagger.v3.oas.annotations.responses.ApiResponse(
		            responseCode = "400",
		            description = "입력값이 유효하지 않음",
		            content = @Content(
		                    mediaType = "application/json",
		                    schema = @Schema(implementation = GuideInvalidInputResponse.class))),
		    @io.swagger.v3.oas.annotations.responses.ApiResponse(
		            responseCode = "401",
		            description = "로그인되지 않은 사용자 접근",
		            content = @Content(
		                    mediaType = "application/json",
		                    schema = @Schema(implementation = GuideUnauthorizedAccessResponse.class))),
		    @io.swagger.v3.oas.annotations.responses.ApiResponse(
		            responseCode = "403",
		            description = "권한 없는 사용자의 접근",
		            content = @Content(
		                    mediaType = "application/json",
		                    schema = @Schema(implementation = GuideForbiddenResponse.class))),
		    @io.swagger.v3.oas.annotations.responses.ApiResponse(
		            responseCode = "404",
		            description = "해당 키친가이드를 찾을 수 없음",
		            content = @Content(
		                    mediaType = "application/json",
		                    schema = @Schema(implementation = GuideNotFoundResponse.class))),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(
					responseCode = "500",
					description = "서버 내부 오류",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = InternalServerErrorResponse.class)))
		})
	// 특정 가이드 수정 (관리자)
	@PatchMapping("/guides/{id}")
	public ResponseEntity<?> editGuide(
			@Parameter(description = "키친가이드의 일련번호") @PathVariable int id,
			@Parameter(description = "키친가이드 수정 요청 정보") @RequestBody GuideUpdateRequestDto guideRequestDto) {
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
		    throw new ApiException(KitchenErrorCode.KITCHEN_UNAUTHORIZED_ACCESS);
		}
		
		GuideUpdateResponseDto guideResponseDto = kitchenService.updateGuide(guideRequestDto);
		
		return ResponseEntity.status(KitchenSuccessCode.KITCHEN_UPDATE_SUCCESS.getStatus())
				.body(ApiResponse.success(KitchenSuccessCode.KITCHEN_UPDATE_SUCCESS, guideResponseDto));
	}
	
	
	

	@Operation(
	    summary = "가이드 삭제 (관리자 전용)"
	)
	@ApiResponses(value = {
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "200",
	            description = "가이드 삭제 성공",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = GuideDeleteSuccessResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "400",
	            description = "가이드 삭제 실패",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = GuideDeleteFailResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "400",
	            description = "입력값이 유효하지 않음",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = GuideInvalidInputResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "401",
	            description = "로그인되지 않은 사용자 접근",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = GuideUnauthorizedAccessResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "403",
	            description = "권한 없는 사용자의 접근",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = GuideForbiddenResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "404",
	            description = "해당 가이드를 찾을 수 없음",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = GuideNotFoundResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "500",
	            description = "서버 내부 오류",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = InternalServerErrorResponse.class)))
	})
	// 특정 가이드 삭제 (관리자)
	@DeleteMapping("/guides/{id}")
	public ResponseEntity<?> deleteGuide(
			@Parameter(description = "키친가이드의 일련번호") @PathVariable int id) {
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
		    throw new ApiException(KitchenErrorCode.KITCHEN_UNAUTHORIZED_ACCESS);
		}
		
		kitchenService.deleteGuide(id);
		
		
		return ResponseEntity.status(KitchenSuccessCode.KITCHEN_DELETE_SUCCESS.getStatus())
				.body(ApiResponse.success(KitchenSuccessCode.KITCHEN_DELETE_SUCCESS, null));
	}


	@Operation(
	    summary = "가이드 좋아요 (저장)"
	)
	@ApiResponses(value = {
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "200",
	            description = "가이드 좋아요 성공",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = GuideLikeSuccessResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "400",
	            description = "가이드 좋아요 실패",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = GuideLikeFailResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "400",
	            description = "입력값이 유효하지 않음",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = GuideInvalidInputResponse.class))),
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
	                    schema = @Schema(implementation = GuideUnauthorizedAccessResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "403",
				description = "권한 없는 사용자의 접근",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = LikeForbiddenResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "404",
	            description = "해당 가이드를 찾을 수 없음",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = GuideNotFoundResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "500",
	            description = "서버 내부 오류",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = InternalServerErrorResponse.class)))
	})

	// 특정 가이드 좋아요 (저장)
	@PostMapping("/guides/{id}/likes")
	public ResponseEntity<?> likeGuide(
			@Parameter(description = "키친가이드의 일련번호") @PathVariable("id") int guideId,
			@Parameter(description = "좋아요 작성 요청 정보") @RequestBody LikeCreateRequestDto likeRequestDto) {
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
		    throw new ApiException(KitchenErrorCode.KITCHEN_UNAUTHORIZED_ACCESS);
		}
		
		likeRequestDto.setUserId(userService.readUserByUsername(authentication.getName()).getId());
		LikeCreateResponseDto likeResponseDto = kitchenService.likeGuide(likeRequestDto);
					
		return ResponseEntity.status(KitchenSuccessCode.KITCHEN_LIKE_SUCCESS.getStatus())
				.body(ApiResponse.success(KitchenSuccessCode.KITCHEN_LIKE_SUCCESS, likeResponseDto));
	}

	
	
	
	@Operation(
		    summary = "가이드 좋아요 취소"
		)
		@ApiResponses(value = {
		    @io.swagger.v3.oas.annotations.responses.ApiResponse(
		            responseCode = "200",
		            description = "가이드 좋아요 취소 성공",
		            content = @Content(
		                    mediaType = "application/json",
		                    schema = @Schema(implementation = GuideUnlikeSuccessResponse.class))),
		    @io.swagger.v3.oas.annotations.responses.ApiResponse(
		            responseCode = "400",
		            description = "가이드 좋아요 취소 실패",
		            content = @Content(
		                    mediaType = "application/json",
		                    schema = @Schema(implementation = GuideUnlikeFailResponse.class))),
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
		                    schema = @Schema(implementation = GuideInvalidInputResponse.class))),
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
		                    schema = @Schema(implementation = GuideUnauthorizedAccessResponse.class))),
		    @io.swagger.v3.oas.annotations.responses.ApiResponse(
		            responseCode = "403",
		            description = "권한 없는 사용자의 접근",
		            content = @Content(
		                    mediaType = "application/json",
		                    schema = @Schema(implementation = GuideForbiddenResponse.class))),
		    @io.swagger.v3.oas.annotations.responses.ApiResponse(
		            responseCode = "404",
		            description = "해당 좋아요를 찾을 수 없음",
		            content = @Content(
		                    mediaType = "application/json",
		                    schema = @Schema(implementation = LikeNotFoundResponse.class))),
		    @io.swagger.v3.oas.annotations.responses.ApiResponse(
		            responseCode = "404",
		            description = "해당 가이드를 찾을 수 없음",
		            content = @Content(
		                    mediaType = "application/json",
		                    schema = @Schema(implementation = GuideNotFoundResponse.class))),
		    @io.swagger.v3.oas.annotations.responses.ApiResponse(
		            responseCode = "500",
		            description = "서버 내부 오류",
		            content = @Content(
		                    mediaType = "application/json",
		                    schema = @Schema(implementation = InternalServerErrorResponse.class)))
		})
	// 특정 가이드 좋아요 취소
	@DeleteMapping("/guides/{id}/likes")
	public ResponseEntity<?> unlikeGuide(
			@Parameter(description = "키친가이드의 일련번호") @PathVariable("id") int guideId,
			@Parameter(description = "키친가이드 좋아요 삭제 요청 정보") @RequestBody LikeDeleteRequestDto likeDto) {

	    // 로그인 여부 확인
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
	        throw new ApiException(KitchenErrorCode.KITCHEN_UNAUTHORIZED_ACCESS);
	    }

	    likeDto.setUserId(userService.readUserByUsername(authentication.getName()).getId());

	    // 서비스 호출
	    kitchenService.unlikeGuide(likeDto);

	    return ResponseEntity.status(KitchenSuccessCode.KITCHEN_UNLIKE_SUCCESS.getStatus())
	            .body(ApiResponse.success(KitchenSuccessCode.KITCHEN_UNLIKE_SUCCESS, null));
	}
	
	
	
	
	@Operation(
	    summary = "사용자가 저장한 가이드 목록 조회"
	)
	@ApiResponses(value = {
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "200",
	            description = "저장한 가이드 목록 조회 성공",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = GuideReadMySavedListSuccessResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "400",
	            description = "저장한 가이드 목록 조회 실패",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = GuideReadMySavedListFailResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "400",
	            description = "입력값이 유효하지 않음",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = GuideInvalidInputResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "401",
	            description = "로그인되지 않은 사용자 접근",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = GuideUnauthorizedAccessResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "403",
	            description = "권한 없는 사용자의 접근",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = GuideForbiddenResponse.class))),
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
	// 저장한(좋아요를 누른) 키친가이드
	@GetMapping("/users/{id}/likes/guides")
	public ResponseEntity<?> readUserSavedGuideList(
			@Parameter(description = "사용자의 일련번호") @PathVariable Integer id,
			@Parameter(description = "페이지 번호") @RequestParam int page,
			@Parameter(description = "페이지 크기") @RequestParam int size) {
		
		// 입력값 검증
		if (id == null) {
			throw new ApiException(UserErrorCode.USER_INVALID_INPUT);
		}
		
		// 인증 여부 확인 (비로그인)
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
		    throw new ApiException(UserErrorCode.USER_UNAUTHORIZED_ACCESS);
		}
		
		// 로그인 정보
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		// 본인 확인
		if (!userService.readUserById(id).getRole().equals(Role.ROLE_ADMIN.name())) {
			if (id != userService.readUserByUsername(username).getId()) {
				throw new ApiException(UserErrorCode.USER_FORBIDDEN);
			}
		}
		
		Pageable pageable = PageRequest.of(page, size);
		Page<GuideReadMySavedResponseDto> savedGuidePage = kitchenService.readSavedGuidePageByUserId(id, pageable);
		
		return ResponseEntity.status(UserSuccessCode.USER_READ_LIST_SUCCESS.getStatus())
				.body(ApiResponse.success(UserSuccessCode.USER_READ_LIST_SUCCESS, savedGuidePage));
	}
		
	
}
