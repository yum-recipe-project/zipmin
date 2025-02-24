package com.project.zipmin.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.zipmin.dto.MemberDTO;
import com.project.zipmin.service.IMemberService;

@Service
public class MemberServiceImpl implements IMemberService {

	@Override
	public List<MemberDTO> selectMemberList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MemberDTO> selectFollowerList(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MemberDTO> selectFollowingList(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MemberDTO selectMember(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insertMember(MemberDTO memberDTO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateMember(MemberDTO memberDTO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updatePassword(String id, String password) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteMember(String id) {
		// TODO Auto-generated method stub
		return 0;
	}


}
