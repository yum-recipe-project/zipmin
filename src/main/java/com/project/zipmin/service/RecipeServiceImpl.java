package com.project.zipmin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.zipmin.dto.RecipeDTO;
import com.project.zipmin.dto.RecipeStepDTO;

@Service
public class RecipeServiceImpl implements RecipeService {

	@Override
	public List<RecipeDTO> selectRecipeListSortLike() {
		return null;
	}

	@Override
	public List<RecipeDTO> selectRecipeListSortNew() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RecipeDTO> selectRecipeListSortMatch() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RecipeDTO> selectLikedRecipeListByMemberId(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RecipeDTO> selectFollowedRecipeListByMemberId(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RecipeDTO> selectRecipeListByCategory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int selectRecipeCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public RecipeDTO selectRecipe(int recipeIdx) {
		RecipeDTO recipeDTO = new RecipeDTO();
		// 더미데이터
		recipeDTO.setTitle("김치볶음밥");
		recipeDTO.setLevel("쉬움");
		recipeDTO.setTime("15분");
		recipeDTO.setSpicy("아주 매움");
		recipeDTO.setUserId("harim");
		recipeDTO.setIntroduce("이 요리는 영국에서 최초로 시작되어 일년에 한바퀴를 돌면서 받는 사람에게 행운을 주었고"
				+ "	지금은 당신에게로 옮겨진 이 요리는 4일 안에 당신 곁을 떠나야 합니다."
				+ "	이 요리를 포함해서 7통을 행운이 필요한 사람에게 보내 주셔야 합니다."
				+ "	복사를 해도 좋습니다. 혹 미신이라 하실지 모르겠지만 사실입니다.");
		recipeDTO.setPortion(2);
		recipeDTO.setTip("이틀 안에 모두 드세요");
		return recipeDTO;
	}

	@Override
	public int insertRecipe(RecipeDTO recipeDTO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteRecipe(int recipeIdx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<RecipeStepDTO> selectStepList(int recipeIdx) {
		List<RecipeStepDTO> stepList = new ArrayList<RecipeStepDTO>();
		// 더미데이터 1
		RecipeStepDTO stepDTO1 = new RecipeStepDTO();
		stepDTO1.setId(1);
		stepDTO1.setContent("1번 설명입니다");
		stepDTO1.setRecipeId(1);
		stepList.add(stepDTO1);
		// 더미데이터 2
		RecipeStepDTO stepDTO2 = new RecipeStepDTO();
		stepDTO2.setId(2);
		stepDTO2.setContent("2번 설명입니다");
		stepDTO2.setRecipeId(1);
		stepList.add(stepDTO2);
		return stepList;
	}

	@Override
	public int insertStep(RecipeStepDTO stepDTO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteStep(int stepIdx) {
		// TODO Auto-generated method stub
		return 0;
	}

}
