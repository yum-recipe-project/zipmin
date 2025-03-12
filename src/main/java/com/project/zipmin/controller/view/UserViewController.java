package com.project.zipmin.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserViewController {
	
	
	@GetMapping("/user/login.do")
	public String login() {
		return "user/login";
	}
	
	@GetMapping("/user/join.do")
	public String join() {
		return "user/join";
	}
	
	@GetMapping("user/join/complete.do")
	public String joinComplete() {
		return "user/joinComplete";
	}
	
	@GetMapping("/user/findAccount.do")
	public String findAccount() {
		return "user/findAccount";
	}
	
	@GetMapping("/user/findAccount/idResult.do")
	public String findAccountId() {
		return "user/findIdResult";
	}
	
	@GetMapping("/user/findAccount/passwordResult.do")
	public String findAccountPassword() {
		return "user/findPasswordResult";
	}
	
	@GetMapping("/user/verifyPassword.do")
	public String verifyPassword() {
		return "user/verifyPassword";
	}
	
	@GetMapping("/user/changePassword.do")
	public String changePassword() {
		return "user/changePassword";
	}
	
	@GetMapping("/user/userInfo.do")
	public String userInfo() {
		return "user/userInfo";
	}
	
	// 제거 할 것
	@GetMapping("/user/viewMemberInfo.do")
	public String viewMemberInfo() {
		return "user/viewMemberInfo";
	}
	
}
