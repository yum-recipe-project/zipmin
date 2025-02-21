package com.project.zipmin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
	
	@GetMapping("member/join/complete.do")
	public String joinComplete() {
		return "member/joinComplete";
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
	
	@GetMapping("/member/verifyPassword.do")
	public String verifyPassword() {
		return "member/verifyPassword";
	}
	
	@GetMapping("/member/changePassword.do")
	public String changePassword() {
		return "member/changePassword";
	}
	
	@GetMapping("/member/userInfo.do")
	public String userInfo() {
		return "member/userInfo";
	}
	
	@GetMapping("/member/viewMemberInfo.do")
	public String viewMemberInfo() {
		return "member/viewMemberInfo";
	}
}
