package com.project.zipmin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.zipmin.dto.RecipeIngredientDTO;
import com.project.zipmin.service.IIngredientService;

@Service
public class IngredientServiceImpl implements IIngredientService {

	@Override
	public List<RecipeIngredientDTO> selectIngredientListByMemberId(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RecipeIngredientDTO> selectIngredientListByRecipeIdx(int recipeIdx) {
		List<RecipeIngredientDTO> ingredientList = new ArrayList<RecipeIngredientDTO>();
		// 더미데이터 1
		RecipeIngredientDTO ingredientDTO1 = new RecipeIngredientDTO();
		ingredientDTO1.setId(1);
		ingredientDTO1.setName("마늘");
		ingredientDTO1.setAmount(5);
		ingredientDTO1.setUnit("쪽");
		ingredientDTO1.setNote("슬라이스");
		ingredientDTO1.setRecipeId(1);
		ingredientList.add(ingredientDTO1);
		// 더미데이터 2
		RecipeIngredientDTO ingredientDTO2 = new RecipeIngredientDTO();
		ingredientDTO2.setId(2);
		ingredientDTO2.setName("김치");
		ingredientDTO2.setAmount(2);
		ingredientDTO2.setUnit("포기");
		ingredientDTO2.setRecipeId(1);
		ingredientList.add(ingredientDTO2);
		return ingredientList;
	}

	@Override
	public int insertIngredient(RecipeIngredientDTO ingredientDTO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateIngredient(RecipeIngredientDTO ingredientDTO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteIngredient(int ingrIdx) {
		// TODO Auto-generated method stub
		return 0;
	}

}
