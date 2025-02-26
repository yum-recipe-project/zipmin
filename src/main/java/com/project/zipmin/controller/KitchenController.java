package com.project.zipmin.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.zipmin.dto.CommentDTO;
import com.project.zipmin.dto.GuideDTO;

@RestController
@RequestMapping("/guides")
public class KitchenController {
	

	// 가이드 목록 조회
	@GetMapping("")
	public List<GuideDTO> listGuide() {
		return null;
	}
	
	
	
	// 특정 가이드 조회
	@GetMapping("/{guideIdx}")
	public GuideDTO viewGuide(@PathVariable("guideIdx") int guideIdx) {
		return null;
	}
	
	
	
	// 새 가이드 등록 (관리자)
	@PostMapping("")
	public int writeGuide() {
		return 0;
	}
	
	
	
	// 특정 가이드 수정 (관리자)
	@PutMapping("/{guideIdx}")
	public int editGuide(@PathVariable("guideIdx") int guideIdx) {
		return 0;
	}
	
	
	
	// 특정 가이드 삭제 (관리자)
	@DeleteMapping("/{guideIdx}")
	public int deleteGuide(@PathVariable("guideIdx") int guideIdx) {
		return 0;
	}
	
	
	
	// 특정 가이드의 댓글 목록 조회
	@GetMapping("/{guideIdx}/comments")
	public List<CommentDTO> listGuideComment(
			@PathVariable("guideIdx") int guideIdx,
			@RequestParam(name = "sort", defaultValue = "new") String sort) {
		return null;
	}
	
	
	
	// 특정 가이드에 댓글 작성
	@PostMapping("/{guideIdx}/comments")
	public int writeGuideComment(
			@PathVariable("guideIdx") int guideIdx) {
		return 0;
	}
	
	
	
	// 특정 가이드의 특정 댓글 수정
	@PutMapping("/{guideIdx}/comments/{commIdx}")
	public int editGuideComment(
			@PathVariable("guideIdx") int guideIdx,
			@PathVariable("commIdx") int commIdx) {
		return 0;
	}
	
	
	
	// 특정 가이드의 특정 댓글 삭제
	@DeleteMapping("/{guideIdx}/comments/{commIdx}")
	public int deleteGuideComment(
			@PathVariable("guideIdx") int guideIdx,
			@PathVariable("commIdx") int commIdx) {
		return 0;
	}
}
