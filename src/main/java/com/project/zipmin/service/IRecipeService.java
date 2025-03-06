package com.project.zipmin.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.zipmin.dto.RecipeDTO;
import com.project.zipmin.dto.RecipeStepDTO;

@Service
public interface IRecipeService {
	
	// 레시피 목록 조회 (추천순)
	public List<RecipeDTO> selectRecipeListSortLike();
	
	// 레시피 목록 조회 (최신순)
	public List<RecipeDTO> selectRecipeListSortNew();
	
	// 레시피 목록 조회 (정확순)
	public List<RecipeDTO> selectRecipeListSortMatch();
	
	// 특정 사용자가 저장한 레시피 목록 조회
	public List<RecipeDTO> selectLikedRecipeListByMemberId(String id);
	
	// 특정 사용자가 팔로우한 사람의 레시피 목록 조회
	public List<RecipeDTO> selectFollowedRecipeListByMemberId(String id);
	
	// 카테고리에 해당하는 레시피 목록 조회
	public List<RecipeDTO> selectRecipeListByCategory();
	
	// 레시피 수 조회
	public int selectRecipeCount();
	
	// 레시피 조회
	public RecipeDTO selectRecipe(int recipeIdx);
	
	// 레시피 작성
	public int insertRecipe(RecipeDTO recipeDTO);
	
	// 레시피 삭제
	public int deleteRecipe(int recipeIdx);
	
	
	
	// 조리 순서 목록 조회
	public List<RecipeStepDTO> selectStepList(int recipeIdx);
	
	// 조리 순서 작성
	public int insertStep(RecipeStepDTO stepDTO);
	
	// 조리 순서 삭제
	public int deleteStep(int stepIdx);
	
}
