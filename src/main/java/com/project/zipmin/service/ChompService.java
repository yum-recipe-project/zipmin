package com.project.zipmin.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.zipmin.dto.ChompDTO;
import com.project.zipmin.dto.ChompEventResponseDTO;
import com.project.zipmin.dto.ChompMegazineResponseDTO;
import com.project.zipmin.dto.ChompVoteDTO;

@Service
public interface ChompService {
	
	// 모든 쩝쩝박사 게시물 조회
	public List<ChompDTO> getChompList();
	
	
	

	// 특정 투표 게시물 조회
	public ChompVoteDTO getVoteById(int id);

	// 매거진 상세 조회
	public ChompMegazineResponseDTO getMegazineById(int id);
	
	// 이벤트 상세 조회
	public ChompEventResponseDTO getEventById(int id);
}
