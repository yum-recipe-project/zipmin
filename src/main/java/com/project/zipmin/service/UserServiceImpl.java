package com.project.zipmin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.zipmin.dto.UserDTO;
import com.project.zipmin.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userDAO;

	@Override
	public List<UserDTO> getUserList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserDTO> getFollowerList(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserDTO> getFollowingList(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserDTO getUserById(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserDTO registerUser(UserDTO userDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserDTO updateUserAccount(UserDTO userDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean changePassword(String userId, String newPassword) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteUserById(String userId) {
		// TODO Auto-generated method stub
		return false;
	}

}
