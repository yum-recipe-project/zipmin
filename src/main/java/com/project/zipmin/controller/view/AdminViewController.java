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
	
	@GetMapping("/admin/listGuide.do")
	public String adminListGuide() {
		return "admin/listGuide";
	}
	
	@GetMapping("/admin/listChomp.do")
	public String adminListChomp() {
		return "admin/listChomp";
	}
	
	@GetMapping("/admin/listVote.do")
	public String adminListVote() {
		return "admin/listVote";
	}
	
	@GetMapping("/admin/listMegazine.do")
	public String adminListMegazine() {
		return "admin/listMegazine";
	}
	
	@GetMapping("/admin/writeMegazine.do")
	public String adminWriteMegazine() {
		return "admin/writeMegazine";
	}
	
	@GetMapping("/admin/viewMegazine.do")
	public String adminViewMegazine() {
		return "admin/viewMegazine";
	}
	
	@GetMapping("/admin/editMegazine.do")
	public String adminEditMegazine() {
		return "admin/editMegazine";
	}
	
	@GetMapping("/admin/listEvent.do")
	public String adminListEvent() {
		return "admin/listEvent";
	}
	
	@GetMapping("/admin/listComment.do")
	public String adminListComment() {
		return "admin/listComment";
	}
	
	@GetMapping("/admin/listReview.do")
	public String adminListReview() {
		return "admin/listReview";
	}
	
}
