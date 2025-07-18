package com.project.zipmin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.zipmin.entity.Recipe;

public interface RecipeRepository extends JpaRepository<Recipe, Integer> {

	
}
