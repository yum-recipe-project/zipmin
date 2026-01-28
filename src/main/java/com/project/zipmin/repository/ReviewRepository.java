package com.project.zipmin.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.project.zipmin.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
	
	// 상세 조회
	Review findById(int id);
	
	// 목록 조회
	Page<Review> findAll(Pageable pageable);
	Page<Review> findAllByTablename(String tablename, Pageable pageable);
	Page<Review> findAllByUserId(int userId, Pageable pageable);
	Page<Review> findAllByTablenameAndRecodenum(String tablename, int recodenum, Pageable pageable);
	Page<Review> findAllByContentContainingIgnoreCase(String keyword, Pageable pageable);
	Page<Review> findAllByTablenameAndContentContainingIgnoreCase(String tablename, String keyword, Pageable pageable);
	
	// 기타
	int countByRecipeId(Integer recipeId);
	
}
