package com.project.zipmin.service;

import org.springframework.stereotype.Service;

import com.project.zipmin.dto.ChompVoteDTO;

@Service
public interface ChompVoteService {
	
	// 모든 투표 게시물 조회
	
	public ChompVoteDTO getVoteById(int id);
	
	// 특정 투표 게시물 조회
	public ChompVoteDTO getVoteByChompId(int chompId);
}
