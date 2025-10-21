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
import org.springframework.web.bind.annotation.RestController;

import com.project.zipmin.api.ApiException;
import com.project.zipmin.api.ApiResponse;
import com.project.zipmin.api.MemoErrorCode;
import com.project.zipmin.api.MemoSuccessCode;
import com.project.zipmin.api.UserErrorCode;
import com.project.zipmin.dto.MemoCreateRequestDto;
import com.project.zipmin.dto.MemoCreateResponseDto;
import com.project.zipmin.dto.MemoReadResponseDto;
import com.project.zipmin.dto.MemoUpdateRequestDto;
import com.project.zipmin.dto.MemoUpdateResponseDto;
import com.project.zipmin.dto.RecipeStockMemoCreateRequestDto;
import com.project.zipmin.entity.Role;
import com.project.zipmin.service.MemoService;
import com.project.zipmin.service.UserService;
import com.project.zipmin.swagger.InternalServerErrorResponse;
import com.project.zipmin.swagger.MemoCreateFailResponse;
import com.project.zipmin.swagger.MemoCreateSuccessResponse;
import com.project.zipmin.swagger.MemoDeleteFailResponse;
import com.project.zipmin.swagger.MemoDeleteSuccessResponse;
import com.project.zipmin.swagger.MemoForbiddenResponse;
import com.project.zipmin.swagger.MemoInvalidInputResponse;
import com.project.zipmin.swagger.MemoNotFoundResponse;
import com.project.zipmin.swagger.MemoReadListSuccessResponse;
import com.project.zipmin.swagger.MemoUnauthorizedAccessResponse;
import com.project.zipmin.swagger.MemoUpdateFailResponse;
import com.project.zipmin.swagger.MemoUpdateSuccessResponse;
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
@Tag(name = "MEMO API", description = "장보기메모 관련 API")
public class MemoController {
	
	private final UserService userService;
	private final MemoService memoService;	
	
	@Operation(summary = "장보기 메모 목록 조회")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "메모 목록 조회 성공",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = MemoReadListSuccessResponse.class))),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "400",
                description = "입력값이 유효하지 않음",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = MemoInvalidInputResponse.class))),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "401",
                description = "로그인되지 않은 사용자",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = MemoUnauthorizedAccessResponse.class))),
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
	public ResponseEntity<?> listAllMemo(
			@Parameter(description = "사용자의 일련번호") @PathVariable Integer id) {

	    // 입력값 검증
	    if (id == null) {
	        throw new ApiException(MemoErrorCode.MEMO_INVALID_INPUT);
	    }

	    // 인증 여부 확인 (비로그인)
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
	        throw new ApiException(MemoErrorCode.MEMO_UNAUTHORIZED_ACCESS);
	    }

	    // 로그인 정보
	    String username = SecurityContextHolder.getContext().getAuthentication().getName();

	    // 본인 확인
	    if (!userService.readUserById(id).getRole().equals(Role.ROLE_ADMIN.name())) {
	        if (id != userService.readUserByUsername(username).getId()) {
	            throw new ApiException(UserErrorCode.USER_FORBIDDEN);
	        }
	    }

	    List<MemoReadResponseDto> memoList = memoService.readMemoList(id);

	    return ResponseEntity.status(MemoSuccessCode.MEMO_READ_LIST_SUCCESS.getStatus())
	            .body(ApiResponse.success(MemoSuccessCode.MEMO_READ_LIST_SUCCESS, memoList));
	}

	

	
	@Operation(summary = "장보기 메모 작성")
	@ApiResponses(value = {
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "201",
	            description = "메모 작성 성공",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = MemoCreateSuccessResponse.class))
	    ),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "400",
	            description = "메모 작성 실패",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = MemoCreateFailResponse.class))
	    ),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "400",
	            description = "입력값이 유효하지 않음",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = MemoInvalidInputResponse.class))
	    ),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "401",
	            description = "로그인되지 않은 사용자",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = MemoUnauthorizedAccessResponse.class))
	    ),
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
	                    schema = @Schema(implementation = InternalServerErrorResponse.class))
	    )
	})
	// 장보기 메모 입력
	@PostMapping("/users/{userId}/memos")
	public ResponseEntity<?> writeMemo(
			@Parameter(description = "사용자의 일련번호") @PathVariable int userId,
			@Parameter(description = "장보기 메모 작성 요청 정보") @RequestBody MemoCreateRequestDto memoRequestDto) {
	    
	    // 로그인 여부 확인
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
	        throw new ApiException(MemoErrorCode.MEMO_UNAUTHORIZED_ACCESS);
	    }

	    MemoCreateResponseDto memoResponseDto = memoService.createMemo(memoRequestDto);
	    
	    return ResponseEntity.status(MemoSuccessCode.MEMO_CREATE_SUCCESS.getStatus())
	            .body(ApiResponse.success(MemoSuccessCode.MEMO_CREATE_SUCCESS, memoResponseDto));
	}

	
	
	
	@Operation(summary = "장보기 메모 수정")
	@ApiResponses(value = {
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "200",
	            description = "메모 수정 성공",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = MemoUpdateSuccessResponse.class))
	    ),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "400",
	            description = "메모 수정 실패",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = MemoUpdateFailResponse.class))
	    ),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "400",
	            description = "입력값이 유효하지 않음",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = MemoInvalidInputResponse.class))
	    ),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "401",
	            description = "로그인되지 않은 사용자",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = MemoUnauthorizedAccessResponse.class))
	    ),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "403",
	            description = "권한 없는 사용자의 접근",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = MemoForbiddenResponse.class))
	    ),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "404",
	            description = "해당 메모를 찾을 수 없음",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = MemoNotFoundResponse.class))
	    ),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "500",
	            description = "서버 내부 오류",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = InternalServerErrorResponse.class))
	    )
	})
	// 장보기 메모 수정
	@PatchMapping("/users/{userId}/memos/{memoId}")
	public ResponseEntity<?> updateMemo(
			@Parameter(description = "장보기 메모의 일련번호") @PathVariable int memoId,
	        @Parameter(description = "장보기 메모 수정 요청 정보") @RequestBody MemoUpdateRequestDto memoRequestDto) {
		
	    // 인증 여부 확인 (비로그인)
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
	        throw new ApiException(MemoErrorCode.MEMO_UNAUTHORIZED_ACCESS);
	    }
	    
	    // 메모 수정 서비스 호출
	    MemoUpdateResponseDto memoResponseDto = memoService.updateMemo(memoId, memoRequestDto);
	    
	    return ResponseEntity.status(MemoSuccessCode.MEMO_UPDATE_SUCCESS.getStatus())
	            .body(ApiResponse.success(MemoSuccessCode.MEMO_UPDATE_SUCCESS, memoResponseDto));
	}
	
	
	
	
	@Operation(summary = "장보기 메모 삭제")
	@ApiResponses(value = {
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "200",
	            description = "메모 삭제 성공",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = MemoDeleteSuccessResponse.class))
	    ),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "400",
	            description = "메모 삭제 실패",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = MemoDeleteFailResponse.class))
	    ),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "400",
	            description = "입력값이 유효하지 않음",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = MemoInvalidInputResponse.class))
	    ),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "401",
	            description = "로그인되지 않은 사용자",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = MemoUnauthorizedAccessResponse.class))
	    ),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "403",
	            description = "권한 없는 사용자의 접근",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = MemoForbiddenResponse.class))
	    ),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "404",
	            description = "해당 메모를 찾을 수 없음",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = MemoNotFoundResponse.class))
	    ),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "500",
	            description = "서버 내부 오류",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = InternalServerErrorResponse.class))
	    )
	})
	// 장보기 메모 삭제
	@DeleteMapping("/users/{userId}/memos/{memoId}")
	public ResponseEntity<?> deleteMemo(
			@Parameter(description = "장보기 메모의 일련번호") @PathVariable int memoId) {
		
	    // 로그인 여부 확인
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
	        throw new ApiException(MemoErrorCode.MEMO_UNAUTHORIZED_ACCESS);
	    }

	    // 메모 삭제 서비스 호출
	    memoService.deleteMemo(memoId);

	    return ResponseEntity.status(MemoSuccessCode.MEMO_DELETE_SUCCESS.getStatus())
	            .body(ApiResponse.success(MemoSuccessCode.MEMO_DELETE_SUCCESS, null));
	}


	
	
	@Operation(summary = "레시피 재료를 장보기 메모에 추가")
	@ApiResponses(value = {
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "201",
	            description = "메모 추가 성공",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = MemoCreateSuccessResponse.class))
	    ),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "400",
	            description = "입력값이 유효하지 않음",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = MemoInvalidInputResponse.class))
	    ),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "401",
	            description = "로그인되지 않은 사용자",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = MemoUnauthorizedAccessResponse.class))
	    ),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "400",
	            description = "메모 작성 실패",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = MemoCreateFailResponse.class))
	    ),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(
	            responseCode = "500",
	            description = "서버 내부 오류",
	            content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = InternalServerErrorResponse.class))
	    )
	})
	// 레시피 재료 장보기 메모 추가
	@PostMapping("/users/{userId}/memos/recipe-stock")
	public ResponseEntity<?> addRecipeStockMemo(
			@Parameter(description = "사용자의 일련번호")  @PathVariable int userId,
			@Parameter(description = "레시피 재료 장보기 메모에 추가 요청 정보") @RequestBody RecipeStockMemoCreateRequestDto requestDTO) {
		
		System.err.println("레시피-> 메모 컨트롤러");
		System.err.println(requestDTO);

	    // 로그인 여부 확인
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
	        throw new ApiException(MemoErrorCode.MEMO_UNAUTHORIZED_ACCESS);
	    }

	    // 요청이 비어있는 경우 처리
	    if (requestDTO.getMemoList() == null || requestDTO.getMemoList().isEmpty()) {
	        throw new ApiException(MemoErrorCode.MEMO_INVALID_INPUT);
	    }

	    // 서비스 호출 (각 항목을 반복해서 저장)
	    List<MemoCreateResponseDto> result = memoService.addRecipeStockMemo(userId, requestDTO.getMemoList());

	    return ResponseEntity.status(MemoSuccessCode.MEMO_CREATE_SUCCESS.getStatus())
	            .body(ApiResponse.success(MemoSuccessCode.MEMO_CREATE_SUCCESS, result));
	}

	
}
