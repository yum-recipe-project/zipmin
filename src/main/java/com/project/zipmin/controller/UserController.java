package com.project.zipmin.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.zipmin.api.ApiException;
import com.project.zipmin.api.ApiResponse;
import com.project.zipmin.api.UserErrorCode;
import com.project.zipmin.api.UserSuccessCode;
import com.project.zipmin.dto.TokenDto;
import com.project.zipmin.dto.UserCreateRequestDto;
import com.project.zipmin.dto.UserCreateResponseDto;
import com.project.zipmin.dto.UserPasswordCheckRequestDto;
import com.project.zipmin.dto.UserPasswordUpdateRequestDto;
import com.project.zipmin.dto.UserPointReadResponseDto;
import com.project.zipmin.dto.UserProfileReadResponseDto;
import com.project.zipmin.dto.UserReadPasswordRequestDto;
import com.project.zipmin.dto.UserReadResponseDto;
import com.project.zipmin.dto.UserReadUsernameRequestDto;
import com.project.zipmin.dto.UserUpdateRequestDto;
import com.project.zipmin.dto.UserUpdateResponseDto;
import com.project.zipmin.dto.like.LikeCreateRequestDto;
import com.project.zipmin.dto.like.LikeCreateResponseDto;
import com.project.zipmin.dto.like.LikeDeleteRequestDto;
import com.project.zipmin.entity.Role;
import com.project.zipmin.service.ReissueService;
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
import com.project.zipmin.swagger.UserReadSuccessResponse;
import com.project.zipmin.swagger.UserReadUsernameSuccessResponse;
import com.project.zipmin.swagger.UserTelDuplicatedResponse;
import com.project.zipmin.swagger.UserUnauthorizedAccessResponse;
import com.project.zipmin.swagger.UserUpdateFailResponse;
import com.project.zipmin.swagger.UserUpdateSuccessResponse;
import com.project.zipmin.swagger.UserUsernameDuplicatedResponse;
import com.project.zipmin.swagger.UserUsernameNotDuplicatedResponse;
import com.project.zipmin.util.CookieUtil;
import com.project.zipmin.util.JwtUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name = "User API", description = "사용자 관련 API")
public class UserController {
	
	private final UserService userService;
	private final ReissueService reissueService;
	
	private final JwtUtil jwtUtil;
	
	
	
	
	
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
	// 사용자 조회
	@GetMapping("/users/{id}")
	public ResponseEntity<?> readUser(@Parameter(description = "사용자의 일련번호") @PathVariable int id) {
		
		UserReadResponseDto userDto = userService.readUserById(id);
		
		return ResponseEntity.status(UserSuccessCode.USER_READ_SUCCESS.getStatus())
					.body(ApiResponse.success(UserSuccessCode.USER_READ_SUCCESS, userDto));
	}
	
	
	
	
	
	@Operation(
	    summary = "사용자 프로필 조회"
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "200",
				description = "사용자 조회 성공",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserReadSuccessResponse.class))),
		
		// LIKE_COUNT_FAIL
		
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserInvalidInputResponse.class))),
		
		// LIKE_INVALID_INPUT
		
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
	// 사용자 프로필 조회
	@GetMapping("/users/{id}/profile")
	public ResponseEntity<?> readUserProfile(@Parameter(description = "사용자의 일련번호", required = true, example = "1") @PathVariable int id) {
		
		UserProfileReadResponseDto userDto = userService.readUserProfileById(id);
		
		return ResponseEntity.status(UserSuccessCode.USER_READ_SUCCESS.getStatus())
				.body(ApiResponse.success(UserSuccessCode.USER_READ_SUCCESS, userDto));
	}
	
	
	
	
	
	@Operation(
	    summary = "사용자 아이디 검증"
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
	// 사용자 아이디 검증
	@GetMapping("/users/check-username")
	public ResponseEntity<?> checkUsername(
			@Parameter(description = "사용자 아이디") @RequestParam String username) {

        // 아이디 중복확인
        if (userService.existsUsername(username)) {
        	throw new ApiException(UserErrorCode.USER_USERNAME_DUPLICATED);
        }

        return ResponseEntity.status(UserSuccessCode.USER_USERNAME_NOT_DUPLICATED.getStatus())
        		.body(ApiResponse.success(UserSuccessCode.USER_USERNAME_NOT_DUPLICATED, null));
	}
	
	
	
	
	
	@Operation(
	    summary = "사용자 이메일 검증"
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
				responseCode = "409",
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
	// 사용자 이메일 검증
	@PostMapping("/users/check-email")
	public ResponseEntity<?> checkEmail(
			@Parameter(description = "비밀번호 조회 요청 정보") @RequestBody UserReadPasswordRequestDto userRequestDto) {
		
		UserReadResponseDto userResponseDto = userService.readUserByUsernameAndEmail(userRequestDto);		
		
		return ResponseEntity.status(UserSuccessCode.USER_READ_SUCCESS.getStatus())
				.body(ApiResponse.success(UserSuccessCode.USER_READ_SUCCESS, userResponseDto));
	}
	
	
	
	
	
	@Operation(
	    summary = "사용자 비밀번호 검증"
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
	// 사용자 비밀번호 검증
	@PostMapping("/users/{id}/check-password")
	public ResponseEntity<?> verifyPassword(
			@Parameter(description = "사용자의 일련번호") @PathVariable Integer id,
			@Parameter(description = "비밀번호 검증 요청 정보") @RequestBody UserPasswordCheckRequestDto userDto) {

		// 입력값 검증
		if (id == null) {
			throw new ApiException(UserErrorCode.USER_INVALID_INPUT);
		}		
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
		    throw new ApiException(UserErrorCode.USER_UNAUTHORIZED_ACCESS);
		}
		
		// 권한 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		if (!userService.readUserByUsername(username).getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			if (!userService.readUserByUsername(username).getRole().equals(Role.ROLE_ADMIN.name())) {
				if (userService.readUserByUsername(username).getId() != id) {
					throw new ApiException(UserErrorCode.USER_FORBIDDEN);
				}
			}
		}
		userDto.setId(userService.readUserByUsername(username).getId());
		
		// 비밀번호 확인
		userService.checkPassword(userDto);
		
		return ResponseEntity.status(UserSuccessCode.USER_CORRECT_PASSWORD.getStatus())
				.body(ApiResponse.success(UserSuccessCode.USER_CORRECT_PASSWORD, null));
	}
	
	
	
	
	
	@Operation(
	    summary = "사용자 비밀번호 검증"
	)
	@ApiResponses(value = {
		// USER_VALID_TOKEN
		// USER_TOKEN_MISSING
		// USER_TOKEN_INVALID
		// USER_TOKEN_EXPIRED
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "500",
				description = "서버 내부 오류",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = InternalServerErrorResponse.class)))
	})
	// 비밀번호 토큰 검증
	@GetMapping("/users/check-token")
	public ResponseEntity<?> checkToken(@RequestHeader("Authorization") String authorization) {
		
		// 토큰 추출
		if (authorization == null || !authorization.startsWith("Bearer ")) {
			throw new ApiException(UserErrorCode.USER_TOKEN_MISSING);
		}
        String token = authorization.substring(7);
        
        userService.checkToken(token);
		
        return ResponseEntity.status(UserSuccessCode.USER_VALID_TOKEN.getStatus())
				.body(ApiResponse.success(UserSuccessCode.USER_VALID_TOKEN, null));
	}
	
	
	
	
	
	@Operation(
	    summary = "사용자 아이디 조회"
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "200",
				description = "사용자 아이디 조회 성공",
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
	// 사용자 아이디 조회
	@PostMapping("/users/find-username")
	public ResponseEntity<?> findUsername(
			@Parameter(description = "아이디 조회 요청 정보") @RequestBody UserReadUsernameRequestDto userRequestDto) {
		
		// 아이디 조회
		String username = userService.findUsername(userRequestDto).getUsername();
		
		return ResponseEntity.status(UserSuccessCode.USER_READ_USERNAME_SUCCESS.getStatus())
				.body(ApiResponse.success(UserSuccessCode.USER_READ_USERNAME_SUCCESS, Map.of("username", username)));
	}
	

	
	
	
	
	@Operation(
	    summary = "사용자 비밀번호 조회"
	)
	@ApiResponses(value = {
			
		// 200 USER_READ_PASSWORD_SUCCESS
		// 400 USER_SEND_EMAIL_FAIL
			
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
	// 사용자 비밀번호 조회
	@PostMapping("/users/find-password")
	public ResponseEntity<?> findPassword(
			@Parameter(description = "비밀번호 조회 요청 정보") @RequestBody UserReadPasswordRequestDto userDto) {
		
		// 비밀번호 조회
		userService.findPassword(userDto);
		
		return ResponseEntity.status(UserSuccessCode.USER_READ_PASSWORD_SUCCESS.getStatus())
				.body(ApiResponse.success(UserSuccessCode.USER_READ_PASSWORD_SUCCESS, null));
	}
	
	
	
	
	
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
	// 사용자 작성
	@PostMapping("/users")
	public ResponseEntity<?> createUser(
			@Parameter(description = "사용자 작성 요청 정보") @RequestBody UserCreateRequestDto userRequestDto) {
		
		// 로그인 여부 확인 후 권한 설정
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
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
	
	

	
	
	
	// UPDATE_TOKEN
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
	// 사용자 수정
	@PatchMapping("/users/{id}")
	public ResponseEntity<?> updateUser(
			@Parameter(description = "사용자의 일련번호") @PathVariable Integer id,
			@Parameter(description = "사용자 수정 요청 정보") @RequestBody UserUpdateRequestDto userRequestDto,
			HttpServletResponse response) {

		// 입력값 검증
		if (id == null) {
			throw new ApiException(UserErrorCode.USER_INVALID_INPUT);
		}
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
		    throw new ApiException(UserErrorCode.USER_UNAUTHORIZED_ACCESS);
		}
		
		// 권한 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		if (!userService.readUserByUsername(username).getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
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
			else {
				if (userService.readUserByUsername(username).getId() != id) {
					throw new ApiException(UserErrorCode.USER_FORBIDDEN);
				}
			}
		}
		userRequestDto.setId(id);
		
		UserUpdateResponseDto userResponseDto = userService.updateUser(userRequestDto);
		
		// 토큰 재발급
		String access = jwtUtil.createJwt("access", userResponseDto.getId(), userResponseDto.getUsername(),
				userResponseDto.getNickname(), userResponseDto.getAvatar(), userResponseDto.getRole(), 60 * 60 * 60L);
		String refresh = jwtUtil.createJwt("refresh", userResponseDto.getId(), userResponseDto.getUsername(),
				userResponseDto.getNickname(), userResponseDto.getAvatar(), userResponseDto.getRole(), 86400_000L);
		
		reissueService.addRefresh(userResponseDto.getUsername(), refresh, 86400_000L);
		
		response.addCookie(CookieUtil.createCookie("refresh", refresh, 86_400));
		response.setHeader("Authorization", "Bearer " + access);
		
		TokenDto tokenDto = TokenDto.toDto(access);

		return ResponseEntity.status(UserSuccessCode.USER_UPDATE_TOKEN_SUCCESS.getStatus())
				.body(ApiResponse.success(UserSuccessCode.USER_UPDATE_TOKEN_SUCCESS, tokenDto));
	}
	
	
	
	
	
	@Operation(
			summary = "사용자 비밀번호 수정"
			)
	@ApiResponses(value = {
			// TODO : 순서 적절히 변경 필요
			// USER_UPDATE_PASSWORD_SUCCESS
			// USER_TOKEN_MISSING
			// USER_TOKEN_INVALID
			// USER_TOKEN_EXPIRED
			// USER_UPDATE_FAIL
			// USER_TOKEN_UPDATE_FAIL
			@io.swagger.v3.oas.annotations.responses.ApiResponse(
					responseCode = "500",
					description = "서버 내부 오류",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = InternalServerErrorResponse.class)))
	})
	// 사용자 비밀번호 수정
	@PatchMapping("/users/password")
	public ResponseEntity<?> changePassword(
			@RequestHeader("Authorization") String authorization,
			@Parameter(description = "사용자 비밀번호 수정 요청 정보") @RequestBody UserPasswordUpdateRequestDto userRequestDto) {
		
		// 토큰 추출
		if (authorization == null || !authorization.startsWith("Bearer ")) {
			throw new ApiException(UserErrorCode.USER_TOKEN_MISSING);
		}
		String token = authorization.substring(7);
		userRequestDto.setToken(token);
		
		userService.updatePassword(userRequestDto);
		
		return ResponseEntity.status(UserSuccessCode.USER_UPDATE_PASSWORD_SUCCESS.getStatus())
				.body(ApiResponse.success(UserSuccessCode.USER_UPDATE_PASSWORD_SUCCESS, null));
	}
	
	
	
	
	
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
				description = "로그인되지 않은 사용자",
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
	// 사용자 삭제
	@DeleteMapping("/users/{id}")
	public ResponseEntity<?> deleteMember(
			@Parameter(description = "사용자의 일련번호") @PathVariable Integer id) {
		
		// 입력값 검증
		if (id == null) {
			throw new ApiException(UserErrorCode.USER_INVALID_INPUT);
		}
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
		    throw new ApiException(UserErrorCode.USER_UNAUTHORIZED_ACCESS);
		}
		
		// 권한 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		if (userService.readUserByUsername(username).getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			if (userService.readUserByUsername(username).getId() == id) {
				throw new ApiException(UserErrorCode.USER_SUPER_ADMIN_FORBIDDEN);
			}
		}
		else {
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


	
	
	
	
	// USER_READ_LIST_SUCCESS
	// USER_READ_LIST_FAIL
	// LIKE_READ_LIST_FAIL
	// LIKE_EXIST_FAIL
	// USER_INVALID_INPUT
	// LIKE_INVALID_INPUT
	// USER_UNAUTHORIZED_ACCESS
	// USER_FORBIDDEN
	// USER_NOT_FOUND
	
	// 사용자가 좋아요한 사용자 목록 조회
	@GetMapping("/users/{id}/like-users")
	public ResponseEntity<?> readUserLikeUserList(
			@PathVariable Integer id) {
		
	    // 입력값 검증
	    if (id == null) {
	        throw new ApiException(UserErrorCode.USER_INVALID_INPUT);
	    }
	    
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
		    throw new ApiException(UserErrorCode.USER_UNAUTHORIZED_ACCESS);
		}
	    
		// 권한 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		if (!userService.readUserByUsername(username).getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			if (!userService.readUserByUsername(username).getRole().equals(Role.ROLE_ADMIN.name())) {
				if (userService.readUserByUsername(username).getId() != id) {
					throw new ApiException(UserErrorCode.USER_FORBIDDEN);
				}
			}
		}
	    
	    List<UserProfileReadResponseDto> userList = userService.readLikeUserList(id);
		
		return ResponseEntity.status(UserSuccessCode.USER_READ_LIST_SUCCESS.getStatus())
				.body(ApiResponse.success(UserSuccessCode.USER_READ_LIST_SUCCESS, userList));
	}
	
	
	
	
	// USER_READ_LIST_SUCCESS
	// USER_READ_LIST_FAIL
	// LIKE_READ_LIST_FAIL
	// LIKE_EXIST_FAIL
	// USER_INVALID_INPUT
	// LIKE_INVALID_INPUT
	// USER_UNAUTHORIZED_ACCESS
	// USER_FORBIDDEN
	// USER_NOT_FOUND
	// 사용자를 좋아하는 사용자 목록 조회
	@GetMapping("/users/{id}/liked-users")
	public ResponseEntity<?> readUserLikedUserList(
			@PathVariable Integer id) {
		
		// 입력값 검증
		if (id == null) {
			throw new ApiException(UserErrorCode.USER_INVALID_INPUT);
		}
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
		    throw new ApiException(UserErrorCode.USER_UNAUTHORIZED_ACCESS);
		}
	    
		// 권한 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		if (!userService.readUserByUsername(username).getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			if (!userService.readUserByUsername(username).getRole().equals(Role.ROLE_ADMIN.name())) {
				if (userService.readUserByUsername(username).getId() != id) {
					throw new ApiException(UserErrorCode.USER_FORBIDDEN);
				}
			}
		}
		
		List<UserProfileReadResponseDto> userList = userService.readLikedUserList(id);
		
		return ResponseEntity.status(UserSuccessCode.USER_READ_LIST_SUCCESS.getStatus())
				.body(ApiResponse.success(UserSuccessCode.USER_READ_LIST_SUCCESS, userList));
	}
	
	
	
	
	
	// USER_UNAUTHORIZED_ACCESS
	// USER_LIKE_SUCCESS
	// USER_INVALID_INPUT
	// USER_NOT_FOUND
	// LIKE_INVALID_INPUT
	// LIKE_DUPLICATE
	// LIKE_CREATE_FAIL
	
	// 사용자 좋아요
	@PostMapping("/users/{id}/likes")
	public ResponseEntity<?> likeUser(
			@Parameter(description = "사용자의 일련번호") @PathVariable int id,
			@Parameter(description = "좋아요 작성 요청 정보") @RequestBody LikeCreateRequestDto likeRequestDto) {
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
		    throw new ApiException(UserErrorCode.USER_UNAUTHORIZED_ACCESS);
		}
		likeRequestDto.setUserId(userService.readUserByUsername(authentication.getName()).getId());
		
		LikeCreateResponseDto likeResponseDto = userService.likeUser(likeRequestDto);
		
		return ResponseEntity.status(UserSuccessCode.USER_LIKE_SUCCESS.getStatus())
				.body(ApiResponse.success(UserSuccessCode.USER_LIKE_SUCCESS, likeResponseDto));
		
	}
	
	
	
	
	// USER_UNAUTHORIZED_ACCESS
	// USER_UNLIKE_SUCCESS
	// USER_INVALID_INPUT
	// USER_NOT_FOUND
	// LIKE_INVALID_INPUT
	// LIKE_NOT_FOUND
	// (LIKE_FORBIDDEN)
	// LIKE_DELETE_FAIL
	
	// 사용자 좋아요 취소
	@DeleteMapping("/users/{id}/likes")
	public ResponseEntity<?> unlikeUser(
			@Parameter(description = "사용자의 일련번호") @PathVariable int id,
			@Parameter(description = "좋아요 삭제 요청 정보") @RequestBody LikeDeleteRequestDto likeDto) {
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
		    throw new ApiException(UserErrorCode.USER_UNAUTHORIZED_ACCESS);
		}
		likeDto.setUserId(userService.readUserByUsername(authentication.getName()).getId());
		
		userService.unlikeUser(likeDto);
		
		return ResponseEntity.status(UserSuccessCode.USER_UNLIKE_SUCCESS.getStatus())
				.body(ApiResponse.success(UserSuccessCode.USER_UNLIKE_SUCCESS, null));
	}
	
	

	
	
	// 사용자 포인트 조회
//	@GetMapping("/users/{id}/point")
//	public ResponseEntity<?> readUserPoint(
//	        @PathVariable int id) {
//
//	    UserPointReadResponseDto pointDto = userService.readUserPointById(id);
//
//	    return ResponseEntity.status(UserSuccessCode.USER_READ_SUCCESS.getStatus())
//	            .body(ApiResponse.success(UserSuccessCode.USER_READ_SUCCESS, pointDto));
//	}
//
//
//	
	
	
	
	
	
	

	
}
