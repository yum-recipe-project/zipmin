package com.project.zipmin.service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.zipmin.api.ApiException;
import com.project.zipmin.api.AuthErrorCode;
import com.project.zipmin.api.LikeErrorCode;
import com.project.zipmin.api.UserErrorCode;
import com.project.zipmin.dto.CustomUserDetails;
import com.project.zipmin.dto.LikeCreateRequestDto;
import com.project.zipmin.dto.LikeCreateResponseDto;
import com.project.zipmin.dto.LikeDeleteRequestDto;
import com.project.zipmin.dto.LikeReadResponseDto;
import com.project.zipmin.dto.MailDto;
import com.project.zipmin.dto.UserReadUsernameRequestDto;
import com.project.zipmin.dto.UserPasswordCheckRequestDto;
import com.project.zipmin.dto.UserProfileReadResponseDto;
import com.project.zipmin.dto.UserReadPasswordRequestDto;
import com.project.zipmin.dto.UserDto;
import com.project.zipmin.dto.UserCreateRequestDto;
import com.project.zipmin.dto.UserCreateResponseDto;
import com.project.zipmin.dto.UserReadResponseDto;
import com.project.zipmin.dto.UserUpdateRequestDto;
import com.project.zipmin.dto.UserUpdateResponseDto;
import com.project.zipmin.entity.User;
import com.project.zipmin.entity.Role;
import com.project.zipmin.mapper.ChompMapper;
import com.project.zipmin.mapper.UserMapper;
import com.project.zipmin.repository.UserRepository;

import io.jsonwebtoken.lang.Collections;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserMapper userMapper;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	private final LikeService likeService;
	private final MailService mailService;
	
	
	

	
	// 사용자 목록 조회
	public Page<UserReadResponseDto> readUserPage(String category, Pageable pageable) {
		
		// 사용자 목록 조회
		Page<User> userPage;
		try {
			if (category == null || category.isBlank()) {
	            userPage = userRepository.findAll(pageable);
	        }
			else {
				List<Role> roles = null;
				if (category.equalsIgnoreCase("admin")) {
				    roles = List.of(Role.ROLE_ADMIN, Role.ROLE_SUPER_ADMIN);
				}
				else if (category.equalsIgnoreCase("user")) {
				    roles = List.of(Role.ROLE_USER);
				}
				userPage = userRepository.findByRoleIn(roles, pageable);
	        }
		}
		catch (Exception e) {
			throw new ApiException(UserErrorCode.USER_READ_LIST_FAIL);
		}
		
		List<UserReadResponseDto> userDtoList = new ArrayList<UserReadResponseDto>();
		for (User user : userPage) {
			UserReadResponseDto userDto = userMapper.toReadResponseDto(user);
			userDtoList.add(userDto);
		}
		
		return new PageImpl<>(userDtoList, pageable, userPage.getTotalElements());
	}
	
	
	
	
	
	
	
	// 사용자가 좋아요한 사용자 목록 조회
	public List<UserProfileReadResponseDto> readLikeUserList(Integer userId) {
		
		// 입력값 검증
		if (userId == null) {
			throw new ApiException(UserErrorCode.USER_INVALID_INPUT);
		}
		
		// 좋아요 일련번호 목록 조회
		List<LikeReadResponseDto> likeDtoList = likeService.readLikeListByTablenameAndUserId("users", userId);
		List<Integer> idList = likeDtoList.stream()
				.map(LikeReadResponseDto::getRecodenum)
				.collect(Collectors.toCollection(LinkedHashSet::new))
				.stream().toList();
		
		if (idList.isEmpty()) {
			return Collections.emptyList();
		}
		
		// 사용자 목록 조회
		List<User> userList = null;
		try {
			userList = userRepository.findAllByIdIn(idList);
		}
		catch (Exception e) {
			throw new ApiException(UserErrorCode.USER_READ_LIST_FAIL);
		}
		
		// 사용자 목록 응답 구성
		List<UserProfileReadResponseDto> userDtoList = new ArrayList<>();	
		for (User user : userList) {
			UserProfileReadResponseDto userDto = userMapper.toReadProfileResponseDto(user);
			
			// 좋아요 여부
			userDto.setLiked(likeService.existsUserLike("users", userDto.getId(), userId));
			
			userDtoList.add(userDto);
		}
		
		return userDtoList;
	}
	
	
	
	// 사용자를 좋아하는 사용자 목록 조회
	public List<UserProfileReadResponseDto> readLikedUserList(Integer userId) {
		
		// 입력값 검증
		if (userId == null) {
			throw new ApiException(UserErrorCode.USER_INVALID_INPUT);
		}
		
		// 좋아요 일련번호 목록 조회
		List<LikeReadResponseDto> likeDtoList = likeService.readLikeListByTablenameAndRecodenum("users", userId);
		List<Integer> idList = likeDtoList.stream()
				.map(LikeReadResponseDto::getUserId)
				.collect(Collectors.toCollection(LinkedHashSet::new))
				.stream().toList();
		
		if (idList.isEmpty()) {
			return Collections.emptyList();
		}
		
		// 사용자 목록 조회
		List<User> userList = null;
		try {
			userList = userRepository.findAllByIdIn(idList);
		}
		catch (Exception e) {
			throw new ApiException(UserErrorCode.USER_READ_LIST_FAIL);
		}
		
		// 사용자 목록 응답 구성
		List<UserProfileReadResponseDto> userDtoList = new ArrayList<>();	
		for (User user : userList) {
			UserProfileReadResponseDto userDto = userMapper.toReadProfileResponseDto(user);
			
			// 좋아요 여부
			userDto.setLiked(likeService.existsUserLike("users", userDto.getId(), userId));
			
			userDtoList.add(userDto);
		}
		
		return userDtoList;
	}
	
	
	
	
	
	
	
	// 아이디로 사용자 조회
	public UserReadResponseDto readUserById(Integer id) {
		
		// 입력값 검증
		if (id == null) {
			throw new ApiException(UserErrorCode.USER_INVALID_INPUT);
		}
		
		// 사용자 조회
		User user = userRepository.findById(id)
				.orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));
		
		return userMapper.toReadResponseDto(user);
	}
	
	
	
	// 아이디로 사용자 프로필 조회
	public UserProfileReadResponseDto readUserProfileById(Integer id) {
		
		// 입력값 검증
		if (id == null) {
			throw new ApiException(UserErrorCode.USER_INVALID_INPUT);
		}
		
		// 사용자 조회
		User user = userRepository.findById(id)
				.orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));
		
		// 사용자 응답 구성
		UserProfileReadResponseDto userDto = userMapper.toReadProfileResponseDto(user);
		userDto.setLikecount(likeService.countLike("users", user.getId()));
		
		// 좋아요 여부
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {
			String username = authentication.getName();
			int userId = readUserByUsername(username).getId();
			userDto.setLiked(likeService.existsUserLike("users", user.getId(), userId));
		}
		
		return userDto;
	}
	
	
	
	
	// 사용자명으로 사용자 조회
	public UserReadResponseDto readUserByUsername(String username) {
		
		// 입력값 검증
		if (username == null) {
			throw new ApiException(UserErrorCode.USER_INVALID_INPUT);
		}
		
		// 사용자 조회
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));

		return userMapper.toReadResponseDto(user);
	}
	
	
	
	// 이름과 전화번호로 사용자 조회 (아이디 찾기)
	// TODO : 함수 분리할 것
	public UserReadResponseDto readUserByNameAndTel(UserReadUsernameRequestDto userDto) {
		
		// 입력값 검증
//		if (userDto == null || userDto.getName() == "" || userDto.getTel() == null) {
//			throw new ApiException(UserErrorCode.USER_INVALID_INPUT);
//		}

		// 사용자 조회
		User user = userRepository.findByNameAndTel(userDto.getName(), userDto.getTel())
				.orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));
		
		return userMapper.toReadResponseDto(user);
	}
	
	
	
	
	// 사용자명과 이메일로 사용자 조회
	public UserReadResponseDto readUserByUsernameAndEmail(UserReadPasswordRequestDto userDto) {
		
		// 입력값 검증
		if (userDto == null || userDto.getUsername() == null || userDto.getEmail() == null) {
			throw new ApiException(UserErrorCode.USER_INVALID_INPUT);
		}
		
		// 사용자 조회
		User user = userRepository.findByUsernameAndEmail(userDto.getUsername(), userDto.getEmail())
				.orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));
		
		return userMapper.toReadResponseDto(user);
	}

	
	
	
	// 비밀번호 찾기
	public void findPassword(UserReadPasswordRequestDto userDto) {
		
		System.err.println(userDto);
		
		// 입력값 검증
		if (userDto == null || userDto.getUsername() == null || userDto.getEmail() == null) {
			throw new ApiException(UserErrorCode.USER_INVALID_INPUT);
		}
		
		// 사용자 조회
		User user = userRepository.findByUsernameAndEmail(userDto.getUsername(), userDto.getEmail())
				.orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));
		
	
		String tmpPassword = getTmpPassword();
		
		// 메일 보내기
		MailDto mailDto = new MailDto();
		mailDto.setTo(user.getEmail());
		mailDto.setSubject("집밥의민족 계정 암호 재설정");
		// TODO : 내용 수정
		mailDto.setContent("임시비밀번호 이거임 -> " + tmpPassword);
		
		mailService.sendEmail(mailDto);
		
		// 여기서 랜덤 생성된 비밀번호로 업데이트하기
		user.setPassword(passwordEncoder.encode(tmpPassword));
		// 사용자 수정
		try {
			user = userRepository.save(user);
		}
		catch (Exception e) {
			throw new ApiException(UserErrorCode.USER_UPDATE_FAIL);
		}
	}
	
	  public String getTmpPassword() {
	      char[] charSet = new char[]{ '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
	              'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
	              'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

	      String newPassword = "";

	      for (int i = 0; i < 10; i++) {
	          int idx = (int) (charSet.length * Math.random());
	          newPassword += charSet[idx];
	      }

	      return newPassword;
	  }

	

	
	
	
	
	// 사용자 작성
	public UserCreateResponseDto createUser(UserCreateRequestDto userRequestDto) {
		
		// 입력값 검증
		if (userRequestDto == null || userRequestDto.getUsername() == null || userRequestDto.getPassword() == null || userRequestDto.getNickname() == null || userRequestDto.getName() == null) {
			throw new ApiException(UserErrorCode.USER_INVALID_INPUT);
		}
		
		User user = userMapper.toEntity(userRequestDto);
		user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
		user.setRole(userRequestDto.getRole());
		
		// 중복 아이디 검사
		if (userRepository.existsByUsername(userRequestDto.getUsername())) {
			throw new ApiException(UserErrorCode.USER_USERNAME_DUPLICATED);
		}
		
		// 중복 전화번호 검사
		if (userRequestDto.getTel() != null && userRepository.existsByTel(userRequestDto.getTel())) {
			throw new ApiException(UserErrorCode.USER_TEL_DUPLICATED);
		}
		
		// 중복 이메일 검사
		if (userRequestDto.getEmail() != null && userRepository.existsByEmail(userRequestDto.getEmail())) {
			throw new ApiException(UserErrorCode.USER_EMAIL_DUPLICATED);
		}
		
		// 사용자 저장
		try {
			user = userRepository.save(user);
			return userMapper.toCreateResponseDto(user);
		}
		catch (Exception e) {
			throw new ApiException(UserErrorCode.USER_CREATE_FAIL);
		}
		
	}

	

	// 사용자 수정
	public UserUpdateResponseDto updateUser(UserUpdateRequestDto userDto) {
		
		// 입력값 검증
		if (userDto == null || userDto.getId() == null) {
			throw new ApiException(UserErrorCode.USER_INVALID_INPUT);
		}
		
		// 사용자 존재 여부 판단
		User user = userRepository.findById(userDto.getId())
				.orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));

		// 필요한 필드 수정
		if (userDto.getUsername() != null) {
			if (!userDto.getUsername().equals(user.getUsername()) && userRepository.existsByUsername(userDto.getUsername())) {
				throw new ApiException(UserErrorCode.USER_USERNAME_DUPLICATED);
			}
			user.setUsername(userDto.getUsername());
		}
		if (userDto.getPassword() != null) {
			user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		}
		if (userDto.getEmail() != null) {
			if (!userDto.getEmail().equals(user.getEmail()) && userRepository.existsByEmail(userDto.getEmail())) {
				throw new ApiException(UserErrorCode.USER_EMAIL_DUPLICATED);
			}
			user.setEmail(userDto.getEmail());
		}
		if (userDto.getName() != null) {
			user.setName(userDto.getName());
		}
		if (userDto.getNickname() != null) {
			user.setNickname(userDto.getNickname());
		}
		if (userDto.getTel() != null) {
			if (!userDto.getTel().equals(user.getTel()) && userRepository.existsByTel(userDto.getTel())) {
				throw new ApiException(UserErrorCode.USER_TEL_DUPLICATED);
			}
			user.setTel(userDto.getTel());
		}
		if (userDto.getAvatar() != null) {
			user.setAvatar(userDto.getAvatar());
		}
		if (userDto.getIntroduce() != null) {
			user.setIntroduce(userDto.getIntroduce());
		}
		
		// 사용자 수정
		try {
			user = userRepository.save(user);
			return userMapper.toUpdateResponseDto(user);
		}
		catch (Exception e) {
			throw new ApiException(UserErrorCode.USER_UPDATE_FAIL);
		}
		
	}

	
	
	// 사용자 삭제
	public void deleteUser(Integer id) {
		
		// 입력값 검증
		if (id == null) {
			throw new ApiException(UserErrorCode.USER_INVALID_INPUT);
		}
		
		// 사용자 존재 여부 판단
		if (!userRepository.existsById(id)) {
			throw new ApiException(UserErrorCode.USER_NOT_FOUND);
		}
		
		// 사용자 삭제
		try {
			userRepository.deleteById(id);
		}
		catch (Exception e) {
			throw new ApiException(UserErrorCode.USER_DELETE_FAIL);
		}
		
	}
	

	
	// 비밀번호 검증
	public void checkPassword(UserPasswordCheckRequestDto userDto) {
		
		// 입력값 검증
		if (userDto == null || userDto.getId() == null || userDto.getPassword() == null) {
			throw new ApiException(UserErrorCode.USER_INVALID_INPUT);
		}
		
		// 사용자 존재 여부 판단
		User user = userRepository.findById(userDto.getId())
				.orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));
		
		// 비밀번호 검증
		if (!passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
			throw new ApiException(UserErrorCode.USER_INCORRECT_PASSWORD);
		}
		
	}
	
	
	

	
	
	// 아이디 중복확인
	public boolean existsUsername(String username) {
		
		// 입력값 검증
		if (username == null) {
			throw new ApiException(UserErrorCode.USER_INVALID_INPUT);
		}

		return userRepository.existsByUsername(username);
	}

	
	
	
	// 사용자 좋아요
	public LikeCreateResponseDto likeUser(LikeCreateRequestDto likeDto) {
		
		// 입력값 검증
		if (likeDto == null || likeDto.getTablename() == null
				|| likeDto.getRecodenum() == null || likeDto.getUserId() == null) {
			throw new ApiException(UserErrorCode.USER_INVALID_INPUT);
		}
		
		// 사용자 존재 여부 확인
		if (userRepository.existsById(likeDto.getRecodenum())) {
			new ApiException(UserErrorCode.USER_NOT_FOUND);
		}
		
		// 좋아요 저장
		try {
			return likeService.createLike(likeDto);
		}
		catch (ApiException e) {
			throw e;
		}
		catch (Exception e) {
			throw new ApiException(UserErrorCode.USER_LIKE_FAIL);
		}
		
	}
	
	
	
	
	// 사용자 좋아요 취소
	public void unlikeUser(LikeDeleteRequestDto likeDto) {
		
		// 입력값 검증
		if (likeDto == null || likeDto.getTablename() == null
				|| likeDto.getRecodenum() == null || likeDto.getUserId() == null) {
			throw new ApiException(UserErrorCode.USER_INVALID_INPUT);
		}
		
		// 사용자 존재 여부 확인
		if (userRepository.existsById(likeDto.getRecodenum())) {
			new ApiException(UserErrorCode.USER_NOT_FOUND);
		}
		
		// 좋아요 취소
		try {
			likeService.deleteLike(likeDto);
		}
		catch (ApiException e) {
			throw e;
		}
		catch (Exception e) {
			throw new ApiException(UserErrorCode.USER_UNLIKE_FAIL);
		}
		
	}
	
	
	
	
	
	
	// 유저 정보 반환
//	public User getUserEntityByUsername(String username) {
//	    if (username == null) {
//	        throw new ApiException(UserErrorCode.USER_INVALID_INPUT);
//	    }
//
//	    return userRepository.findByUsername(username)
//	            .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));
//	}







	


}
