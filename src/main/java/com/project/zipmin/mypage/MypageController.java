package com.project.zipmin.mypage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MypageController {
	
	@GetMapping("/mypage/myComment.do")
	public String myComment() {
		return "mypage/myComment";
	}
	
	@GetMapping("/mypage/myReview.do")
	public String myReview() {
		return "mypage/myReview";
	}
	
	@GetMapping("/mypage/myRecipe.do")
	public String myRecipe() {
		return "mypage/myRecipe";
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
	
	

}
