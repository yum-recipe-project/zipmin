package com.project.zipmin.mypage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MypageController {
	
	
	@GetMapping("/mypage/modifyMember.do")
	public String login() {
		return "mypage/modifyMember";
	}
	
	

}
