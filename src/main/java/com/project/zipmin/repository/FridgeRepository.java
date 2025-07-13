package com.project.zipmin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.zipmin.entity.Fridge;

public interface FridgeRepository extends JpaRepository<Fridge, Integer> {

	List<Fridge> findAllByUserId(int userId);
}
