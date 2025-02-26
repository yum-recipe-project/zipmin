package com.project.zipmin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.zipmin.dto.CategoryDTO;
import com.project.zipmin.dto.CommentDTO;
import com.project.zipmin.dto.IngredientDTO;
import com.project.zipmin.dto.RecipeDTO;
import com.project.zipmin.dto.ReviewDTO;
import com.project.zipmin.dto.RecipeStepDTO;
import com.project.zipmin.dto.UserDTO;
import com.project.zipmin.service.ICategoryService;
import com.project.zipmin.service.ICommentService;
import com.project.zipmin.service.IIngredientService;
import com.project.zipmin.service.ILikeService;
import com.project.zipmin.service.IRecipeService;
import com.project.zipmin.service.IReportService;
import com.project.zipmin.service.IReviewService;
import com.project.zipmin.service.IUserService;

import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/recipes")
public class RecipeController {
	
	@Autowired
	IRecipeService recipeDAO;
	@Autowired
	ICategoryService categoryDAO;
	@Autowired
	IUserService userDAO;
	@Autowired
	ILikeService likeDAO;
	@Autowired
	IReportService reportDAO;
	@Autowired
	IIngredientService ingredientDAO;
	@Autowired
	IReviewService reviewDAO;
	@Autowired
	ICommentService commentDAO;
	
	
	
	// 레시피 목록 조회
	@GetMapping("")
	public List<RecipeDTO> listRecipe() {
		return null;
	}
	
	
	
	// 특정 레시피 조회
	@GetMapping("/{recipeIdx}")
	public RecipeDTO viewRecipe(@PathVariable("recipeIdx") int recipeIdx) {
		
		// 레시피 조회
		RecipeDTO recipeDTO = recipeDAO.selectRecipe(recipeIdx);
		
		// 카테고리 목록 조회
		List<CategoryDTO> categoryList = categoryDAO.selectCategoryListByRecipeIdx(recipeIdx);
		recipeDTO.setCategory_list(categoryList);
		
		// 작성자 조회
		UserDTO userDTO = userDAO.selectUser(recipeDTO.getUser_id());
		recipeDTO.setMember(userDTO);
		
		// 재료 목록 조회
		List<IngredientDTO> ingredientList = ingredientDAO.selectIngredientListByRecipeIdx(recipeIdx);
		recipeDTO.setIngredient_list(ingredientList);
		
		// 조리 순서 목록 조회
		List<RecipeStepDTO> stepList = recipeDAO.selectStepList(recipeIdx);
		recipeDTO.setStep_list(stepList);
		
		// recipeIdx랑 로그인 한 user의 로그인 정보 보내서 레시피에 좋아요와 신고 누른 여부 각각 가져와야 함
		Boolean isLike = likeDAO.selectLikeStatusByTable("dayeong", "recipe", recipeIdx);
		Boolean isReport = reportDAO.selectReportStatusByTable("dayeong", "recipe", recipeIdx);
		recipeDTO.setIsLike(isLike);
		recipeDTO.setIsReport(isReport);
		
		// 팔로워 수와 팔로우 여부 조회
		int followerCount = likeDAO.selectFollowerCountByMemberId(recipeDTO.getMember().getId());
		Boolean isFollow = likeDAO.selectLikeStatusByTable("dayeong", "recipe", recipeDTO.getId());
		recipeDTO.setFollower_count(followerCount);
		recipeDTO.setIsFollow(isFollow);
		
		// 리뷰 수와 댓글 수 조회
		int reviewCount = reviewDAO.selectReviewCountByRecipeIdx(recipeIdx);
		recipeDTO.setReview_count(reviewCount);
		int commentCount = commentDAO.selectCommentCountByTable("recipe", recipeIdx);
		recipeDTO.setComment_count(commentCount);
		
		return recipeDTO;
	}
	
	
	
	// 새 레시피 등록
	@PostMapping("")
	public int writeRecipe() {
		return 0;
	}
	
	
	
	// 특정 레시피 수정
	@PutMapping("/{recipeIdx}")
	public int editRecipe(@PathVariable("recipeIdx") int recipeIdx) {
		
		// 구현 필요 없을 듯
		
		return 0;
	}
	
	
	
	// 특정 레시피 삭제
	@DeleteMapping("/{recipeIdx}")
	public int deleteRecipe(@PathVariable("recipeIdx") int recipeIdx) {
		return 0;
	}
	
	
	
	// 특정 레시피의 리뷰 목록 조회
	@GetMapping("/{recipeIdx}/reviews")
	public List<ReviewDTO> listRecipeReview(
			@PathVariable("recipeIdx") int recipeIdx,
			@RequestParam(name = "sort", defaultValue = "new") String sort) {
	
		// required 옵션 추가해야 할 수도 있음
		
		// 특정 레시피의 리뷰 조회
		if (sort.equals("new")) {
			return reviewDAO.selectReviewListByRecipeIdxSortNew(1);
		}
		else if (sort.equals("old")) {
			return reviewDAO.selectReviewListByRecipeIdxSortOld(1);
		}
		else {
			return reviewDAO.selectReviewListByRecipeIdxSortHelp(1);
		}
	}
	
	
	
	// 특정 레시피의 특정 리뷰 조회
	@GetMapping("/{recipeIdx}/reviews/{reviewIdx}")
	public ReviewDTO viewRecipeReview(
			@PathVariable("recipeIdx") int recipeIdx,
			@PathVariable("reviewIdx") int reviewIdx) {
		return null;
	}
	
	
	
	// 특정 레시피에 리뷰 작성
	@PostMapping("/{recipeIdx}/reviews")
	public int writeRecipeReview(@PathVariable("recipeIdx") int recipeIdx) {
		return 0;
	}
	
	
	
	// 특정 레시피의 특정 리뷰 수정
	@PutMapping("/{recipeIdx}/reviews/{reviewIdx}")
	public int editRecipeReview(
			@PathVariable("recipeIdx") int recipeIdx,
			@PathVariable("reviewIdx") int reviewIdx) {
		return 0;
	}
	
	
	
	// 특정 레시피의 특정 리뷰 삭제
	@DeleteMapping("/{recipeIdx}/reviews/{reviewIdx}")
	public int deleteRecipeReview(
			@PathVariable("recipeIdx") int recipeIdx,
			@PathVariable("reviewIdx") int reviewIdx) {
		return 0;
	}
	
	
	
	// 특정 레시피의 댓글 목록 조회
	@GetMapping("/{recipeIdx}/comments")
	public List<CommentDTO> listRecipeComment(
			@PathVariable("recipeIdx") int recipeIdx,
			@RequestParam(name = "sort", defaultValue = "new") String sort) {
		
		return null;
	}
	
	
	
	// 특정 레시피의 특정 댓글 조회
	@GetMapping("/{recipeIdx}/comments/{commIdx}")
	public CommentDTO viewRecipeComment(
			@PathVariable("recipeIdx") int recipeIdx,
			@PathVariable("commIdx") int commIdx) {
		return null;
	}
	
	
	
	// 특정 레시피에 댓글 작성
	@PostMapping("/{recipeIdx}/comments")
	public int writeRecipeComment(
			@PathVariable("recipeIdx") int recipeIdx) {
		
		return 0;
	}
	
	
	
	// 특정 레시피의 특정 댓글 수정
	@PutMapping("/{recipeIdx}/comments/{commIdx}")
	public int editRecipeComment(
			@PathVariable("recipeIdx") int recipeIdx,
			@PathVariable("commIdx") int commIdx) {
		return 0;
	}
	
	
	
	// 특정 레시피의 특정 댓글 삭제
	@DeleteMapping("/{recipeIdx}/comments/{commIdx}")
	public int deleteRecipeComment(
			@PathVariable("recipeIdx") int recipeIdx,
			@PathVariable("commIdx") int commIdx) {
		return 0;
	}
	
	
	
	
	
	
	
	
	
	

}
