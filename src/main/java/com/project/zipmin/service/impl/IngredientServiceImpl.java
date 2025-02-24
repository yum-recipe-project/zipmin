package com.project.zipmin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.zipmin.dto.IngredientDTO;
import com.project.zipmin.service.IIngredientService;

@Service
public class IngredientServiceImpl implements IIngredientService {

	@Override
	public List<IngredientDTO> selectIngredientListByMemberId(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IngredientDTO> selectIngredientListByRecipeIdx(int recipeIdx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insertIngredient(IngredientDTO ingredientDTO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateIngredient(IngredientDTO ingredientDTO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteIngredient(int ingrIdx) {
		// TODO Auto-generated method stub
		return 0;
	}

//	@Override
//	public List<IngredientDTO> selectIngredientsByRecipeIdx(Integer recipeIdx) {
//		
//		List<IngredientDTO> ingredientList = new ArrayList<IngredientDTO>();
//		
//		// 더미데이터 1
//		IngredientDTO ingredientDTO1 = new IngredientDTO();
//		ingredientDTO1.setIngredient_idx(1);
//		ingredientDTO1.setName("마늘");
//		ingredientDTO1.setAmount(5);
//		ingredientDTO1.setUnit("쪽");
//		ingredientDTO1.setNote("슬라이스");
//		ingredientDTO1.setRecipe_ref(1);
//		ingredientList.add(ingredientDTO1);
//		
//		// 더미데이터 2
//		IngredientDTO ingredientDTO2 = new IngredientDTO();
//		ingredientDTO2.setIngredient_idx(2);
//		ingredientDTO2.setName("김치");
//		ingredientDTO2.setAmount(2);
//		ingredientDTO2.setUnit("포기");
//		ingredientDTO2.setRecipe_ref(1);
//		ingredientList.add(ingredientDTO2);
//		
//		return ingredientList;
//	}

}
