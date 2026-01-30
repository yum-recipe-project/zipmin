package com.project.zipmin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.zipmin.entity.Fridge;
import com.project.zipmin.entity.UserFridge;

public interface UserFridgeRepository extends JpaRepository<UserFridge, Integer> {
	
	// 상세 조회
	UserFridge findById(int id);

	// 목록 조회
	List<UserFridge> findAllByUserId(int userId);
	
	
}
