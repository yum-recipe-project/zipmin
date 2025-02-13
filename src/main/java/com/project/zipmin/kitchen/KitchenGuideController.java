package com.project.zipmin.kitchen;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class KitchenGuideController {
	
	@GetMapping("/kitchen/listGuide.do")
	public String listKitchenGuide() {
		return "kitchen/listGuide";
	}
	
	@GetMapping("/kitchen/viewGuide.do")
	public String viewKitchenGuide() {
		return "kitchen/viewGuide";
	}
	
	@GetMapping("/kitchen/writeGuide.do")
	public String writeKitchenGuide() {
		return "kitchen/writeGuide";
	}
	@GetMapping("/kitchen/editGuide.do")
	public String editKitchenGuide() {
		return "kitchen/editGuide";
	}
}
