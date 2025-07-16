package com.project.zipmin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.zipmin.entity.RecipeStock;

public interface RecipeStockRepository extends JpaRepository<RecipeStock, Integer> {

	List<RecipeStock> findByRecipeId(int recipeId);
	
}
