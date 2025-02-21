package com.project.zipmin.service;

import org.springframework.stereotype.Service;

import com.project.zipmin.dto.MemberDTO;

@Service
public interface IMemberService {
	
	public MemberDTO selectMemberById(String id);
}
