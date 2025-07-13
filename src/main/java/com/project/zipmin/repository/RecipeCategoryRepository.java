package com.project.zipmin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.zipmin.entity.RecipeCategory;

public interface RecipeCategoryRepository extends JpaRepository<RecipeCategory, Integer> {

	
}
