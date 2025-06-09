package com.project.zipmin.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.project.zipmin.dto.ChompResponseDTO;
import com.project.zipmin.dto.ChompMegazineDTO;
import com.project.zipmin.dto.ChompVoteDTO;

@Service
public interface ChompService {
	
	// 모든 쩝쩝박사 게시물 조회
	public List<ChompResponseDTO> getChompList();
	
	public Page<ChompResponseDTO> getChompListByCategoryAndStatus(String category, String status, Pageable pageable);
	
	
	
	// 모든 투표 게시물 조회
	public ChompVoteDTO getVoteById(int id);
	
	// 특정 투표 게시물 조회
	public ChompVoteDTO getVoteByChompId(int chompId);
	
	
	
	
	
	// 매거진 상세 조회
	public ChompMegazineDTO getMegazineById(int id);
}
