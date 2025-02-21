package com.project.zipmin.service;

import org.springframework.stereotype.Service;

import com.project.zipmin.dto.CategoryDTO;

@Service
public interface ICategoryService {
	
	public CategoryDTO selectCategoryByRecipeIdx(int recipeIdx);

}
