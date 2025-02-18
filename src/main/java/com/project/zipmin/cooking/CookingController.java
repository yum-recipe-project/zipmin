package com.project.zipmin.cooking;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CookingController {
	
	@GetMapping("/cooking/listClass.do")
	public String listClass() {
		return "cooking/listClass";
	}
	
	@GetMapping("/cooking/listClass/class.do")
	public String listClassContent() {
		return "cooking/classContent";
	}
	
	@GetMapping("/cooking/viewClass.do")
	public String viewClass() {
		return "cooking/viewClass";
	}
	
	@GetMapping("/cooking/openClass.do")
	public String openClass() {
		return "cooking/openClass";
	}
}
