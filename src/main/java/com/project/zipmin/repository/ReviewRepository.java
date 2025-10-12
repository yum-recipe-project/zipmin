package com.project.zipmin.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.project.zipmin.entity.Comment;
import com.project.zipmin.entity.Review;


public interface ReviewRepository extends JpaRepository<Review, Integer> {
	
	// 목록 조회
    Page<Review> findAllByRecipeId(Integer recipeId, Pageable pageable);

    // 전체 리뷰 목록 조회
    Page<Review> findAll(Pageable pageable);

    // 특정 키워드 포함 리뷰 조회 (내용 기준, 대소문자 무시)
    Page<Review> findAllByContentContainingIgnoreCase(String keyword, Pageable pageable);

    // 특정 사용자가 작성한 리뷰 조회
    Page<Review> findByUserId(int userId, Pageable pageable);

    // 특정 레시피에 달린 리뷰 개수 조회
    int countByRecipeId(Integer recipeId);
}
