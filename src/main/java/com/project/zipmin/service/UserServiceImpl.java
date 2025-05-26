package com.project.zipmin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Service;

import com.project.zipmin.dto.UserDTO;
import com.project.zipmin.dto.UserRequestDto;
import com.project.zipmin.entity.User;
import com.project.zipmin.mapper.ChompMapper;
import com.project.zipmin.mapper.UserMapper;
import com.project.zipmin.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	private final UserMapper userMapper;

	@Override
	public List<UserDTO> getUserList() {
//		List<User> userList = userRepository.findAll();
//		return userMapper.userListToUserDTOList(userList);
		return null;
	}

	@Override
	public User joinUser(UserRequestDto userDto) {
		User user = userMapper.toEntity(userDto);
		user.setPassword(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(userDto.getPassword()));
		user.setRole("ROLE_USER");
		user.setEnable(1);
		return userRepository.save(user);
	}

	@Override
	public boolean isUsernameDuplicated(String username) {
		return userRepository.existsByUsername(username);
	}
	
	


}
