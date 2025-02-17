package com.project.zipmin.mypage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MypageController {
	
	@GetMapping("/mypage/comment.do")
	public String comment() {
		return "mypage/comment";
	}
	
	@GetMapping("/mypage/review.do")
	public String review() {
		return "mypage/review";
	}
	
	@GetMapping("/mypage/myRecipe.do")
	public String myRecipe() {
		return "mypage/recipe";
	}
	
	@GetMapping("/mypage/savedRecipe.do")
	public String savedRecipe() {
		return "mypage/savedRecipe";
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

	
	

}
