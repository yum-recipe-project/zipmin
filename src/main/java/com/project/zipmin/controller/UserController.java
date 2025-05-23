package com.project.zipmin.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.zipmin.dto.ClassDTO;
import com.project.zipmin.dto.FundDTO;
import com.project.zipmin.dto.UserDTO;
import com.project.zipmin.jwt.JWTUtil;
import com.project.zipmin.jwt.RedisService;
import com.project.zipmin.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
	
	@Autowired
	UserService userService;
	
	private final JWTUtil jwtUtil;
	private final RedisService redisService;
	

	// 사용자 목록 조회 (관리자)
	@GetMapping("/users")
	@Operation(summary = "사용자 목록 조회", description = "모든 사용자의 목록을 조회하는 메서드입니다.",
		parameters = {
				@Parameter(in = ParameterIn.HEADER, name = "test", description = "테스트", required = true, example = "테스트임")
		})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "조회 성공")
	})
	public List<UserDTO> listMember() {
		List<UserDTO> userList = userService.getUserList();
		System.err.println(userList);
		return userList;
	}
	
	// 특정 사용자 조회
	@GetMapping("/users/{userId}")
	public UserDTO viewMember(@PathVariable("userId") String id) {
		// 프로필 넣말
		return null;
	}
	
	
	
	// 사용자 생성
	@PostMapping("/users")
	public int addMember() {
		
		return 0;
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

	
	// 시큐리티에서는 필요 없을 듯
//	@PostMapping("/login")
//	public int login() {
//		
//		return 0;
//	}
	
	
//	
//	@PostMapping("/logout")
//	public void logout(HttpServletRequest request, HttpServletResponse response) {
//		// 1. 쿠키가 있는지 확인
//		Cookie[] cookies = request.getCookies();
//		if (cookies == null) {
//			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//			return;
//		}
//		
//		// 2. Cookie 내부에 RefreshToken이 있는지 확인
//		Optional<Cookie> refreshCookie = Arrays.stream(cookies)
//				.filter(cookie -> "refresh".equals(cookie.getName()))
//				.findFirst();
//		if (refreshCookie.isPresent()) {
//			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//			return;
//		}
//		
//		// 3. RefreshToken의 값이 존재하는지 확인
//		String refreshToken = refreshCookie.get().getValue();
//		if (refreshToken == null || refreshToken.isEmpty()) {
//			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//			return;
//		}
//		
//		// 4. 존재한다면 해당 RefreshToken으로 부터 사용자명(username) 추출
//		String key = jwtUtil.getId(refreshToken);
//		
//		// 5. 사용자명을 키로 Redis에 존재하는 키의 값을 확인
//		if (redisService.getValues(key) == null) {
//			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//			return;
//		}
//		
//		// 6. 있다면 Redis에서 삭제
//		redisService.deleteValues(key);
//		
//		// 7. 클라이언트측 refresh 쿠키를 null로 변경하여 200 상태와 함께 응답
//		Cookie cookie = new Cookie("refresh", null);
//		cookie.setMaxAge(0);
//		cookie.setPath("/");
//		response.setStatus(HttpServletResponse.SC_OK);
//		response.addCookie(cookie);
//	}
	
	
	// 팔로워 목록
	@GetMapping("/users/{userId}/followers")
	public List<UserDTO> listFollowers() {
		return null;
	}
	
	
	
	// 팔로잉 목록
	@GetMapping("/users/{userId}/following")
	public List<UserDTO> listFollowing() {
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
