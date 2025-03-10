package com.project.zipmin.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.zipmin.dto.UserDTO;
import com.project.zipmin.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {

	@Override
	public List<UserDTO> selectUserList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserDTO> selectFollowerList(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserDTO> selectFollowingList(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserDTO selectUser(String id) {
		UserDTO userDTO = new UserDTO();
		// 더미데이터
		userDTO.setId("harim");
		userDTO.setName("정하림");
		userDTO.setNickname("뽀야미가 되고 싶은 아잠만");
		userDTO.setPoint(300);
		return userDTO;
	}

	@Override
	public int insertUser(UserDTO memberDTO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateUser(UserDTO memberDTO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updatePassword(String id, String password) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteUser(String id) {
		// TODO Auto-generated method stub
		return 0;
	}

}
