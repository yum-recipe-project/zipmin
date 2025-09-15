package com.project.zipmin.controller;

import java.util.List;
import java.util.Map;

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
import com.project.zipmin.api.ClassSuccessCode;
import com.project.zipmin.api.UserErrorCode;
import com.project.zipmin.api.UserSuccessCode;
import com.project.zipmin.dto.ClassMyApplyReadResponseDto;
import com.project.zipmin.dto.ClassReadResponseDto;
import com.project.zipmin.dto.CommentReadMyResponseDto;
import com.project.zipmin.dto.FundDTO;
import com.project.zipmin.dto.RecipeReadMyResponseDto;
import com.project.zipmin.dto.RecipeReadMySavedResponseDto;
import com.project.zipmin.dto.UserCreateRequestDto;
import com.project.zipmin.dto.UserCreateResponseDto;
import com.project.zipmin.dto.UserDto;
import com.project.zipmin.dto.UserPasswordCheckRequestDto;
import com.project.zipmin.dto.UserReadRequestDto;
import com.project.zipmin.dto.UserReadResponseDto;
import com.project.zipmin.dto.UserUpdateRequestDto;
import com.project.zipmin.dto.UserUpdateResponseDto;
import com.project.zipmin.entity.Role;
import com.project.zipmin.service.CommentService;
import com.project.zipmin.service.CookingService;
import com.project.zipmin.service.FridgeService;
import com.project.zipmin.service.KitchenService;
import com.project.zipmin.service.RecipeService;
import com.project.zipmin.service.UserService;
import com.project.zipmin.swagger.InternalServerErrorResponse;
import com.project.zipmin.swagger.UserCorrectPassworResponse;
import com.project.zipmin.swagger.UserCreateFailResponse;
import com.project.zipmin.swagger.UserCreateSuccessResponse;
import com.project.zipmin.swagger.UserDeleteFailResponse;
import com.project.zipmin.swagger.UserDeleteSuccessResponse;
import com.project.zipmin.swagger.UserEmailDuplicatedResponse;
import com.project.zipmin.swagger.UserForbiddenResponse;
import com.project.zipmin.swagger.UserIncorrectPassworResponse;
import com.project.zipmin.swagger.UserInvalidInputResponse;
import com.project.zipmin.swagger.UserNotFoundResponse;
import com.project.zipmin.swagger.UserReadListFailResponse;
import com.project.zipmin.swagger.UserReadListSuccessResponse;
import com.project.zipmin.swagger.UserReadSuccessResponse;
import com.project.zipmin.swagger.UserReadUsernameSuccessResponse;
import com.project.zipmin.swagger.UserTelDuplicatedResponse;
import com.project.zipmin.swagger.UserUnauthorizedAccessResponse;
import com.project.zipmin.swagger.UserUpdateFailResponse;
import com.project.zipmin.swagger.UserUpdateSuccessResponse;
import com.project.zipmin.swagger.UserUsernameDuplicatedResponse;
import com.project.zipmin.swagger.UserUsernameNotDuplicatedResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name = "User API", description = "사용자 관련 API")
public class UserController {
	
	private final UserService userService;
	private final CommentService commentService;	
	private final CookingService cookingService;	
	private final FridgeService fridgeService;
	private final KitchenService kitchenService;
	private final RecipeService recipeService;
	
	
	

	
	// 사용자 목록 조회 (관리자)
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
				responseCode = "500",
				description = "서버 내부 오류",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = InternalServerErrorResponse.class)))
	})
	@GetMapping("/users")
	public ResponseEntity<?> readUserPage(
			@Parameter(description = "카테고리", required = false) @RequestParam(required = false) String category,
			@Parameter(description = "조회할 페이지 번호", required = true) @RequestParam int page,
			@Parameter(description = "페이지의 항목 수", required = true) @RequestParam int size) {
		
		Pageable pageable = PageRequest.of(page, size);
		Page<UserReadResponseDto> userPage = userService.readUserPage(category, pageable);
		
		// 권한 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		if (!userService.readUserByUsername(username).getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			if (!userService.readUserByUsername(username).getRole().equals(Role.ROLE_ADMIN.name())) {
				throw new ApiException(UserErrorCode.USER_FORBIDDEN);
			}
		}
		
		return ResponseEntity.status(UserSuccessCode.USER_READ_LIST_SUCCESS.getStatus())
				.body(ApiResponse.success(UserSuccessCode.USER_READ_LIST_SUCCESS, userPage));
	}
	
	
	
	// 사용자 조회
	@Operation(
	    summary = "사용자 조회"
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "200",
				description = "사용자 조회 성공",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserReadSuccessResponse.class))),
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
	@GetMapping("/users/{id}")
	public ResponseEntity<?> readUser(@Parameter(description = "투표의 일련번호", required = true, example = "1") @PathVariable int id) {
		UserReadResponseDto userDto = userService.readUserById(id);
		
		return ResponseEntity.status(UserSuccessCode.USER_READ_SUCCESS.getStatus())
					.body(ApiResponse.success(UserSuccessCode.USER_READ_SUCCESS, userDto));
	}
	
	
	
	// 사용자 생성
	@Operation(
	    summary = "사용자 작성"
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "201",
				description = "사용자 작성 성공",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserCreateSuccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "사용자 작성 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserCreateFailResponse.class))),
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
				responseCode = "409",
				description = "아이디 중복 작성 시도",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserUsernameDuplicatedResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "409",
				description = "전화번호 중복 작성 시도",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserTelDuplicatedResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "409",
				description = "이메일 중복 작성 시도",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserEmailDuplicatedResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "500",
				description = "서버 내부 오류",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = InternalServerErrorResponse.class)))
	})
	@PostMapping("/users")
	public ResponseEntity<?> createUser(
			@Parameter(description = "사용자 생성 요청 정보", required = true) @RequestBody UserCreateRequestDto userRequestDto) {
		
		// 인증 여부 확인 (비로그인)
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		// 권한 설정
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
		    userRequestDto.setRole(Role.ROLE_USER);
		}
		else {
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			if (userService.readUserByUsername(username).getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
				userRequestDto.setRole(Role.ROLE_ADMIN);
			}
		}
		
		UserCreateResponseDto userResponseDto  = userService.createUser(userRequestDto);

		return ResponseEntity.status(UserSuccessCode.USER_CREATE_SUCCESS.getStatus())
					.body(ApiResponse.success(UserSuccessCode.USER_CREATE_SUCCESS, userResponseDto));
	}
	
	
	
	// 사용자 아이디 중복 확인
	@Operation(
	    summary = "사용자 아이디 중복 확인"
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "200",
				description = "사용 가능한 아이디",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserUsernameNotDuplicatedResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "409",
				description = "아이디 중복 작성 시도",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserUsernameDuplicatedResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "500",
				description = "서버 내부 오류",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = InternalServerErrorResponse.class)))
	})
	@GetMapping("/users/check-username")
	public ResponseEntity<?> checkUsername(
			@Parameter(description = "사용자 아이디", required = true, example = "user1") @RequestParam String username) {

        // 아이디 중복확인
        if (userService.existsUsername(username)) {
        	throw new ApiException(UserErrorCode.USER_USERNAME_DUPLICATED);
        }

        return ResponseEntity.status(UserSuccessCode.USER_USERNAME_NOT_DUPLICATED.getStatus())
        		.body(ApiResponse.success(UserSuccessCode.USER_USERNAME_NOT_DUPLICATED, null));
	}
	
	
	
	// 비밀번호 확인
	@Operation(
	    summary = "사용자 비밀번호 확인"
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "200",
				description = "비밀번호 일치",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserCorrectPassworResponse.class))),
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
						schema = @Schema(implementation = UserUnauthorizedAccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "401",
				description = "권한 없는 사용자의 접근",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserForbiddenResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "401",
				description = "비밀번호 불일치",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserIncorrectPassworResponse.class))),
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
	@PostMapping("/users/{id}/check-password")
	public ResponseEntity<?> verifyPassword(
			@Parameter(description = "사용자의 일련번호", required = true, example = "1") @PathVariable Integer id,
			@Parameter(description = "비밀번호 확인 요청 정보", required = true) @RequestBody UserPasswordCheckRequestDto userDto) {

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
		userDto.setId(userService.readUserByUsername(username).getId());
		
		// 본인 확인
		if (!userService.readUserById(id).getRole().equals(Role.ROLE_ADMIN)) {
			if (id != userService.readUserByUsername(username).getId()) {
				throw new ApiException(UserErrorCode.USER_FORBIDDEN);
			}
		}
		
		// 비밀번호 확인
		userService.checkPassword(userDto);
		
		return ResponseEntity.status(UserSuccessCode.USER_CORRECT_PASSWORD.getStatus())
				.body(ApiResponse.success(UserSuccessCode.USER_CORRECT_PASSWORD, null));
	}
	
	
	
	// 아이디 찾기
	@Operation(
	    summary = "사용자 아이디 찾기"
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "200",
				description = "비밀번호 일치",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserReadUsernameSuccessResponse.class))),
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
	@PostMapping("/users/find-username")
	public ResponseEntity<?> findUsername(
			@Parameter(description = "사용자 아이디 조회 요청 정보", required = true) @RequestBody @Valid UserReadRequestDto userRequestDto) {
		
		// 아이디 조회
		String username = userService.readUserByNameAndTel(userRequestDto).getUsername();
		
		return ResponseEntity.status(UserSuccessCode.USER_READ_USERNAME_SUCCESS.getStatus())
				.body(ApiResponse.success(UserSuccessCode.USER_READ_USERNAME_SUCCESS, Map.of("username", username)));
	}
	
	
	
	// 사용자 수정
	@Operation(
	    summary = "사용자 수정"
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "200",
				description = "사용자 수정 성공",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserUpdateSuccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "사용자 수정 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserUpdateFailResponse.class))),
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
						schema = @Schema(implementation = UserUnauthorizedAccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "401",
				description = "권한 없는 사용자의 접근",
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
				responseCode = "409",
				description = "전화번호 중복 작성 시도",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserTelDuplicatedResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "409",
				description = "이메일 중복 작성 시도",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserEmailDuplicatedResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "500",
				description = "서버 내부 오류",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = InternalServerErrorResponse.class)))
	})
	@PatchMapping("/users/{id}")
	public ResponseEntity<?> updateUser(
			@Parameter(description = "사용자의 일련번호", required = true, example = "1") @PathVariable Integer id,
			@Parameter(description = "사용자 수정 요청 정보", required = true) @RequestBody UserUpdateRequestDto userRequestDto) {

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
		userRequestDto.setId(id);
		
		// 권한 확인
		if (!userService.readUserByUsername(username).getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			// 관리자
			if (userService.readUserByUsername(username).getRole().equals(Role.ROLE_ADMIN.name())) {
				if (userService.readUserById(id).getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
					throw new ApiException(UserErrorCode.USER_FORBIDDEN);
				}
				if (userService.readUserById(id).getRole().equals(Role.ROLE_ADMIN.name())) {
					if (userService.readUserByUsername(username).getId() != id) {
						throw new ApiException(UserErrorCode.USER_FORBIDDEN);
					}
				}
			}
			// 일반 회원
			else {
				if (userService.readUserByUsername(username).getId() != id) {
					throw new ApiException(UserErrorCode.USER_FORBIDDEN);
				}
			}
		}
		
		UserUpdateResponseDto userResponseDto = userService.updateUser(userRequestDto);

		return ResponseEntity.status(UserSuccessCode.USER_UPDATE_SUCCESS.getStatus())
				.body(ApiResponse.success(UserSuccessCode.USER_UPDATE_SUCCESS, userResponseDto));
	}
	
	
	
	// 사용자 삭제
	@Operation(
	    summary = "사용자 삭제"
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "200",
				description = "사용자 삭제 성공",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserDeleteSuccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "사용자 삭제 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserDeleteFailResponse.class))),
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
						schema = @Schema(implementation = UserUnauthorizedAccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "401",
				description = "권한 없는 사용자의 접근",
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
	@DeleteMapping("/users/{id}")
	public ResponseEntity<?> deleteMember(
			@Parameter(description = "사용자의 일련번호", required = true, example = "1") @PathVariable Integer id) {
		
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
		
		// 권한 확인
		if (userService.readUserByUsername(username).getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			if (userService.readUserByUsername(username).getId() == id) {
				throw new ApiException(UserErrorCode.USER_SUPER_ADMIN_FORBIDDEN);
			}
		}
		else {
			// 관리자
			if (userService.readUserByUsername(username).getRole().equals(Role.ROLE_ADMIN.name())) {
				if (userService.readUserById(id).getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
					throw new ApiException(UserErrorCode.USER_FORBIDDEN);
				}
				if (userService.readUserById(id).getRole().equals(Role.ROLE_ADMIN.name())) {
					if (userService.readUserByUsername(username).getId() != id) {
						throw new ApiException(UserErrorCode.USER_FORBIDDEN);
					}
				}
			}
			// 일반 회원
			else {
				if (userService.readUserByUsername(username).getId() != id) {
					throw new ApiException(UserErrorCode.USER_FORBIDDEN);
				}
			}
		}
		
		userService.deleteUser(id);
		
		return ResponseEntity.status(UserSuccessCode.USER_DELETE_SUCCESS.getStatus())
				.body(ApiResponse.success(UserSuccessCode.USER_DELETE_SUCCESS, null));
	}

	
	
	// 작성한 댓글
	@GetMapping("/users/{id}/comments")
	public ResponseEntity<?> readUserCommentList(
			@Parameter(description = "사용자의 일련번호", required = true, example = "1") @PathVariable Integer id,
			@Parameter(description = "조회할 페이지 번호", required = true, example = "1") @RequestParam int page,
			@Parameter(description = "페이지의 항목 수", required = true, example = "10") @RequestParam int size) {
		
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
		if (!userService.readUserById(id).getRole().equals(Role.ROLE_ADMIN)) {
			if (id != userService.readUserByUsername(username).getId()) {
				throw new ApiException(UserErrorCode.USER_FORBIDDEN);
			}
		}
		
		Pageable pageable = PageRequest.of(page, size);
		Page<CommentReadMyResponseDto> commentPage = commentService.readCommentPageByUserId(id, pageable);
		
		return ResponseEntity.status(UserSuccessCode.USER_READ_LIST_SUCCESS.getStatus())
				.body(ApiResponse.success(UserSuccessCode.USER_READ_LIST_SUCCESS, commentPage));
	}
	
	
	
	// 신청한 쿠킹클래스
	@GetMapping("/users/{id}/applied-classes")
	public ResponseEntity<?> readUserClassApplyList(
			@Parameter(description = "사용자의 일련번호", required = true, example = "1") @PathVariable Integer id,
			@RequestParam String sort,
			@Parameter(description = "조회할 페이지 번호", required = true, example = "1") @RequestParam int page,
			@Parameter(description = "페이지의 항목 수", required = true, example = "10") @RequestParam int size) {
		
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
		if (!userService.readUserById(id).getRole().equals(Role.ROLE_ADMIN)) {
			if (id != userService.readUserByUsername(username).getId()) {
				throw new ApiException(UserErrorCode.USER_FORBIDDEN);
			}
		}
		
		Pageable pageable = PageRequest.of(page, size);
		Page<ClassMyApplyReadResponseDto> applyPage = cookingService.readApplyClassPageByUserId(id, sort, pageable);
		
		return ResponseEntity.status(ClassSuccessCode.CLASS_READ_LIST_SUCCESS.getStatus())
				.body(ApiResponse.success(ClassSuccessCode.CLASS_READ_LIST_SUCCESS, applyPage));
	}
	
	
	
	// 개설한 쿠킹클래스
	@GetMapping("/users/{id}/classes")
	public ResponseEntity<?> listUserClass(
			@Parameter(description = "사용자의 일련번호") @PathVariable Integer id,
			@Parameter(description = "정렬") @RequestParam String sort,
			@Parameter(description = "조회할 페이지 번호") @RequestParam int page,
			@Parameter(description = "페이지의 항목 수") @RequestParam int size) {
		
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
		if (!userService.readUserById(id).getRole().equals(Role.ROLE_ADMIN)) {
			if (id != userService.readUserByUsername(username).getId()) {
				throw new ApiException(UserErrorCode.USER_FORBIDDEN);
			}
		}
		
		Pageable pageable = PageRequest.of(page, size);
		Page<ClassReadResponseDto> classPage = cookingService.readClassPageByUserId(id, sort, pageable);
		
		return ResponseEntity.status(UserSuccessCode.USER_READ_LIST_SUCCESS.getStatus())
				.body(ApiResponse.success(UserSuccessCode.USER_READ_LIST_SUCCESS, classPage));
	}
	
	
	// 저장한(좋아요를 누른) 키친가이드
//	@GetMapping("/users/{id}/likes/guides")
//	public ResponseEntity<?> readUserSavedGuideList(
//			@PathVariable Integer id,
//			@RequestParam int page,
//			@RequestParam int size) {
//		
//		// 입력값 검증
//		if (id == null) {
//			throw new ApiException(UserErrorCode.USER_INVALID_INPUT);
//		}
//		
//		// 인증 여부 확인 (비로그인)
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
//		    throw new ApiException(UserErrorCode.USER_UNAUTHORIZED_ACCESS);
//		}
//		
//		// 로그인 정보
//		String username = SecurityContextHolder.getContext().getAuthentication().getName();
//		
//		// 본인 확인
//		if (!userService.readUserById(id).getRole().equals(Role.ROLE_ADMIN)) {
//			if (id != userService.readUserByUsername(username).getId()) {
//				throw new ApiException(UserErrorCode.USER_FORBIDDEN);
//			}
//		}
//		
//		Pageable pageable = PageRequest.of(page, size);
//		Page<GuideReadMySavedResponseDto> savedGuidePage = kitchenService.readSavedGuidePageByUserId(id, pageable);
//		
//		return ResponseEntity.status(UserSuccessCode.USER_READ_LIST_SUCCESS.getStatus())
//				.body(ApiResponse.success(UserSuccessCode.USER_READ_LIST_SUCCESS, savedGuidePage));
//	}
	
	
	
	
	
	// 저장한(좋아요를 누른) 레시피
	@GetMapping("/users/{id}/likes/recipes")
	public ResponseEntity<?> readUserSavedRecipeList(
	        @PathVariable Integer id,
	        @RequestParam int page,
	        @RequestParam int size) {

		
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
	    if (!userService.readUserById(id).getRole().equals(Role.ROLE_ADMIN)) {
	        if (id != userService.readUserByUsername(username).getId()) {
	            throw new ApiException(UserErrorCode.USER_FORBIDDEN);
	        }
	    }

	    Pageable pageable = PageRequest.of(page, size);
	    
	    // 레시피 저장 페이지 조회
	    Page<RecipeReadMySavedResponseDto> savedRecipePage = recipeService.readSavedRecipePageByUserId(id, pageable);
	    System.err.println("페이지 조회 후");
	    
	    return ResponseEntity.status(UserSuccessCode.USER_READ_LIST_SUCCESS.getStatus())
	            .body(ApiResponse.success(UserSuccessCode.USER_READ_LIST_SUCCESS, savedRecipePage));
	}

	
	
	
	
	// 작성한 레시피
	@GetMapping("/users/{id}/recipes")
	public ResponseEntity<?> readUserRecipeList(
			@PathVariable Integer id,
			@RequestParam int page,
			@RequestParam int size) {
		
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
		if (!userService.readUserById(id).getRole().equals(Role.ROLE_ADMIN)) {
			if (id != userService.readUserByUsername(username).getId()) {
				throw new ApiException(UserErrorCode.USER_FORBIDDEN);
			}
		}
		
		Pageable pageable = PageRequest.of(page, size);
		Page<RecipeReadMyResponseDto> recipePage = recipeService.readRecipePageByUserId(id, pageable);
		
		return ResponseEntity.status(UserSuccessCode.USER_READ_LIST_SUCCESS.getStatus())
				.body(ApiResponse.success(UserSuccessCode.USER_READ_LIST_SUCCESS, recipePage));
	}
	
	
	/*********** 아래 요청명 다 적절히 수정 필요 !!! ***********/
	
	
	// 팔로워 목록
	@GetMapping("/users/{id}/followers")
	public List<UserDto> listFollowers() {
		return null;
	}
	
	
	
	// 팔로잉 목록
	@GetMapping("/users/{id}/following")
	public List<UserDto> listFollowing() {
		return null;
	}
	
	
	
	// 로그인 한 사용자가 특정 사용자를 팔로우하고 있는지 확인
	@GetMapping("/users/{id}/following/{targetId}")
	public boolean checkUserFollow() {
		return false;
	}
	
	
	
	
	// 로그인 한 사용자의 레시피 좋아요 여부
	@GetMapping("/users/{userId}/likes/{tablename}/{recodenum}")
	public boolean checkUserLike(
	        @PathVariable("userId") String userId,
	        @PathVariable("tablename") String tablename,
	        @PathVariable("recodenum") int recodenum) {

	    return true;
	}
	
	
	
	// 좋아요 및 팔로우
	@PostMapping("/users/{userId}/likes/{tablename}/{recodenum}")
	public int like() {
		return 0;
	}
	
	
	
	// 좋아요 삭제 및 언팔로우
	@DeleteMapping("/{userId}/likes/{tablename}/{recodenum}")
	public int unlike() {
		return 0;
	}
	
	
	
	// 사용자의 클래스 결석 횟수 조회
	@GetMapping("/{userId}/applies/count")
	public int countApply(
			@PathVariable("userId") String userId) {
		return 0;
	}
	
	
	
	
	
	


	
	
	// 로그인 한 사용자가 받은 후원 조회
	@GetMapping("/supports")
	public List<FundDTO> listMySupport() {
		return null;
	}
	
	
	
	// 로그인 한 유저가 포인트 충전 요청
	@PostMapping("/points/deposit")
	public int depositPoint() {
		return 0;
	}
	
	
	
	// 특정 유저가 포인트 인출 요청
	@PostMapping("/{userId}/points/withdraw")
	public int withdrawPoint(
			@PathVariable("userId") String userId) {
		return 0;
	}
	
	
}
