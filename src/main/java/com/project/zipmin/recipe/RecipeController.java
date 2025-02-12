package com.project.zipmin.recipe;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RecipeController {
	
	@GetMapping("/recipe/listRecipe.do")
	public String listRecripe() {
		return "recipe/listRecipe";
	}
	
	@GetMapping("/recipe/viewRecipe.do")
	public String viewRecripe() {
		return "recipe/viewRecipe";
	}
	
	@GetMapping("/recipe/writeRecipe.do")
	public String writeRecripe() {
		return "recipe/writeRecipe";
	}
	
}
