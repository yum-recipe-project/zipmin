package com.project.zipmin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.zipmin.entity.RecipeStep;

public interface RecipeStepRepository extends JpaRepository<RecipeStep, Integer> {
	
	List<RecipeStep> findAllByRecipeId(int recipeId);

	
}
