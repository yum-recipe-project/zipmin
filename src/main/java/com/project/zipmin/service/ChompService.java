package com.project.zipmin.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.project.zipmin.dto.ChompReadResponseDto;
import com.project.zipmin.dto.EventReadResponseDto;
import com.project.zipmin.dto.MegazineReadResponseDto;
import com.project.zipmin.dto.VoteReadResponseDto;

@Service
public interface ChompService {
	
	// 쩝쩝박사의 게시물 목록을 조회하는 함수
	public Page<ChompReadResponseDto> getChompList(String category, Pageable pageable);
	
	
	
	// 투표 상세 조회
	public VoteReadResponseDto getVoteById(int id);
	
	// 매거진 상세 조회
	public MegazineReadResponseDto getMegazineById(int id);
	
	// 이벤트 상세 조회
	public EventReadResponseDto getEventById(int id);
}
