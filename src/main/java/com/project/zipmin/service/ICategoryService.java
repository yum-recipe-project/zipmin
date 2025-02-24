package com.project.zipmin.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.zipmin.dto.CategoryDTO;

@Service
public interface ICategoryService {
	
	// 레시피 일련번호를 이용해 레시피에 해당하는 카테고리 목록 조회
	public List<CategoryDTO> selectCategoryListByRecipeIdx(int recipeIdx);
	
	// 카테고리 작성
	public int insertCategoty(CategoryDTO categoryDTO);
	
	// 카테고리 수정
	public int updateCategory(CategoryDTO categoryDTO);
	
	// 카테고리 삭제
	public int deleteCategory(int category_idx);
	
}
