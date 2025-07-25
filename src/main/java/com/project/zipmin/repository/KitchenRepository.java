package com.project.zipmin.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.zipmin.entity.Guide;

@Repository
public interface KitchenRepository extends JpaRepository<Guide, Integer> {
	Page<Guide> findAll(Pageable pageable);
	Page<Guide> findByCategory(String category, Pageable pageable);
	
	@Query(value = "SELECT g FROM Guide g " +
			"ORDER BY g.id DESC",
		   countQuery = "SELECT COUNT(g) FROM Guide g")
	Page<Guide> findOrderByIdDesc(Pageable pageable);
	
	@Query(value = "SELECT g FROM Guide g " +
			"WHERE g.category = :category " +
			"ORDER BY g.id DESC",
	   countQuery = "SELECT COUNT(g) FROM Guide g")
	Page<Guide> findByCategoryOrderByIdDesc(@Param("category") String category, Pageable pageable);
	
	@Query(value = "SELECT g FROM Guide g " +
		       "ORDER BY g.likecount DESC, g.id ASC",
		   countQuery = "SELECT COUNT(g) FROM Guide g")
	Page<Guide> findOrderByLikecount(Pageable pageable);
	
	@Query(value = "SELECT g FROM Guide g " +
			"WHERE g.category = :category " +
			"ORDER BY g.likecount DESC, g.id ASC",
			countQuery = "SELECT COUNT(g) FROM Guide g")
	Page<Guide> findByCategoryOrderByLikecount(@Param("category") String category, Pageable pageable);
	
	
}
