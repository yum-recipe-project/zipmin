package com.project.zipmin.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.zipmin.dto.ReviewDTO;

@Service
public interface ReviewService {
	
	// 레시피 일련번호를 이용해 리뷰 목록 조회 (최신순)
	public List<ReviewDTO> selectReviewListByRecipeIdxSortNew(int recipeIdx);
	
	// 레시피 일련번호를 이용해 리뷰 목록 조회 (오래된순)
	public List<ReviewDTO> selectReviewListByRecipeIdxSortOld(int recipeIdx);
	
	// 레시피 일련번호를 이용해 리뷰 목록 조회 (도움순)
	public List<ReviewDTO> selectReviewListByRecipeIdxSortHelp(int recipeIdx);
	
	// 레시피 일련번호를 이용해 레시피의 리뷰 수 조회
	public int selectReviewCountByRecipeIdx(int recipeIdx);
	
	// 사용자 아이디를 이용해 리뷰 목록 조회
	public List<ReviewDTO> selectReviewListByMemberId(String id);
	
	// 사용자 아이디를 이용해 리뷰 수 조회
	public int selectReviewCountByMemberId(String id);
	
	// 리뷰 조회
	public ReviewDTO selectReview(int reviewIdx);
	
	// 리뷰 작성
	public int insertReview(ReviewDTO reviewDTO);
	
	// 리뷰 수정
	public int updateReview(ReviewDTO reviewDTO);
	
	// 리뷰 삭제
	public int deleteReview(int reviewIdx);
	
}
