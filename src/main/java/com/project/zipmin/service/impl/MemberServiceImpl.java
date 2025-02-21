package com.project.zipmin.service.impl;

import org.springframework.stereotype.Service;

import com.project.zipmin.dto.MemberDTO;
import com.project.zipmin.service.IMemberService;

@Service
public class MemberServiceImpl implements IMemberService {

	@Override
	public MemberDTO selectMemberById(String id) {
		
		MemberDTO memberDTO = new MemberDTO();
		
		// 더미데이터
		memberDTO.setName("정하림");
		memberDTO.setNickname("뽀야미가 되고 싶은 아잠만");
		
		return memberDTO;
	}

}
