package com.project.zipmin.chopessor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChompessorController {
	
	@GetMapping("/chompessor/viewForum.do")
	public String viewforum() {
		return "chompessor/viewForum";
	}
	
	@GetMapping("/chompessor/listForum.do")
	public String listforum() {
		return "chompessor/listForum";
	}
	
	
	
	
}
