package com.project.zipmin.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.zipmin.dto.UserDTO;

@Service
public interface IUserService {
	
	// 회원 목록 조회
	public List<UserDTO> selectUserList();
	
	// 팔로우 목록 조회
	public List<UserDTO> selectFollowerList(String id);
	
	// 팔로잉 목록 조회
	public List<UserDTO> selectFollowingList(String id);
	
	// 아이디를 이용해 특정 회원 조회
	public UserDTO selectUser(String id);
	
	// 회원가입
	public int insertUser(UserDTO memberDTO);
	
	// 회원 정보 수정
	public int updateUser(UserDTO memberDTO);
	
	// 비밀번호 변경
	public int updatePassword(String id, String password);
	
	// 회원 정보 삭제
	public int deleteUser(String id);
}
