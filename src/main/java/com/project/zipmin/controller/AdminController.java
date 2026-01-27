package com.project.zipmin.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.zipmin.api.ApiException;
import com.project.zipmin.api.ApiResponse;
import com.project.zipmin.api.ChompErrorCode;
import com.project.zipmin.api.ChompSuccessCode;
import com.project.zipmin.api.ClassErrorCode;
import com.project.zipmin.api.ClassSuccessCode;
import com.project.zipmin.api.CommentErrorCode;
import com.project.zipmin.api.CommentSuccessCode;
import com.project.zipmin.api.KitchenErrorCode;
import com.project.zipmin.api.KitchenSuccessCode;
import com.project.zipmin.api.RecipeErrorCode;
import com.project.zipmin.api.RecipeSuccessCode;
import com.project.zipmin.api.ReportErrorCode;
import com.project.zipmin.api.ReportSuccessCode;
import com.project.zipmin.api.ReviewErrorCode;
import com.project.zipmin.api.ReviewSuccessCode;
import com.project.zipmin.api.UserErrorCode;
import com.project.zipmin.api.UserSuccessCode;
import com.project.zipmin.api.WithdrawErrorCode;
import com.project.zipmin.api.WithdrawSuccessCode;
import com.project.zipmin.dto.ClassApprovalUpdateRequestDto;
import com.project.zipmin.dto.ClassReadResponseDto;
import com.project.zipmin.dto.CommentReadResponseDto;
import com.project.zipmin.dto.ReviewReadResponseDto;
import com.project.zipmin.dto.UserReadResponseDto;
import com.project.zipmin.dto.WithdrawReadResponseDto;
import com.project.zipmin.dto.chomp.ChompReadResponseDto;
import com.project.zipmin.dto.kitchen.GuideReadResponseDto;
import com.project.zipmin.dto.recipe.RecipeReadResponseDto;
import com.project.zipmin.dto.report.ReportReadRequestDto;
import com.project.zipmin.dto.report.ReportReadResponseDto;
import com.project.zipmin.entity.Role;
import com.project.zipmin.service.ChompService;
import com.project.zipmin.service.CommentService;
import com.project.zipmin.service.CookingService;
import com.project.zipmin.service.FundService;
import com.project.zipmin.service.KitchenService;
import com.project.zipmin.service.RecipeService;
import com.project.zipmin.service.ReportService;
import com.project.zipmin.service.ReviewService;
import com.project.zipmin.service.UserService;
import com.project.zipmin.swagger.ChompReadListFailResponse;
import com.project.zipmin.swagger.ChompReadListSuccessResponse;
import com.project.zipmin.swagger.ClassAlreadyEndedResponse;
import com.project.zipmin.swagger.ClassForbiddenResponse;
import com.project.zipmin.swagger.ClassInvalidInputResponse;
import com.project.zipmin.swagger.ClassNotFoundResponse;
import com.project.zipmin.swagger.ClassReadListFailResponse;
import com.project.zipmin.swagger.ClassReadListSuccessResponse;
import com.project.zipmin.swagger.ClassUnauthorizedAccessResponse;
import com.project.zipmin.swagger.ClassUpdateApprovalFailResponse;
import com.project.zipmin.swagger.ClassUpdateApprovalSuccessResponse;
import com.project.zipmin.swagger.CommentForbiddenResponse;
import com.project.zipmin.swagger.CommentInvalidInputResponse;
import com.project.zipmin.swagger.CommentReadListFailResponse;
import com.project.zipmin.swagger.CommentReadListSuccessResponse;
import com.project.zipmin.swagger.CommentUnauthorizedAccessResponse;
import com.project.zipmin.swagger.InternalServerErrorResponse;
import com.project.zipmin.swagger.UserForbiddenResponse;
import com.project.zipmin.swagger.UserInvalidInputResponse;
import com.project.zipmin.swagger.UserNotFoundResponse;
import com.project.zipmin.swagger.UserReadListFailResponse;
import com.project.zipmin.swagger.UserReadListSuccessResponse;
import com.project.zipmin.swagger.UserUnauthorizedAccessResponse;
import com.project.zipmin.swagger.kitchen.KitchenInvalidInputResponse;
import com.project.zipmin.swagger.kitchen.KitchenReadListFailResponse;
import com.project.zipmin.swagger.kitchen.KitchenReadListSuccessResponse;
import com.project.zipmin.swagger.like.LikeCountFailResponse;
import com.project.zipmin.swagger.like.LikeExistFailResponse;
import com.project.zipmin.swagger.like.LikeInvalidInputResponse;
import com.project.zipmin.swagger.recipe.RecipeForbiddenResponse;
import com.project.zipmin.swagger.recipe.RecipeInvalidInputResponse;
import com.project.zipmin.swagger.recipe.RecipeReadListFailResponse;
import com.project.zipmin.swagger.recipe.RecipeReadListSuccessResponse;
import com.project.zipmin.swagger.report.ReportCountFailResponse;
import com.project.zipmin.swagger.report.ReportForbiddenResponse;
import com.project.zipmin.swagger.report.ReportInvalidInputResponse;
import com.project.zipmin.swagger.report.ReportReadListFailResponse;
import com.project.zipmin.swagger.report.ReportReadListSuccessResponse;
import com.project.zipmin.swagger.report.ReportUnauthorizedResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name = "ADMIN API", description = "관리자 관련 API")
public class AdminController {

	private final UserService userService;
	private final FundService fundService;
	private final RecipeService recipeService;
	private final KitchenService kitchenService;
	private final ChompService chompService;
	private final CookingService cookingService;
	private final CommentService commentService;
	private final ReviewService reviewService;
	private final ReportService reportService;
	
	
	
	
	
	// 사용자 목록 조회
	@Operation(
	    summary = "사용자 목록 조회"
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "200",
				description = "사용자 목록 조회 성공",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserReadListSuccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "사용자 목록 조회 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserReadListFailResponse.class))),
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
						schema = @Schema(implementation = UserUnauthorizedAccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "403",
				description = "권한 없는 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserForbiddenResponse.class))),
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
	// 사용자 목록 조회
	@GetMapping("/admin/users")
	public ResponseEntity<?> readUserPage(
			@Parameter(description = "카테고리", required = false) @RequestParam(required = false) String category,
			@Parameter(description = "검색 필드", required = false) @RequestParam(required = false) String field,
			@Parameter(description = "검색어", required = false) @RequestParam(required = false) String keyword,
			@Parameter(description = "정렬", required = false) @RequestParam(required = false) String sort,
			@Parameter(description = "페이지 번호") @RequestParam int page,
			@Parameter(description = "페이지 크기") @RequestParam int size) {
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
			throw new ApiException(UserErrorCode.USER_UNAUTHORIZED_ACCESS);
		}
		
		// 권한 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		if (!userService.readUserByUsername(username).getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			if (!userService.readUserByUsername(username).getRole().equals(Role.ROLE_ADMIN.name())) {
				throw new ApiException(UserErrorCode.USER_FORBIDDEN);
			}
		}
		
		Pageable pageable = PageRequest.of(page, size);
		Page<UserReadResponseDto> userPage = userService.readUserPage(category, field, keyword, sort, pageable);
		
		return ResponseEntity.status(UserSuccessCode.USER_READ_LIST_SUCCESS.getStatus())
				.body(ApiResponse.success(UserSuccessCode.USER_READ_LIST_SUCCESS, userPage));
	}
	
	
	
	
	
	// 출금 목록 조회
	@Operation(
	    summary = "출금 목록 조회"
	)
	@ApiResponses(value = {
		// 200 WITHDRAW_READ_LIST_SUCCESS 출금 목록 조회 성공
		// 400 WITHDRAW_READ_LIST_FAIL 출금 목록 조회 실패
		// 400 WITHDRAW_INVALID_INPUT 유효하지 않은 입력값
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "유효하지 않은 입력값",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserInvalidInputResponse.class))),
		// 401 WITHDRAW_UNAUTHORIZED_ACCESS 로그인되지 않은 사용자
		// 403 WITHDRAW_FORBIDDEN 권한 없는 사용자
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
	// 출금 목록 조회
	@GetMapping("/admin/withdraws")
	public ResponseEntity<?> readWithdrawList(
			@Parameter(description = "검색어", required = false) @RequestParam(required = false) String keyword,
			@Parameter(description = "검색 필드", required = false) @RequestParam(required = false) String field,
			@Parameter(description = "상태", required = false) @RequestParam(required = false) Integer status,
			@Parameter(description = "정렬", required = false) @RequestParam(required = false) String sort,
			@Parameter(description = "페이지 번호") @RequestParam int page,
			@Parameter(description = "페이지 크기") @RequestParam int size) {
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
			throw new ApiException(WithdrawErrorCode.WITHDRAW_UNAUTHORIZED_ACCESS);
		}
		
		// 권한 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		if (!userService.readUserByUsername(username).getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			if (!userService.readUserByUsername(username).getRole().equals(Role.ROLE_ADMIN.name())) {
				throw new ApiException(WithdrawErrorCode.WITHDRAW_FORBIDDEN);
			}
		}

	    Pageable pageable = PageRequest.of(page, size);
	    Page<WithdrawReadResponseDto> withdrawPage = fundService.readAdminWithdrawPage(field, keyword, status, sort, pageable);

	    return ResponseEntity.status(WithdrawSuccessCode.WITHDRAW_READ_LIST_SUCCESS.getStatus())
	            .body(ApiResponse.success(WithdrawSuccessCode.WITHDRAW_READ_LIST_SUCCESS, withdrawPage));
	}
	
	
	
	
	
	// 레시피 목록 조회
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
						schema = @Schema(implementation = UserUnauthorizedAccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "403",
				description = "권한 없는 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = RecipeForbiddenResponse.class))),
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
	// 레시피 목록 조회
	@GetMapping("/admin/recipes")
	public ResponseEntity<?> readRecipe(
			@Parameter(description = "카테고리", required = false) @RequestParam(required = false) List<String> categoryList,
			@Parameter(description = "검색어", required = false) @RequestParam(required = false) String keyword,
			@Parameter(description = "정렬", required = false) @RequestParam(required = false) String sort,
			@Parameter(description = "페이지 번호") @RequestParam int page,
			@Parameter(description = "페이지 크기") @RequestParam int size) {
		
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
			throw new ApiException(RecipeErrorCode.RECIPE_UNAUTHORIZED);
		}
		
		// 권한 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		if (!userService.readUserByUsername(username).getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			if (!userService.readUserByUsername(username).getRole().equals(Role.ROLE_ADMIN.name())) {
				throw new ApiException(RecipeErrorCode.RECIPE_FORBIDDEN);
			}
		}
		
		Pageable pageable = PageRequest.of(page, size);
		Page<RecipeReadResponseDto> recipePage = recipeService.readRecipePage(keyword, categoryList, sort, pageable);
		
		return ResponseEntity.status(RecipeSuccessCode.RECIPE_READ_LIST_SUCCESS.getStatus())
				.body(ApiResponse.success(RecipeSuccessCode.RECIPE_READ_LIST_SUCCESS, recipePage));
	}
	
	
	
	
	
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
	@GetMapping("/admin/guides")
	public ResponseEntity<?> listGuide(
			@RequestParam(required = false) String category,
			@RequestParam(required = false) String keyword, 
		    @RequestParam(required = false) String sort,
		    @RequestParam int page,
		    @RequestParam int size) {
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
			throw new ApiException(KitchenErrorCode.KITCHEN_UNAUTHORIZED);
		}
		
		// 권한 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		if (!userService.readUserByUsername(username).getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			if (!userService.readUserByUsername(username).getRole().equals(Role.ROLE_ADMIN.name())) {
				throw new ApiException(KitchenErrorCode.KITCHEN_FORBIDDEN);
			}
		}
		
		Pageable pageable = PageRequest.of(page, size);
		Page<GuideReadResponseDto> guidePage = kitchenService.readGuidePage(category, keyword, sort, pageable);
		
        return ResponseEntity.status(KitchenSuccessCode.KITCHEN_READ_LIST_SUCCESS.getStatus())
                .body(ApiResponse.success(KitchenSuccessCode.KITCHEN_READ_LIST_SUCCESS, guidePage));
	}
	
	
	
	
	
	// 쩝쩝박사 목록 조회
	@Operation(
	    summary = "쩝쩝박사 목록 조회"
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "200",
				description = "쩝쩝박사 목록 조회 성공",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = ChompReadListSuccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "쩝쩝박사 목록 조회 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = ChompReadListFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "401",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserInvalidInputResponse.class))),
		// 401 CHOMP_UNAUTHORIZED_ACCESS
		// 403 CHOMP_FORBIDDEN
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
	// 쩝쩝박사 목록 조회
	@GetMapping("/admin/chomp")
	public ResponseEntity<?> listChomp(
			@Parameter(description = "카테고리", required = false) @RequestParam(required = false) String category,
			@Parameter(description = "검색어", required = false) @RequestParam(required = false) String keyword,
			@Parameter(description = "정렬", required = false) @RequestParam(required = false) String sort,
		    @Parameter(description = "페이지 번호") @RequestParam int page,
		    @Parameter(description = "페이지 크기") @RequestParam int size) {
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
			throw new ApiException(ChompErrorCode.CHOMP_UNAUTHORIZED);
		}
		
		// 권한 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		if (!userService.readUserByUsername(username).getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			if (!userService.readUserByUsername(username).getRole().equals(Role.ROLE_ADMIN.name())) {
				throw new ApiException(ChompErrorCode.CHOMP_FORBIDDEN);
			}
		}
		
		Pageable pageable = PageRequest.of(page, size);
		Page<ChompReadResponseDto> chompPage = chompService.readChompPage(category, keyword, sort, pageable);

		return ResponseEntity.status(ChompSuccessCode.CHOMP_READ_LIST_SUCCESS.getStatus())
				.body(ApiResponse.success(ChompSuccessCode.CHOMP_READ_LIST_SUCCESS, chompPage));
	}
	
	
	
	
	
	// 쿠킹클래스 목록 조회
	@Operation(
	    summary = "쿠킹클래스 목록 조회"
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "200",
				description = "쿠킹클래스 목록 조회 성공",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = ClassReadListSuccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "쿠킹클래스 목록 조회 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = ClassReadListFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = ClassInvalidInputResponse.class))),
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
						schema = @Schema(implementation = ClassUnauthorizedAccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "403",
				description = "권한 없는 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = ClassForbiddenResponse.class))),
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
	// 쿠킹클래스 목록 조회
	@GetMapping("/admin/classes")
	public ResponseEntity<?> listClass(
			@Parameter(description = "카테고리", required = false) @RequestParam(required = false) String category,
			@Parameter(description = "검색어", required = false) @RequestParam(required = false) String keyword,
			@Parameter(description = "승인 상태", required = false) @RequestParam(required = false) String approval,
			@Parameter(description = "진행 상태", required = false) @RequestParam(required = false) String status,
			@Parameter(description = "정렬", required = false) @RequestParam(required = false) String sort,
		    @Parameter(description = "페이지 번호") @RequestParam int page,
		    @Parameter(description = "페이지 크기") @RequestParam int size) {
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
			throw new ApiException(ClassErrorCode.CLASS_UNAUTHORIZED_ACCESS);
		}
		
		// 권한 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		if (!userService.readUserByUsername(username).getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			if (!userService.readUserByUsername(username).getRole().equals(Role.ROLE_ADMIN.name())) {
				throw new ApiException(ClassErrorCode.CLASS_FORBIDDEN);
			}
		}
		
		Pageable pageable = PageRequest.of(page, size);
		Page<ClassReadResponseDto> classPage = cookingService.readClassPage(category, keyword, approval, status, sort, pageable);
		
		return ResponseEntity.status(ClassSuccessCode.CLASS_READ_LIST_SUCCESS.getStatus())
				.body(ApiResponse.success(ClassSuccessCode.CLASS_READ_LIST_SUCCESS, classPage));
	}
	
	
	
	
	
	@Operation(
	    summary = "쿠킹클래스 승인 수정"
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "200",
				description = "쿠킹클래스 승인 수정 성공",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = ClassUpdateApprovalSuccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "쿠킹클래스 승인 수정 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = ClassUpdateApprovalFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = ClassInvalidInputResponse.class))),
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
						schema = @Schema(implementation = ClassUnauthorizedAccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "403",
				description = "권한 없는 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = ClassForbiddenResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "403",
				description = "클래스 종료 후 접근 시도",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = ClassAlreadyEndedResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 클래스를 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = ClassNotFoundResponse.class))),
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
	// 클래스 승인 수정
	@PatchMapping("/admin/classes/{id}/approval")
	public ResponseEntity<?>  updateClassApproval(
			@Parameter(description = "클래스의 일련번호") @PathVariable int id,
			@Parameter(description = "승인 상태") @RequestBody ClassApprovalUpdateRequestDto classDto) {
	
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
			throw new ApiException(ClassErrorCode.CLASS_UNAUTHORIZED_ACCESS);
		}
		
		cookingService.updateClassApproval(classDto);
		
		return ResponseEntity.status(ClassSuccessCode.CLASS_UPDATE_APPROVAL_SUCCESS.getStatus())
				.body(ApiResponse.success(ClassSuccessCode.CLASS_UPDATE_APPROVAL_SUCCESS, null));
	}
	
	
	
	
	
	// 댓글 목록 조회
	@Operation(
	    summary = "댓글 목록 조회"
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "200",
				description = "댓글 목록 조회 성공",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = CommentReadListSuccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "댓글 목록 조회 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = CommentReadListFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "좋아요 집계 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = LikeCountFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "신고 집계 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = ReportCountFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = CommentInvalidInputResponse.class))),
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
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = ReportInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "401",
				description = "로그인되지 않은 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = CommentUnauthorizedAccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "403",
				description = "권한 없는 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = CommentForbiddenResponse.class))),
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
	// 댓글 목록 조회
	@GetMapping("/admin/comments")
	public ResponseEntity<?> readAdminComment(
			@Parameter(description = "테이블명", required = false) @RequestParam(required = false) String tablename,
			@Parameter(description = "검색어", required = false) @RequestParam(required = false) String keyword,
			@Parameter(description = "정렬", required = false) @RequestParam(required = false) String sort,
			@Parameter(description = "페이지 번호") @RequestParam int page,
			@Parameter(description = "페이지 크기") @RequestParam int size) {
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
			throw new ApiException(CommentErrorCode.COMMENT_UNAUTHORIZED_ACCESS);
		}
		
		// 권한 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		if (!userService.readUserByUsername(username).getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			if (!userService.readUserByUsername(username).getRole().equals(Role.ROLE_ADMIN.name())) {
				throw new ApiException(CommentErrorCode.COMMENT_FORBIDDEN);
			}
		}
		
		Pageable pageable = PageRequest.of(page, size);
		Page<CommentReadResponseDto> commentPage = commentService.readAdminCommentPage(tablename, keyword, sort, pageable);
		
		return ResponseEntity.status(CommentSuccessCode.COMMENT_READ_LIST_SUCCESS.getStatus())
				.body(ApiResponse.success(CommentSuccessCode.COMMENT_READ_LIST_SUCCESS, commentPage));
	}
	
	
	
	
	
	// 리뷰 목록 조회
	@Operation(
	    summary = "리뷰 목록 조회"
	)
	@ApiResponses(value = {
		// 200 REVIEW_READ_LIST_SUCCESS 리뷰 목록 조회 성공
		// 400 REVIEW_READ_LIST_FAIL 리뷰 목록 조회 실패
		// 400 REVIEW_INVALID_INPUT 유효하지 않은 입력값
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "유효하지 않은 입력값",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserInvalidInputResponse.class))),
		// 401 REVIEW_UNAUTHORIZED_ACCESS 로그인되지 않은 사용자
		// 403 RECIPE_FORBIDDEN 권한 없는 사용자
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
	// 리뷰 목록 조회
	@GetMapping("/admin/reviews")
	public ResponseEntity<?> readAdminReview(
			@Parameter(description = "검색어", required = false) @RequestParam(required = false) String keyword,
			@Parameter(description = "정렬", required = false) @RequestParam(required = false) String sort,
			@Parameter(description = "페이지 번호") @RequestParam int page,
			@Parameter(description = "페이지 크기") @RequestParam int size) {
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
			throw new ApiException(ReviewErrorCode.REVIEW_UNAUTHORIZED_ACCESS);
		}
		
		// 권한 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		if (!userService.readUserByUsername(username).getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			if (!userService.readUserByUsername(username).getRole().equals(Role.ROLE_ADMIN.name())) {
				throw new ApiException(RecipeErrorCode.RECIPE_FORBIDDEN);
			}
		}
		
		Pageable pageable = PageRequest.of(page, size);
		Page<ReviewReadResponseDto> reviewPage = reviewService.readAdminReviewPage(keyword, sort, pageable);
		
		return ResponseEntity.status(ReviewSuccessCode.REVIEW_READ_LIST_SUCCESS.getStatus())
				.body(ApiResponse.success(ReviewSuccessCode.REVIEW_READ_LIST_SUCCESS, reviewPage));
	}
	
	
	
	
	
	// 신고 목록 조회
	@Operation(
	    summary = "신고 목록 조회"
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "200",
				description = "신고 목록 조회 성공",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = ReportReadListSuccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "신고 목록 조회 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = ReportReadListFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "유효하지 않은 입력값",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = ReportInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "유효하지 않은 입력값",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "401",
				description = "로그인되지 않은 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = ReportUnauthorizedResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "403",
				description = "권한 없는 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = ReportForbiddenResponse.class))),
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
	// 신고 목록 조회
	@GetMapping("/reports")
	public ResponseEntity<?> readReport(
			@Parameter(description = "신고 목록 조 요청 정보") @RequestBody ReportReadRequestDto reportRequestDto) {
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
			throw new ApiException(ReportErrorCode.REPORT_UNAUTHORIZED);
		}
		
		// 권한 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		if (!userService.readUserByUsername(username).getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			if (!userService.readUserByUsername(username).getRole().equals(Role.ROLE_ADMIN.name())) {
				throw new ApiException(ReportErrorCode.REPORT_FORBIDDEN);
			}
		}
		
		List<ReportReadResponseDto> reportList = reportService.readReportList(reportRequestDto);
		
		return ResponseEntity.status(ReportSuccessCode.REPORT_READ_LIST_SUCCESS.getStatus())
        		.body(ApiResponse.success(ReportSuccessCode.REPORT_READ_LIST_SUCCESS, reportList));
	}
}
