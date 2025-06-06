package com.project.zipmin.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.zipmin.dto.ReviewDTO;

@Service
public class ReviewServiceImpl implements ReviewService {

	@Override
	public List<ReviewDTO> selectReviewListByRecipeIdxSortNew(int recipeIdx) {
		List<ReviewDTO> reviewList = new ArrayList<ReviewDTO>();
		// 더미데이터1
		ReviewDTO reviewDTO1 = new ReviewDTO();
		reviewDTO1.setId(1);
		reviewDTO1.setUserId("dayeong");
		reviewDTO1.setPostdate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		reviewDTO1.setRecipeId(1);
		reviewDTO1.setScore(5);
		reviewDTO1.setContent("최신순으로 정렬");
		reviewList.add(reviewDTO1);
		// 더미데이터2
		ReviewDTO reviewDTO2 = new ReviewDTO();
		reviewDTO2.setId(2);
		reviewDTO2.setUserId("harim");
		reviewDTO2.setPostdate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		reviewDTO2.setRecipeId(1);
		reviewDTO2.setScore(1);
		reviewDTO2.setContent("별로네요");
		reviewList.add(reviewDTO2);
		return reviewList;
	}

	@Override
	public List<ReviewDTO> selectReviewListByRecipeIdxSortOld(int recipeIdx) {
		List<ReviewDTO> reviewList = new ArrayList<ReviewDTO>();
		// 더미데이터1
		ReviewDTO reviewDTO1 = new ReviewDTO();
		reviewDTO1.setId(1);
		reviewDTO1.setUserId("dayeong");
		reviewDTO1.setRecipeId(1);
		reviewDTO1.setScore(5);
		reviewDTO1.setContent("오래된순");
		reviewList.add(reviewDTO1);
		// 더미데이터2
		ReviewDTO reviewDTO2 = new ReviewDTO();
		reviewDTO2.setId(2);
		reviewDTO2.setUserId("harim");
		reviewDTO2.setRecipeId(1);
		reviewDTO2.setScore(1);
		reviewDTO2.setContent("별로네요");
		reviewList.add(reviewDTO2);
		return reviewList;
	}

	@Override
	public List<ReviewDTO> selectReviewListByRecipeIdxSortHelp(int recipeIdx) {
		List<ReviewDTO> reviewList = new ArrayList<ReviewDTO>();
		// 더미데이터1
		ReviewDTO reviewDTO1 = new ReviewDTO();
		reviewDTO1.setId(1);
		reviewDTO1.setUserId("dayeong");
		reviewDTO1.setRecipeId(1);
		reviewDTO1.setScore(5);
		reviewDTO1.setContent("도움순");
		reviewList.add(reviewDTO1);
		// 더미데이터2
		ReviewDTO reviewDTO2 = new ReviewDTO();
		reviewDTO2.setId(2);
		reviewDTO2.setUserId("harim");
		reviewDTO2.setRecipeId(1);
		reviewDTO2.setScore(1);
		reviewDTO2.setContent("별로네요");
		reviewList.add(reviewDTO2);
		return reviewList;
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
