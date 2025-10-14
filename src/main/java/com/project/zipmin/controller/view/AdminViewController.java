package com.project.zipmin.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminViewController {
	
	@GetMapping("/admin/home.do")
	public String adminHome() {
		return "admin/home";
	}
	
	@GetMapping("/admin/login.do")
	public String adminLogin() {
		return "admin/login";
	}
	
	@GetMapping("/admin/listUser.do")
	public String adminListUser() {
		return "admin/listUser";
	}
	
	@GetMapping("/admin/listRecipe.do")
	public String adminListRecipe() {
		return "admin/listRecipe";
	}
	
	@GetMapping("/admin/listGuide.do")
	public String adminListGuide() {
		return "admin/listGuide";
	}
	
	@GetMapping("/admin/listChomp.do")
	public String adminListChomp() {
		return "admin/listChomp";
	}
	
	@GetMapping("/admin/listComment.do")
	public String adminListComment() {
		return "admin/listComment";
	}
	
	@GetMapping("/admin/listReview.do")
	public String adminListReview() {
		return "admin/listReview";
	}
	
	@GetMapping("/admin/listClass.do")
	public String adminListClass() {
		return "admin/listClass";
	}
	
	@GetMapping("/admin/listWithdraw.do")
	public String adminListWithdraw() {
		return "admin/listWithdraw";
	}
	
}
