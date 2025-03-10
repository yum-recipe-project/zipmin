package com.project.zipmin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.zipmin.dto.RecipeCategoryDTO;
import com.project.zipmin.service.ICategoryService;

@Service
public class CategoryServiceImpl implements ICategoryService {

	@Override
	public List<RecipeCategoryDTO> selectCategoryListByRecipeIdx(int recipeIdx) {
		List<RecipeCategoryDTO> categoryList = new ArrayList<RecipeCategoryDTO>();
		// 더미데이터1
		RecipeCategoryDTO categoryDTO1 = new RecipeCategoryDTO();
		categoryDTO1.setId(1);
		categoryDTO1.setType("종류별");
		categoryDTO1.setTag("밥/죽/떡");
		categoryDTO1.setRecipeId(recipeIdx);
		categoryList.add(categoryDTO1);
		// 더미데이터2
		RecipeCategoryDTO categoryDTO2 = new RecipeCategoryDTO();
		categoryDTO2.setId(2);
		categoryDTO2.setType("상황별");
		categoryDTO2.setTag("일상");
		categoryDTO2.setRecipeId(recipeIdx);
		categoryList.add(categoryDTO2);
		return categoryList;
	}

	@Override
	public int insertCategoty(RecipeCategoryDTO categoryDTO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateCategory(RecipeCategoryDTO categoryDTO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteCategory(int category_idx) {
		// TODO Auto-generated method stub
		return 0;
	}

	
}
