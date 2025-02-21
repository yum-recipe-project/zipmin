package com.project.zipmin.service;

import org.springframework.stereotype.Service;

import com.project.zipmin.dto.RecipeDTO;

@Service
public interface IRecipeService {
	
	public RecipeDTO selectRecipe(Integer recipeIdx);
	
	
	
}
