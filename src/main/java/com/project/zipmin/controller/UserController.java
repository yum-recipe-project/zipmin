package com.project.zipmin.controller;

import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.zipmin.api.ApiException;
import com.project.zipmin.api.ApiResponse;
import com.project.zipmin.dto.ClassDTO;
import com.project.zipmin.dto.FindUsernameRequestDto;
import com.project.zipmin.dto.FundDTO;
import com.project.zipmin.dto.UserDto;
import com.project.zipmin.dto.UserJoinDto;
import com.project.zipmin.dto.UserResponseDto;
import com.project.zipmin.entity.User;
import com.project.zipmin.mapper.UserMapper;
import com.project.zipmin.service.UserService;
import com.project.zipmin.util.JwtUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
//@Tag(name = "Example Controller", description = "This is an example controller")
public class UserController {
	
	private final UserMapper userMapper;
	private final UserService userService;
	

	// 사용자 목록 조회 (관리자)
//	@GetMapping("/users")
//	@Operation(summary = "사용자 목록 조회", description = "모든 사용자의 목록을 조회하는 메서드입니다.",
//		parameters = {
//				@Parameter(in = ParameterIn.HEADER, name = "test", description = "테스트", required = true, example = "테스트임")
//		})
//	@ApiResponses(value = {
//			@ApiResponse(responseCode = "200", description = "조회 성공")
//	})
//	public List<UserDTO> listMember() {
//		List<UserDTO> userList = userService.getUserList();
//		System.err.println(userList);
//		return userList;
//	}
	
	// 특정 사용자 조회
	@GetMapping("/users/{userId}")
	public UserDto viewMember(@PathVariable("userId") String id) {
		// 프로필 넣말
		return null;
	}
	
	
	
	// 사용자 생성 (회원가입)
	@PostMapping("/users")
	public ResponseEntity<?> addUser(@RequestBody UserJoinDto userJoinDto) {
		User user = userService.joinUser(userJoinDto);
		UserResponseDto responseDto = userMapper.toResponseDto(user);
		
		return ResponseEntity.status(HttpStatus.OK)
				.body(ApiResponse.of("SIGN_UP_SUCCESS", "회원가입이 완료되었습니다.", responseDto));
	}
	
	
	
	// 아이디 중복확인
	@GetMapping("/users/check-username")
	public ResponseEntity<?> checkUsername(@RequestParam String username) {
        if (username == null || username.trim().isEmpty()) {
        	throw new ApiException("INVALID_PARAM", "아이디는 필수 입력값입니다.");
        }

        boolean exists = userService.isUsernameDuplicated(username);
        if (exists) {
        	return ResponseEntity.status(HttpStatus.CONFLICT)
        			.body(ApiResponse.error("USERNAME_DUPLICATED", "이미 사용 중인 아이디입니다."));
        }
        
        return ResponseEntity.status(HttpStatus.OK)
        		.body(ApiResponse.success(null));
	}
	
	
	// 아이디 찾기
	@PostMapping("/users/username")
	public ResponseEntity<?> findUsername(@RequestBody FindUsernameRequestDto findUsernameRequestDto) {
		
		String username = userService.findUsername(findUsernameRequestDto);
		
		if (username == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(ApiResponse.error("NOT_FOUND_USERNAME", "아이디가 없습니다."));
		}
		
		return ResponseEntity.status(HttpStatus.OK)
					.body(ApiResponse.success(Map.of("username", username)));
	}
	
	
	
	// 사용자 정보 수정
	@PutMapping("/users/{userId}")
	public int editMember(@PathVariable("userId") String id) {
		return 0;
	}
	
	
	
	// 사용자 삭제
	@DeleteMapping("/users/{userId}")
	public int deleteMember(@PathVariable("userId") String id) {
		return 0;
	}

	
	
	// 팔로워 목록
	@GetMapping("/users/{userId}/followers")
	public List<UserDto> listFollowers() {
		return null;
	}
	
	
	
	// 팔로잉 목록
	@GetMapping("/users/{userId}/following")
	public List<UserDto> listFollowing() {
		return null;
	}
	
	
	
	// 로그인 한 사용자가 특정 사용자를 팔로우하고 있는지 확인
	@GetMapping("/users/{userId}/following/{targetId}")
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
	
	

	

	// 사용자가 참가한 모든 클래스 조회
	@GetMapping("/{userId}/classes")
	public List<ClassDTO> listUserClass(
			@PathVariable("userId") String userId) {
		return null;
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
