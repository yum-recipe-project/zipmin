package com.project.zipmin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.zipmin.api.ApiException;
import com.project.zipmin.api.AuthErrorCode;
import com.project.zipmin.api.UserErrorCode;
import com.project.zipmin.dto.CustomUserDetails;
import com.project.zipmin.dto.FindUsernameRequestDto;
import com.project.zipmin.dto.PasswordVerifyRequestDto;
import com.project.zipmin.dto.UserDto;
import com.project.zipmin.dto.UserJoinRequestDto;
import com.project.zipmin.dto.UserResponseDto;
import com.project.zipmin.dto.UserUpdateRequestDto;
import com.project.zipmin.entity.User;
import com.project.zipmin.entity.Role;
import com.project.zipmin.mapper.ChompMapper;
import com.project.zipmin.mapper.UserMapper;
import com.project.zipmin.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
	private final UserMapper userMapper;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public List<UserDto> getUserList() {
//		List<User> userList = userRepository.findAll();
//		return userMapper.userListToUserDTOList(userList);
		return null;
	}
	
	
	

	@Override
	public UserResponseDto getUserById(int userId) {
		
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));
		
		return userMapper.toResponseDto(user);
	}
	
	
	
	@Override
	public UserResponseDto joinUser(UserJoinRequestDto userJoinDto) {
		
		User user = userMapper.toEntity(userJoinDto);
		user.setPassword(passwordEncoder.encode(userJoinDto.getPassword()));
		user.setRole(Role.ROLE_USER);
		
		Boolean exists = userRepository.existsByUsername(userJoinDto.getUsername());
		if (exists) {
			throw new ApiException(UserErrorCode.USER_USERNAME_DUPLICATED);
		}
		user = userRepository.save(user);
		
		return userMapper.toResponseDto(user);
	}
	
	
	
	
	
	

	@Override
	public boolean isUsernameDuplicated(String username) {
		return userRepository.existsByUsername(username);
	}







	@Override
	public String findUsername(FindUsernameRequestDto findUsernameRequestDto) {
		
		String name = findUsernameRequestDto.getName();
		String tel = findUsernameRequestDto.getTel();
		User user = userRepository.findByNameAndTel(name, tel);
		
		if (user == null) {
			return null;
		}
		
		return user.getUsername();
	}






	@Override
	public UserResponseDto updateUser(int userId, UserUpdateRequestDto userDto) {
		
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));
		
		if (userDto.getPassword() != null) {
			user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		}
		if (userDto.getEmail() != null) {
			user.setEmail(userDto.getEmail());
		}
		if (userDto.getName() != null) {
			user.setName(userDto.getName());
		}
		if (userDto.getNickname() != null) {
			user.setNickname(userDto.getNickname());
		}
		if (userDto.getTel() != null) {
			user.setTel(userDto.getTel());
		}
		user = userRepository.save(user);
		
		return userMapper.toResponseDto(user);
	}

	
	
	@Override
	public void verifyPassword(PasswordVerifyRequestDto passwordVerifyRequestDto) {
				
		User user = userRepository.findById(passwordVerifyRequestDto.getId())
				.orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));
		
		if (!passwordEncoder.matches(passwordVerifyRequestDto.getPassword(), user.getPassword())) {
			throw new ApiException(UserErrorCode.USER_PASSWORD_NOT_MATCH);
		}
	}




	

	@Override
	public void deleteUserById(int userId) {
		
		// 여기에 아이디 없는거같은거 에러처리 추가하기
		
		userRepository.deleteById(userId);
	}












	


}
