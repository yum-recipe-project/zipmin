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
	@GetMapping("/{guideId}")
	public GuideDTO viewGuide(@PathVariable("guideId") int guideId) {
		return null;
	}
	
	
	
	// 새 가이드 등록 (관리자)
	@PostMapping("")
	public int writeGuide() {
		return 0;
	}
	
	
	
	// 특정 가이드 수정 (관리자)
	@PutMapping("/{guideId}")
	public int editGuide(@PathVariable("guideId") int guideId) {
		return 0;
	}
	
	
	
	// 특정 가이드 삭제 (관리자)
	@DeleteMapping("/{guideId}")
	public int deleteGuide(@PathVariable("guideId") int guideId) {
		return 0;
	}
	
	
	
	// 특정 가이드의 댓글 목록 조회
	@GetMapping("/{guideId}/comments")
	public List<CommentDTO> listGuideComment(
			@PathVariable("guideId") int guideId,
			@RequestParam(name = "sort", defaultValue = "new") String sort) {
		return null;
	}
	
	
	
	// 특정 가이드에 댓글 작성
	@PostMapping("/{guideId}/comments")
	public int writeGuideComment(
			@PathVariable("guideId") int guideId) {
		return 0;
	}
	
	
	
	// 특정 가이드의 특정 댓글 수정
	@PutMapping("/{guideId}/comments/{commId}")
	public int editGuideComment(
			@PathVariable("guideId") int guideId,
			@PathVariable("commId") int commId) {
		return 0;
	}
	
	
	
	// 특정 가이드의 특정 댓글 삭제
	@DeleteMapping("/{guideId}/comments/{commId}")
	public int deleteGuideComment(
			@PathVariable("guideId") int guideId,
			@PathVariable("commId") int commId) {
		return 0;
	}
	
	
	
	// 특정 가이드 좋아요 (저장)
	@PostMapping("/{guideId}/likes")
	public int likeGuide(
			@PathVariable("guideId") int guideId) {
		return 0;
	}
	
	
	
	// 특정 가이드의 특정 댓글 좋아요
	@PostMapping("/{guideId}/comments/{commId}/likes")
	public int likeGuideComment(
			@PathVariable("guideId") int guideId,
			@PathVariable("commId") int commId) {
		return 0;
	}
	
	
	
	// 특정 가이드의 특정 댓글 좋아요 개수
	@GetMapping("/{guideId}/comments/{commId}/likes/count")
	public int countLikeGuideComment(
			@PathVariable("guideId") int guideId,
			@PathVariable("commId") int commId) {
		return 0;
	}
	
	
	
	// 특정 가이드의 특정 댓글 좋아요 여부
	@GetMapping("/{guideId}/comments/{commId}/likes/status")
	public boolean checkLikeGuideComment(
			@PathVariable("guideId") int guideId,
			@PathVariable("commId") int commId) {
		return false;
	}
	
	
	
	// 특정 가이드의 특정 댓글 신고
	@PostMapping("/{guideId}/comments/{commId}/likes/reports")
	public int reportGuideComment(
			@PathVariable("guideId") int guideId,
			@PathVariable("commId") int commId) {
		return 0;
	}
	
	
	
	// 특정 가이드의 특정 댓글 신고 개수 (관리자)
	@GetMapping("/{guideId}/comments/{commId}/likes/reports/count")
	public int countReportGuideComment(
			@PathVariable("guideId") int guideId,
			@PathVariable("commId") int commId) {
		return 0;
	}
	
	
	
	// 특정 가이드의 특정 댓글 신고 여부
	@GetMapping("/{guideId}/comments/{commId}/reports/status")
	public boolean checkReportGuideComment(
			@PathVariable("guideId") int guideId,
			@PathVariable("commId") int commId) {
		return false;
	}
	
	
	
	
}
