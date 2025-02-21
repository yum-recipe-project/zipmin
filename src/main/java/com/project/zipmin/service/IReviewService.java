package com.project.zipmin.service;

import org.springframework.stereotype.Service;

@Service
public interface IReviewService {
	
	// 레시피의 리뷰 수
	public Integer selectReviewCountByRecipeIdx(Integer reviewIdx);
}
