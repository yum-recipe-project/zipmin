package com.project.zipmin.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.zipmin.dto.ChompDTO;

@Service
public interface ChompService {
	
	// 모든 쩝쩝박사 게시물 조회
	public List<ChompDTO> getChompList();

}
