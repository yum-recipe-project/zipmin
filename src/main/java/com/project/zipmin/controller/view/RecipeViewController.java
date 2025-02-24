package com.project.zipmin.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RecipeViewController {
	
	@GetMapping("/recipe/listRecipe.do")
	public String listRecipe() {
		return "recipe/listRecipe";
	}
	
	@GetMapping("/recipe/viewRecipe.do")
	public String viewRecipe() {
		return "recipe/viewRecipe";
	}
	
	@GetMapping("/recipe/viewRecipe/review.do")
	public String viewRecipeReview() {
		return "recipe/recipeReviewContent";
	}
	
	@GetMapping("/recipe/viewRecipe/comment.do")
	public String viewRecipeComment() {
		return "recipe/recipeCommentContent";
	}
	
	@GetMapping("/recipe/writeRecipe.do")
	public String writeRecipe() {
		return "recipe/writeRecipe";
	}
}
