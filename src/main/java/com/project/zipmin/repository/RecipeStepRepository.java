package com.project.zipmin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.zipmin.entity.RecipeStep;

public interface RecipeStepRepository extends JpaRepository<RecipeStep, Integer> {

	
}
