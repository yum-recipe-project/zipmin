package com.project.zipmin.service.impl;

import org.springframework.stereotype.Service;

import com.project.zipmin.dto.RecipeDTO;
import com.project.zipmin.service.IRecipeService;

@Service
public class RecipeServiceImpl implements IRecipeService {
	
	@Override
	public RecipeDTO selectRecipe(Integer recipeIdx) {
		
		RecipeDTO recipeDTO = new RecipeDTO();
		
		// 더미데이터
		recipeDTO.setTitle("김치볶음밥");
		recipeDTO.setLevel("쉬움");
		recipeDTO.setTime("15분");
		recipeDTO.setSpicy("아주 매움");
		recipeDTO.setMember_ref("harim");
		recipeDTO.setIntroduce("이 요리는 영국에서 최초로 시작되어 일년에 한바퀴를 돌면서 받는 사람에게 행운을 주었고"
				+ "	지금은 당신에게로 옮겨진 이 요리는 4일 안에 당신 곁을 떠나야 합니다."
				+ "	이 요리를 포함해서 7통을 행운이 필요한 사람에게 보내 주셔야 합니다."
				+ "	복사를 해도 좋습니다. 혹 미신이라 하실지 모르겠지만 사실입니다.");
		recipeDTO.setServing(2);
		recipeDTO.setTip("이틀 안에 모두 드세요");
		
		return recipeDTO;
	}
}
