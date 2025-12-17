package com.project.zipmin;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
		
	@RequestMapping("/")
	public String home() {
		return "home";
	}
	
	@GetMapping("/dummy")
	public ResponseEntity<?> dummy() {
		return ResponseEntity.ok("dummy ok");
	}
}
