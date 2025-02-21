package com.project.zipmin.service.impl;

import org.springframework.stereotype.Service;

import com.project.zipmin.service.IReviewService;

@Service
public class ReviewServiceImpl implements IReviewService {

	@Override
	public Integer selectReviewCountByRecipeIdx(Integer reviewIdx) {
		return 11;
	}

}
