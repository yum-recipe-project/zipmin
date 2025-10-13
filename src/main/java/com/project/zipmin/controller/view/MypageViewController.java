package com.project.zipmin.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MypageViewController {
	
	@GetMapping("/mypage/comment.do")
	public String comment() {
		return "mypage/comment";
	}
	@GetMapping("/mypage/comment/content.do")
	public String commentContent() {
		return "mypage/commentContent";
	}
	@GetMapping("/mypage/revenue.do")
	public String myPoint() {
		return "mypage/revenue";
	}
	@GetMapping("/mypage/withdraw.do")
	public String withdraw() {
		return "mypage/withdraw";
	}
	
	@GetMapping("/mypage/review.do")
	public String review() {
		return "mypage/review";
	}
	@GetMapping("/mypage/review/review.do")
	public String reviewContent() {
		return "mypage/reviewContent";
	}
	
	@GetMapping("/mypage/recipe.do")
	public String myRecipe() {
		return "mypage/recipe";
	}
	
	@GetMapping("/mypage/savedRecipe.do")
	public String savedRecipe() {
		return "mypage/savedRecipe";
	}
	
	@GetMapping("/mypage/savedGuide.do")
	public String savedGuide() {
		return "mypage/savedGuide";
	}
	
	@GetMapping("/mypage.do")
	public String mypage() {
		return "mypage/mypage";
	}
	
	@GetMapping("/mypage/profile.do")
	public String profile() {
		return "mypage/profile";
	}
	
	@GetMapping("/mypage/follower.do")
	public String follower() {
		return "mypage/follower";
	}
	
	@GetMapping("/mypage/following.do")
	public String following() {
		return "mypage/following";
	}
	
	@GetMapping("/mypage/appliedClass.do")
	public String appliedClass() {
		return "mypage/appliedClass";
	}
	
	@GetMapping("/mypage/class.do")
	public String myClass() {
		return "mypage/class";
	}
	
	@GetMapping("/mypage/class/application.do")
	public String myClassApplication() {
		return "mypage/application";
	}

	
	

}
