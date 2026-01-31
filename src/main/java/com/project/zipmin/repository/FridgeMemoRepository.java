package com.project.zipmin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.zipmin.entity.FridgeMemo;

@Repository
public interface FridgeMemoRepository extends JpaRepository<FridgeMemo, Integer> {
	
	// 상세 조회
	FridgeMemo findById(int id);
	
	// 목록 조회
	List<FridgeMemo> findAllByUserId(Integer userId);
	
}
