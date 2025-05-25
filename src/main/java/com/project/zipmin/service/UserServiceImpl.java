package com.project.zipmin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
		user.setRole("ROLE_USER");
		user.setEnable(1);
		// 여기에 존재하는 아이디인지 여부 추가해야 할듯
		// https://yessm621.github.io/spring/GeneralAndSocialLogin/
		return userRepository.save(user);
	}

	@Override
	public boolean isUsernameDuplicated(String username) {
		return userRepository.existsByUsername(username);
	}
	
	


}
