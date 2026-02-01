package com.project.zipmin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.zipmin.entity.FridgeStorage;

public interface FridgeStorageRepository extends JpaRepository<FridgeStorage, Integer> {
	
	// 상세 조회
	FridgeStorage findById(int id);

	// 목록 조회
	List<FridgeStorage> findAllByUserId(int userId);
	
	
}
