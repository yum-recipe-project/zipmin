package com.project.zipmin.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.project.zipmin.dto.ReviewReadResponseDto;

@Service
public class ReviewService {

	
	
	
	
	
	// 리뷰 목록 조회 (오래된순)
	public Page<ReviewReadResponseDto> readReviewPageOrderByIdAsc() {
		
		return null;
	}

	
	
	// 리뷰 목록 조회 (최신순)
	public Page<ReviewReadResponseDto> readReviewPageOrderByIdDesc() {
		
		return null;
	}

	
	
	// 리뷰 목록 조회 (좋아요순 도움순)
	public Page<ReviewReadResponseDto> readReviewPageOrderByLikecount() {
		return null;
	}

	


}
