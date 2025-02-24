package com.project.zipmin.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.zipmin.dto.CategoryDTO;
import com.project.zipmin.service.ICategoryService;

@Service
public class CategoryServiceImpl implements ICategoryService {

	@Override
	public List<CategoryDTO> selectCategoryListByRecipeIdx(int recipeIdx) {
		// TODO Auto-generated method stub
		return null;
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
