package com.project.zipmin.kitchen;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class KitchenGuideController {
	
	@GetMapping("/kitchen/listGuide.do")
	public String listGuide() {
		return "kitchen/listGuide";
	}
	
	@GetMapping("/kitchen/viewGuide.do")
	public String viewGuide() {
		return "kitchen/viewGuide";
	}
	
	@GetMapping("/kitchen/viewGuide/comment.do")
	public String viewGuideComment() {
		return "kitchen/guideCommentContent";
	}
	
	
	
	@GetMapping("/kitchen/writeGuide.do")
	public String writeGuide() {
		return "kitchen/writeGuide";
	}
	@GetMapping("/kitchen/editGuide.do")
	public String editGuide() {
		return "kitchen/editGuide";
	}
}
