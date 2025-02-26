package com.project.zipmin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.zipmin.dto.CategoryDTO;
import com.project.zipmin.service.ICategoryService;

@Service
public class CategoryServiceImpl implements ICategoryService {

	@Override
	public List<CategoryDTO> selectCategoryListByRecipeIdx(int recipeIdx) {
		List<CategoryDTO> categoryList = new ArrayList<CategoryDTO>();
		// 더미데이터1
		CategoryDTO categoryDTO1 = new CategoryDTO();
		categoryDTO1.setId(1);
		categoryDTO1.setType("종류별");
		categoryDTO1.setTag("밥/죽/떡");
		categoryDTO1.setRecipe_id(recipeIdx);
		categoryList.add(categoryDTO1);
		// 더미데이터2
		CategoryDTO categoryDTO2 = new CategoryDTO();
		categoryDTO2.setId(2);
		categoryDTO2.setType("상황별");
		categoryDTO2.setTag("일상");
		categoryDTO2.setRecipe_id(recipeIdx);
		categoryList.add(categoryDTO2);
		return categoryList;
	}

	@Override
	public int insertCategoty(CategoryDTO categoryDTO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateCategory(CategoryDTO categoryDTO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteCategory(int category_idx) {
		// TODO Auto-generated method stub
		return 0;
	}

	
}
