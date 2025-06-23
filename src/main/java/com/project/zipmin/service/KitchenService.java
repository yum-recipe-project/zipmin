package com.project.zipmin.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.project.zipmin.dto.GuideDTO;

@Service
public interface KitchenService {
	
	// 모든 키친가이드 게시물 조회	
	public Page<GuideDTO> getGuideList(String category, Pageable pageable);
	
	// 특정 가이드 상세 조회
    GuideDTO getGuideById(int guideId);
    
}
