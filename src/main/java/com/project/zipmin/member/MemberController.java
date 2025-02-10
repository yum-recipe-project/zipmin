package com.project.zipmin.member;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberController {
	
	
	@GetMapping("/member/login.do")
	public String login() {
		return "member/login";
	}
}
