package com.project.zipmin.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.project.zipmin.entity.Fridge;

public interface FridgeRepository extends JpaRepository<Fridge, Integer> {

	Page<Fridge> findAll(Pageable pageable);
	Page<Fridge> findAllByNameContainingIgnoreCase(String keyword, Pageable pageable);
	
	Page<Fridge> findAllByCategory(String category, Pageable pageable);
	Page<Fridge> findAllByCategoryAndNameContainingIgnoreCase(String category, String keyword, Pageable pageable);
}
