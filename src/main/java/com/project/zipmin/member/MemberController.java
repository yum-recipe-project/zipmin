package com.project.zipmin.member;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberController {
	
	
	@GetMapping("/member/login.do")
	public String login() {
		return "member/login";
	}
	
	@GetMapping("/member/join.do")
	public String join() {
		return "member/join";
	}
	
	@GetMapping("/member/findAccount.do")
	public String findAccount() {
		return "member/findAccount";
	}
	
	@GetMapping("/member/findAccount/idResult.do")
	public String findAccountId() {
		return "member/findIdResult";
	}
	
	@GetMapping("/member/findAccount/passwordResult.do")
	public String findAccountPassword() {
		return "member/findPasswordResult";
	}
}
