package com.project.zipmin.service.impl;

import org.springframework.stereotype.Service;

import com.project.zipmin.dto.CategoryDTO;
import com.project.zipmin.service.ICategoryService;

@Service
public class CategoryServiceImpl implements ICategoryService {

	@Override
	public CategoryDTO selectCategoryByRecipeIdx(int recipeIdx) {
		
		CategoryDTO categoryDTO = new CategoryDTO();
		
		// 더미데이터
		categoryDTO.setType("밥/죽/떡");
		categoryDTO.setSituation("일상");
		categoryDTO.setIngredient("김치");
		categoryDTO.setWay("볶음");
		categoryDTO.setRecipe_ref(1);
		
		return categoryDTO;
	}
}
