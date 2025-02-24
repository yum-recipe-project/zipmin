package com.project.zipmin.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.zipmin.dto.MemberDTO;

@Service
public interface IMemberService {
	
	// 회원 목록 조회
	public List<MemberDTO> selectMemberList();
	
	// 팔로우 목록 조회
	public List<MemberDTO> selectFollowerList(String id);
	
	// 팔로잉 목록 조회
	public List<MemberDTO> selectFollowingList(String id);
	
	// 아이디를 이용해 특정 회원 조회
	public MemberDTO selectMember(String id);
	
	// 회원가입
	public int insertMember(MemberDTO memberDTO);
	
	// 회원 정보 수정
	public int updateMember(MemberDTO memberDTO);
	
	// 비밀번호 변경
	public int updatePassword(String id, String password);
	
	// 회원 정보 삭제
	public int deleteMember(String id);
}
