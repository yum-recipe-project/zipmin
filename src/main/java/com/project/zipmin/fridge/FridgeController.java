package com.project.zipmin.fridge;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FridgeController {
	
	@GetMapping("/fridge/viewFridge.do")
	public String viewFridge() {
		return "fridge/viewFridge";
	}
	
	@GetMapping("/fridge/viewMemo.do")
	public String viewMemo() {
		return "fridge/viewMemo";
	}
	
	
	
}
