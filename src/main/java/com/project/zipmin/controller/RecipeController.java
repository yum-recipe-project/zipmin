package com.project.zipmin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
@RequestMapping("/recipe")
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
	
	
	@GetMapping("")
	public List<Map<String, Object>> listRecipe() {
		return null;
	}
	
	@GetMapping("/{recipeIdx}")
	@ResponseBody
	public RecipeDTO viewRecipe(@PathVariable("recipeIdx") Integer recipeIdx) {
		
		// recipeIdx 보내서 레시피 받아오기
		RecipeDTO recipeDTO = recipeDAO.selectRecipe(recipeIdx);
		
		// reviewIdx 보내서 해당하는 category 읽어오기
		CategoryDTO categoryDTO = categoryDAO.selectCategoryByRecipeIdx(recipeIdx);
		recipeDTO.setCategory(categoryDTO);
		
		// member_ref 보내서 해당하는 member 닉네임 가져오기
		MemberDTO memberDTO = memberDAO.selectMemberById(recipeDTO.getMember_ref());
		recipeDTO.setMember(memberDTO);
		
		// recipeIdx랑 로그인 한 user의 로그인 정보 보내서 레시피에 좋아요와 신고 누른 여부 각각 가져와야 함
		Boolean isLike = likeDAO.selectLikeStatusByRecipeIdx("harim", recipeIdx);
		Boolean isReport = reportDAO.selectReportStatusByRecipeIdx("harim", recipeIdx);
		recipeDTO.setIsLike(isLike);
		recipeDTO.setIsReport(isReport);
		// 가져와서 아이콘 처리하기 (아마 JS단에서)
		
		// 재료 recipeIdx 보내서 해당하는 재료 목록 가져오기
		List<IngredientDTO> ingredientList = ingredientDAO.selectIngredientsByRecipeIdx(recipeIdx);
		recipeDTO.setIngredient_list(ingredientList);
		
		// 조리 순서 recipeIdx 보내서 해당하는 조리 과정 index 순서대로 가져오기
		List<StepDTO> stepList = stepDAO.selectStepsByRecipeIdx(recipeIdx);
		recipeDTO.setStep_list(stepList);
		
		// memberidx랑 login user 보내서 팔로우 여부 팔로우 수 등등
		int followerCount = likeDAO.selectLikeCountByChefIdx("harim");
		recipeDTO.setFollower_count(followerCount);
		Boolean isFollow = likeDAO.selectLikeStatusByChefIdx("dayeong", "harim");
		recipeDTO.setIsFollow(isFollow);
		
		// 리뷰수와 댓글 수
		int reviewCount = reviewDAO.selectReviewCountByRecipeIdx(recipeIdx);
		recipeDTO.setReview_count(reviewCount);
		int commentCount = commentDAO.selectCommentCountByRecipeIdx(recipeIdx);
		recipeDTO.setComment_count(commentCount);
		
		return recipeDTO;
	}
	
	
	

}
