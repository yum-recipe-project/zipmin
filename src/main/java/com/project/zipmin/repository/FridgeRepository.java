package com.project.zipmin.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.project.zipmin.entity.Fridge;

public interface FridgeRepository extends JpaRepository<Fridge, Integer> {
	
	// 상세 조회
	Fridge findById(int id);
	
	// 목록 조회
	List<Fridge> findAllByUserId(int userId);
	List<Fridge> findAllByIdIn(List<Integer> idList);
	Page<Fridge> findAll(Pageable pageable);
	Page<Fridge> findAllByCategory(String category, Pageable pageable);
	Page<Fridge> findAllByNameContainingIgnoreCase(String keyword, Pageable pageable);
	Page<Fridge> findAllByCategoryAndNameContainingIgnoreCase(String category, String keyword, Pageable pageable);
}
