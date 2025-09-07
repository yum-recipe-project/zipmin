package com.project.zipmin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.zipmin.entity.Fridge;
import com.project.zipmin.entity.UserFridge;

public interface UserFridgeRepository extends JpaRepository<UserFridge, Integer> {

	List<UserFridge> findAllByUserId(int userId);
	
	
}
