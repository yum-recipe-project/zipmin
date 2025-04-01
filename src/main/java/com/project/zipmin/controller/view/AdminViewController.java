package com.project.zipmin.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminViewController {
	
	@GetMapping("/admin.do")
	public String adminTest() {
		return "admin/index";
	}
}
