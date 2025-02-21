package com.project.zipmin.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.zipmin.dto.IngredientDTO;

@Service
public interface IIngredientService {
	
	public List<IngredientDTO> selectIngredientsByRecipeIdx(Integer recipeIdx);
}
