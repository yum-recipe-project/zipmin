package com.project.zipmin.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.zipmin.dto.RecipeIngredientDTO;

@Service
public interface IIngredientService {
	
	// 아이디를 이용해 재료 목록 조회
	public List<RecipeIngredientDTO> selectIngredientListByMemberId(String id);
	
	// 레시피 일련번호를 이용해 재료 목록 조회
	public List<RecipeIngredientDTO> selectIngredientListByRecipeIdx(int recipeIdx);
	
	// 재료 작성
	public int insertIngredient(RecipeIngredientDTO ingredientDTO);
	
	// 재료 수정
	public int updateIngredient(RecipeIngredientDTO ingredientDTO);
	
	// 재료 삭제
	public int deleteIngredient(int ingrIdx);
	
}
