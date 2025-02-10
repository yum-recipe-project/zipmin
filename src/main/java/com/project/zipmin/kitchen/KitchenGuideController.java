package com.project.zipmin.kitchen;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class KitchenGuideController {
	
	@GetMapping("/kitchen/viewGuide.do")
	public String viewKitchenGuide() {
		return "kitchen/guideView";
	}
}
