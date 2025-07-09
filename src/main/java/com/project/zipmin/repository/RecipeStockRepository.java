package com.project.zipmin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.zipmin.entity.RecipeStock;

public interface RecipeStockRepository extends JpaRepository<RecipeStock, Integer> {

	
}
