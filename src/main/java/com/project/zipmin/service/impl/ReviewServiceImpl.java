package com.project.zipmin.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.zipmin.dto.ReviewDTO;
import com.project.zipmin.service.IReviewService;

@Service
public class ReviewServiceImpl implements IReviewService {

	@Override
	public List<ReviewDTO> selectReviewListByRecipeIdxSortAsc(int recipeIdx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ReviewDTO> selectReviewListByRecipeIdxSortDesc(int recipeIdx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ReviewDTO> selectReviewListByRecipeIdxSortLike(int recipeIdx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int selectReviewCountByRecipeIdx(int recipeIdx) {
		return 23;
	}

	@Override
	public List<ReviewDTO> selectReviewListByMemberId(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int selectReviewCountByMemberId(String id) {
		return 34;
	}

	@Override
	public ReviewDTO selectReview(int reviewIdx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insertReview(ReviewDTO reviewDTO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateReview(ReviewDTO reviewDTO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteReview(int reviewIdx) {
		// TODO Auto-generated method stub
		return 0;
	}


}
