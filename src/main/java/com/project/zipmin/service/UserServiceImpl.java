package com.project.zipmin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.zipmin.dto.UserDTO;
import com.project.zipmin.entity.User;
import com.project.zipmin.mapper.UserMapper;
import com.project.zipmin.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userDAO;

	@Override
	public List<UserDTO> getUserList() {
		List<User> userList = userDAO.findAll();
		
		return UserMapper.INSTANCE.userListToUserDTOList(userList);
	}
	



}
