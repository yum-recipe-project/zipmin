package com.project.zipmin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.zipmin.dto.CategoryDTO;
import com.project.zipmin.dto.IngredientDTO;
import com.project.zipmin.dto.MemberDTO;
import com.project.zipmin.dto.RecipeDTO;
import com.project.zipmin.dto.StepDTO;
import com.project.zipmin.service.ICategoryService;
import com.project.zipmin.service.ICommentService;
import com.project.zipmin.service.IIngredientService;
import com.project.zipmin.service.ILikeService;
import com.project.zipmin.service.IMemberService;
import com.project.zipmin.service.IRecipeService;
import com.project.zipmin.service.IReportService;
import com.project.zipmin.service.IReviewService;
import com.project.zipmin.service.IStepService;

@RestController
@RequestMapping("/recipes")
public class RecipeController {
	
	@Autowired
	IRecipeService recipeDAO;
	@Autowired
	ICategoryService categoryDAO;
	@Autowired
	IMemberService memberDAO;
	@Autowired
	ILikeService likeDAO;
	@Autowired
	IReportService reportDAO;
	@Autowired
	IIngredientService ingredientDAO;
	@Autowired
	IStepService stepDAO;
	@Autowired
	IReviewService reviewDAO;
	@Autowired
	ICommentService commentDAO;
	
	
	@GetMapping("/{recipeIdx}")
	public RecipeDTO viewRecipe(@PathVariable("recipeIdx") Integer recipeIdx) {
		
		// 레시피 조회
		RecipeDTO recipeDTO = recipeDAO.selectRecipe(recipeIdx);
		
		// 카테고리 목록 조회
		List<CategoryDTO> categoryList = categoryDAO.selectCategoryListByRecipeIdx(recipeIdx);
		recipeDTO.setCategory_list(categoryList);
		
		// 작성자 조회
		MemberDTO memberDTO = memberDAO.selectMember(recipeDTO.getMember_ref());
		recipeDTO.setMember(memberDTO);
		
		// 재료 목록 조회
		List<IngredientDTO> ingredientList = ingredientDAO.selectIngredientListByRecipeIdx(recipeIdx);
		recipeDTO.setIngredient_list(ingredientList);
		
		// 조리 순서 목록 조회
		List<StepDTO> stepList = stepDAO.selectStepList(recipeIdx);
		recipeDTO.setStep_list(stepList);
		
		// recipeIdx랑 로그인 한 user의 로그인 정보 보내서 레시피에 좋아요와 신고 누른 여부 각각 가져와야 함
		Boolean isLike = likeDAO.selectLikeStatusByTable("dayeong", "recipe", recipeIdx);
		Boolean isReport = reportDAO.selectReportStatusByTable("dayeong", "recipe", recipeIdx);
		recipeDTO.setIsLike(isLike);
		recipeDTO.setIsReport(isReport);
		
		// 팔로워 수와 팔로우 여부 조회
		int followerCount = likeDAO.selectFollowerCountByMemberId(recipeDTO.getMember().getId());
		Boolean isFollow = likeDAO.selectLikeStatusByTable("dayeong", "recipe", recipeDTO.getRecipe_idx());
		recipeDTO.setFollower_count(followerCount);
		recipeDTO.setIsFollow(isFollow);
		
		// 리뷰 수와 댓글 수 조회
		int reviewCount = reviewDAO.selectReviewCountByRecipeIdx(recipeIdx);
		recipeDTO.setReview_count(reviewCount);
		int commentCount = commentDAO.selectCommentCountByTable("recipe", recipeIdx);
		recipeDTO.setComment_count(commentCount);
		
		return recipeDTO;
	}
	
	
	

}
