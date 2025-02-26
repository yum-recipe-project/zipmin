package com.project.zipmin.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/supports")
public class SupportController {
	
	
	// 특정 후원 조회 (관리자)
	@GetMapping("/{supportId}")
	public int viewSupport(
			@PathVariable("supportId") String supportId) {
		// 안쓸거같기도?
		return 0;
	}
	
	
	// 특정 후원 취소 (관리자)
	@DeleteMapping("/{supportId}")
	public int deleteSupport(
			@PathVariable("supportId") String supportId) {
		return 0;
	}
}
