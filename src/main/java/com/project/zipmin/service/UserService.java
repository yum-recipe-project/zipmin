package com.project.zipmin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.method.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.zipmin.api.ApiException;
import com.project.zipmin.api.AuthErrorCode;
import com.project.zipmin.api.UserErrorCode;
import com.project.zipmin.dto.CustomUserDetails;
import com.project.zipmin.dto.UserReadRequestDto;
import com.project.zipmin.dto.UserPasswordCheckRequestDto;
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

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserMapper userMapper;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	

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
	
	
	
	// 이름과 전화번호로 사용자 조회
	public UserReadResponseDto readUserByNameAndTel(UserReadRequestDto userDto) {
		
		// 입력값 검증
//		if (userDto == null || userDto.getName() == "" || userDto.getTel() == null) {
//			throw new ApiException(UserErrorCode.USER_INVALID_INPUT);
//		}

		// 사용자 조회
		User user = userRepository.findByNameAndTel(userDto.getName(), userDto.getTel())
				.orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));
		
		return userMapper.toReadResponseDto(user);
	}

	
	
	// 사용자 작성
	public UserCreateResponseDto createUser(UserCreateRequestDto userRequestDto) {
		
		// 입력값 검증
		if (userRequestDto == null || userRequestDto.getUsername() == null || userRequestDto.getTel() == null || userRequestDto.getPassword() == null || userRequestDto.getNickname() == null || userRequestDto.getName() == null || userRequestDto.getEmail() == null) {
			throw new ApiException(UserErrorCode.USER_INVALID_INPUT);
		}
		
		User user = userMapper.toEntity(userRequestDto);
		user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
		user.setRole(Role.ROLE_USER);
		
		// 중복 아이디 검사
		if (userRepository.existsByUsername(userRequestDto.getUsername())) {
			throw new ApiException(UserErrorCode.USER_USERNAME_DUPLICATED);
		}
		
		// 중복 전화번호 검사
		if (userRepository.existsByTel(userRequestDto.getTel())) {
			throw new ApiException(UserErrorCode.USER_TEL_DUPLICATED);
		}
		
		// 중복 이메일 검사
		if (userRepository.existsByEmail(userRequestDto.getEmail())) {
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







	


}
