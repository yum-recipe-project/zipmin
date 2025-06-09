package com.project.zipmin.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.project.zipmin.dto.ChompResponseDTO;
import com.project.zipmin.dto.MegazineResponseDTO;
import com.project.zipmin.dto.VoteResponseDTO;

@Service
public interface ChompService {
	
	// 모든 쩝쩝박사 게시물 조회	
	public Page<ChompResponseDTO> getChompList(String category, Pageable pageable);
	
	
	
	// 모든 투표 게시물 조회
	public VoteResponseDTO getVoteById(int id);
	
	// 특정 투표 게시물 조회
	public VoteResponseDTO getVoteByChompId(int chompId);
	
	
	
	
	
	// 매거진 상세 조회
	public MegazineResponseDTO getMegazineById(int id);
}
