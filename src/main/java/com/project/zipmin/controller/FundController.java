package com.project.zipmin.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/funds")
public class FundController {
	
	
	// 특정 후원 조회 (관리자)
	@GetMapping("/{fundId}")
	public int viewSupport(
			@PathVariable("fundId") String fundId) {
		// 안쓸거같기도?
		return 0;
	}
	
	
	
	// 특정 후원 취소 (관리자)
	@DeleteMapping("/{fundId}")
	public int deleteSupport(
			@PathVariable("fundId") String fundId) {
		return 0;
	}
	
	
	
	
	
	// 특정 사용자가 다른 사용자에게 후원
	@PostMapping("/{funderId}/supports/{fundeeId}")
	public int supportUser(
			@PathVariable("funderId") String funderId,
			@PathVariable("fundeeId") String fundeeId) {
		return 0;
	}
	
	
	
}
