package com.project.zipmin.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.zipmin.entity.Guide;

@Repository
public interface KitchenRepository extends JpaRepository<Guide, Integer> {
	
	// Read
	Guide findById(int id);
	
	// Read List
	Page<Guide> findAll(Pageable pageable);
	Page<Guide> findAllByCategory(String category, Pageable pageable);
	Page<Guide> findAllByTitleContainingIgnoreCase(String keyword, Pageable pageable);
	Page<Guide> findAllByCategoryAndTitleContainingIgnoreCase(String category, String keyword, Pageable pageable);
	Page<Guide> findAllByIdIn(List<Integer> ids, Pageable pageable);
	
}
