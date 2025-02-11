package com.project.zipmin.mypage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MypageController {
	
	@GetMapping("/mypage/myComment.do")
	public String myComment() {
		return "mypage/myComment";
	}
	
	

}
