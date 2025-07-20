package com.project.zipmin.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project.zipmin.entity.Recipe;

public interface RecipeRepository extends JpaRepository<Recipe, Integer> {

	@Query(value = "SELECT r FROM Recipe r " +
		       "LEFT JOIN FETCH r.user u " +
		       "ORDER BY r.likecount DESC, r.id ASC",
		   countQuery = "SELECT COUNT(r) FROM Recipe r")
	Page<Recipe> findOrderByLikecount(Pageable pageable);
	
	@Query(value = "SELECT r FROM Recipe r " +
			"LEFT JOIN FETCH r.user u " +
			"ORDER BY r.score DESC, r.id ASC",
			countQuery = "SELECT COUNT(r) FROM Recipe r")
	Page<Recipe> findOrderByScore(Pageable pageable);

}
