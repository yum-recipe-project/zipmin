package com.project.zipmin.cooking;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CookingController {
	
	@GetMapping("/cooking/listClass.do")
	public String listClass() {
		return "cooking/listClass";
	}
	
	@GetMapping("/cooking/viewClass.do")
	public String viewClass() {
		return "cooking/viewClass";
	}
}
