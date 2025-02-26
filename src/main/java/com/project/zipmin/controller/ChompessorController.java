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
import com.project.zipmin.dto.VoteDTO;

@RestController
@RequestMapping("/votes")
public class ChompessorController {
	
	
	// 투표 목록 조회
	@GetMapping("")
	public List<VoteDTO> listVote() {
		return null;
	}
	
	
	
	// 특정 투표 조회
	@GetMapping("/{voteId}")
	public VoteDTO viewVote(
			@PathVariable("voteId") int voteId) {
		return null;
	}
	
	
	
	// 새 투표 등록 (관리자)
	@PostMapping("")
	public int writeVote() {
		return 0;
	}
	
	
	
	// 특정 투표 수정 (관리자)
	@PutMapping("/{voteId}")
	public int editVote(
			@PathVariable("voteId") int voteId) {
		return 0;
	}
	
	
	
	// 특정 투표 삭제 (관리자)
	@DeleteMapping("/{voteId}")
	public int deleteVote(
			@PathVariable("voteId") int voteId) {
		return 0;
	}
	
	
	
	// 특정 투표의 댓글 목록 조회
	@GetMapping("/{voteId}/comments")
	public List<CommentDTO> listVoteComment(
			@PathVariable("guideId") int guideId,
			@RequestParam(name = "sort", defaultValue = "new") String sort) {
		return null;
	}
	
	
	
	// 특정 투표에 댓글 작성
	@PostMapping("/{voteId}/comments")
	public int writeVoteComment(
			@PathVariable("voteId") int voteId) {
		return 0;
	}
	
	
	
	// 특정 투표의 특정 댓글 수정
	@PutMapping("/{voteId}/comments/{commId}")
	public int editVoteComment(
			@PathVariable("voteId") int voteId,
			@PathVariable("commId") int commId) {
		return 0;
	}
	
	
	
	// 특정 투표의 특정 댓글 삭제
	@DeleteMapping("/{voteId}/comments/{commId}")
	public int deleteVoteComment(
			@PathVariable("voteId") int voteId,
			@PathVariable("commId") int commId) {
		return 0;
	}
	
	
	
	// 특정 투표의 특정 댓글 좋아요
	@PostMapping("/{voteId}/comments/{commId}/likes")
	public int likeVoteComment(
			@PathVariable("voteId") int voteId,
			@PathVariable("commId") int commId) {
		return 0;
	}
	
	
	
	// 특정 투표의 특정 댓글 좋아요 개수
	@GetMapping("/{voteId}/comments/{commId}/likes/count")
	public int countLikeVoteComment(
			@PathVariable("voteId") int voteId,
			@PathVariable("commId") int commId) {
		return 0;
	}
	
	
	
	// 특정 투표의 특정 댓글 좋아요 여부
	@GetMapping("/{voteId}/comments/{commId}/likes/status")
	public boolean checkLikeVoteComment(
			@PathVariable("voteId") int voteId,
			@PathVariable("commId") int commId) {
		return false;
	}
	
	
	
	// 특정 투표의 특정 댓글 신고
	@PostMapping("/{voteId}/comments/{commId}/reports")
	public int reportVoteComment(
			@PathVariable("voteId") int voteId,
			@PathVariable("commId") int commId) {
		return 0;
	}
	
	
	
	// 특정 투표의 특정 댓글 신고 개수 (관리자)
	@GetMapping("/{voteId}/comments/{commId}/reports/count")
	public int countReportVoteComment(
			@PathVariable("voteId") int voteId,
			@PathVariable("commId") int commId) {
		return 0;
	}
	
	
	
	// 특정 투표의 특정 댓글 신고 여부
	@GetMapping("/{voteId}/comments/{commId}/reports/status")
	public boolean checkReportVoteComment(
			@PathVariable("voteId") int voteId,
			@PathVariable("commId") int commId) {
		return false;
	}
}
