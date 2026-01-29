package com.project.zipmin.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.zipmin.entity.Chomp;

@Repository
public interface ChompRepository extends JpaRepository<Chomp, Integer> {
	
	// 상세 조회
	Chomp findById(int id);
	
	// 목록 조회
	Page<Chomp> findAll(Pageable pageable);
	Page<Chomp> findAllByCategory(String category, Pageable pageable);
	Page<Chomp> findAllByTitleContainingIgnoreCase(String keyword, Pageable pageable);
	Page<Chomp> findAllByCategoryAndTitleContainingIgnoreCase(String category, String keyword, Pageable pageable);
	
}
