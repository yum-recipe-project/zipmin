package com.project.zipmin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.zipmin.api.ApiException;
import com.project.zipmin.dto.CustomUserDetails;
import com.project.zipmin.dto.FindUsernameRequestDto;
import com.project.zipmin.dto.UserDto;
import com.project.zipmin.dto.UserJoinDto;
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
	public User joinUser(UserJoinDto userJoinDto) {
		
		User user = userMapper.toEntity(userJoinDto);
		user.setPassword(passwordEncoder.encode(userJoinDto.getPassword()));
		user.setRole(Role.ROLE_USER);
		
		Boolean exists = userRepository.existsByUsername(userJoinDto.getUsername());
		if (exists) {
			throw new ApiException("USERNAME_DUPLICATED", "중복된 아이디입니다.");
		}
		
		return userRepository.save(user);
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
	
	


}
