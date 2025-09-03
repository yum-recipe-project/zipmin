package com.project.zipmin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.zipmin.entity.RecipeCategory;

public interface RecipeCategoryRepository extends JpaRepository<RecipeCategory, Integer> {

	List<RecipeCategory> findAllByRecipeId(int recipeId);
	
}
