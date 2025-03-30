package com.project.zipmin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.zipmin.dto.ChompDTO;
import com.project.zipmin.dto.CommentDTO;
// import com.project.zipmin.service.ChompService;
import com.project.zipmin.dto.ChompVoteDTO;

@RestController
public class ChompessorController {
	
	// @Autowired
	// ChompService chompService;

	// 쩝쩝박사 목록 조회
	@GetMapping("/chomp")
	public List<ChompDTO> listChomp() {
		// List<ChompDTO> chompList = chompService.getChompList();
		// System.err.println(chompList);
		return null;
	}
	
	
	
	// 특정 투표 조회
	@GetMapping("/votes/{voteId}")
	public ChompVoteDTO viewVote(
			@PathVariable("voteId") int voteId) {
		return null;
	}

	// 새 투표 등록 (관리자)
	@PostMapping("/votes")
	public int writeVote() {
		return 0;
	}

	// 특정 투표 수정 (관리자)
	@PutMapping("/votes/{voteId}")
	public int editVote(
			@PathVariable("voteId") int voteId) {
		return 0;
	}

	// 특정 투표 삭제 (관리자)
	@DeleteMapping("/votes/{voteId}")
	public int deleteVote(
			@PathVariable("voteId") int voteId) {
		return 0;
	}
	
	
	
	// 사용자의 투표 내용을 조회하면서 전체 결과 조회
	@GetMapping("/votes/{voteId}/records")
	public Object viewVoteResult (
			@PathVariable("voteId") int voteId) {
		return null;
	}
	
	// 사용자의 특정 투표 참여
	@PostMapping("/votes/{voteId}/records")
	public int castVote(
			@PathVariable("voteId") int voteIdx) {
		return 0;
	}
	
	// 사용자의 특정 투표 취소
	@DeleteMapping("/votes/{voteId}/records")
	public int cancelVote(
			@PathVariable("voteId") int voteId) {
		return 0;
	}
	
	// 사용자의 특정 투표 여부 확인
	@GetMapping("/votes/{voteId}/status")
	public int checkCastVoteStatus(
			@PathVariable("voteId") int voteId) {
		return 0;
	}
	


	// 특정 투표의 댓글 목록 조회
	@GetMapping("/votes/{voteId}/comments")
	public List<CommentDTO> listVoteComment(
			@PathVariable("voteId") int voteId,
			@RequestParam(name = "sort", defaultValue = "new") String sort) {
		return null;
	}
	
	// 특정 투표에 댓글 작성
	@PostMapping("/votes/{voteId}/comments")
	public int writeVoteComment(
			@PathVariable("voteId") int voteId) {
		return 0;
	}
	
	// 특정 투표의 특정 댓글 수정
	@PutMapping("/votes/{voteId}/comments/{commId}")
	public int editVoteComment(
			@PathVariable("voteId") int voteId,
			@PathVariable("commId") int commId) {
		return 0;
	}
	
	// 특정 투표의 특정 댓글 삭제
	@DeleteMapping("/votes/{voteId}/comments/{commId}")
	public int deleteVoteComment(
			@PathVariable("voteId") int voteId,
			@PathVariable("commId") int commId) {
		return 0;
	}

	// 특정 투표의 특정 댓글 좋아요
	@PostMapping("/votes/{voteId}/comments/{commId}/likes")
	public int likeVoteComment(
			@PathVariable("voteId") int voteId,
			@PathVariable("commId") int commId) {
		return 0;
	}
	
	// 특정 투표의 특정 댓글 좋아요 취소
	@DeleteMapping("/votes/{voteId}/comments/{commId}/likes")
	public int unlikeVoteComment(
			@PathVariable("voteId") int voteId,
			@PathVariable("commId") int commId) {
		return 0;
	}

	// 특정 투표의 특정 댓글 좋아요 개수
	@GetMapping("/votes/{voteId}/comments/{commId}/likes/count")
	public int countLikeVoteComment(
			@PathVariable("voteId") int voteId,
			@PathVariable("commId") int commId) {
		// 초기에 목록 가져올 때 좋아요 개수 한 번에 가져오고
		// 좋아요 혹은 좋아요 취소 했을 때 부분적으로 가져올 가능성도 있음
		return 0;
	}

	// 특정 투표의 특정 댓글 좋아요 여부 확인
	@GetMapping("/votes/{voteId}/comments/{commId}/likes/status")
	public boolean checkLikeVoteComment(
			@PathVariable("voteId") int voteId,
			@PathVariable("commId") int commId) {
		// 이 함수를 사용 안하고 초기에 목록 가져올 때 개수 한 번에 가져올 수도 있음
		// 근데 그렇게 하면 사용자가 좋아요 하거나 취소할 때마다 전체 목록을 다시 가져와야 하므로
		// 이 함수를 부분적으로 사용할수도..
		return false;
	}

	// 특정 투표의 특정 댓글 신고
	@PostMapping("/votes/{voteId}/comments/{commId}/reports")
	public int reportVoteComment(
			@PathVariable("voteId") int voteId,
			@PathVariable("commId") int commId) {
		return 0;
	}

	// 특정 투표의 특정 댓글 신고 개수 (관리자)
	@GetMapping("/votes/{voteId}/comments/{commId}/reports/count")
	public int countReportVoteComment(
			@PathVariable("voteId") int voteId,
			@PathVariable("commId") int commId) {
		return 0;
	}

	// 특정 투표의 특정 댓글 신고 여부
	@GetMapping("/votes/{voteId}/comments/{commId}/reports/status")
	public boolean checkReportVoteComment(
			@PathVariable("voteId") int voteId,
			@PathVariable("commId") int commId) {
		return false;
	}
	
	
	
	// 특정 매거진 조회
	@GetMapping("/megazines/{megazineId}")
	public ChompVoteDTO viewMegazine(
			@PathVariable("megazineId") int megazineId) {
		return null;
	}

	// 새 매거진 등록 (관리자)
	@PostMapping("/megazines")
	public int writeMegazines() {
		return 0;
	}

	// 특정 매거진 수정 (관리자)
	@PutMapping("/megazines/{megazineId}")
	public int editMegazine(
			@PathVariable("megazineId") int megazineId) {
		return 0;
	}

	// 특정 매거진 삭제 (관리자)
	@DeleteMapping("/megazines/{megazineId}")
	public int deleteMegazine(
			@PathVariable("megazineId") int megazineId) {
		return 0;
	}
	
	
	
	// 특정 매거진의 댓글 목록 조회
	@GetMapping("/megazines/{megazineId}/comments")
	public List<CommentDTO> listMegazineComment(
			@PathVariable("megazineId") int megazineId,
			@RequestParam(name = "sort", defaultValue = "new") String sort) {
		return null;
	}
	
	// 특정 매거진에 댓글 작성
	@PostMapping("/megazines/{megazineId}/comments")
	public int writeMegazineComment(
			@PathVariable("megazineId") int megazineId) {
		return 0;
	}
	
	// 특정 매거진의 특정 댓글 수정
	@PutMapping("/megazines/{megazineId}/comments/{commId}")
	public int editmegazineComment(
			@PathVariable("megazineId") int megazineId,
			@PathVariable("commId") int commId) {
		return 0;
	}
	
	// 특정 매거진의 특정 댓글 삭제
	@DeleteMapping("/megazines/{megazineId}/comments/{commId}")
	public int deleteMegazineComment(
			@PathVariable("megazineId") int megazineId,
			@PathVariable("commId") int commId) {
		return 0;
	}
	
	// 특정 매거진의 특정 댓글 좋아요
	@PostMapping("/megazines/{megazineId}/comments/{commId}/likes")
	public int likeMegazineComment(
			@PathVariable("megazineId") int megazineId,
			@PathVariable("commId") int commId) {
		return 0;
	}
	
	// 특정 매거진의 특정 댓글 좋아요 취소
	@DeleteMapping("/megazines/{megazineId}/comments/{commId}/likes")
	public int unlikeMegazineComment(
			@PathVariable("megazineId") int megazineId,
			@PathVariable("commId") int commId) {
		return 0;
	}

	// 특정 매거진의 특정 댓글 좋아요 개수
	@GetMapping("/megazines/{megazineId}/comments/{commId}/likes/count")
	public int countLikeMegazineComment(
			@PathVariable("megazineId") int megazineId,
			@PathVariable("commId") int commId) {
		// 초기에 목록 가져올 때 좋아요 개수 한 번에 가져오고
		// 좋아요 혹은 좋아요 취소 했을 때 부분적으로 가져올 가능성도 있음
		return 0;
	}

	// 특정 매거진의 특정 댓글 좋아요 여부 확인
	@GetMapping("/megazines/{megazineId}/comments/{commId}/likes/status")
	public boolean checkLikeMegazineComment(
			@PathVariable("megazineId") int megazineId,
			@PathVariable("commId") int commId) {
		// 이 함수를 사용 안하고 초기에 목록 가져올 때 개수 한 번에 가져올 수도 있음
		// 근데 그렇게 하면 사용자가 좋아요 하거나 취소할 때마다 전체 목록을 다시 가져와야 하므로
		// 이 함수를 부분적으로 사용할수도..
		return false;
	}
	
	// 특정 매거진의 특정 댓글 신고
	@PostMapping("/megazines/{megazineId}/comments/{commId}/reports")
	public int reportMegazineComment(
			@PathVariable("megazineId") int megazineId,
			@PathVariable("commId") int commId) {
		return 0;
	}

	// 특정 매거진의 특정 댓글 신고 개수 (관리자)
	@GetMapping("/megazines/{megazineId}/comments/{commId}/reports/count")
	public int countReportMegazineComment(
			@PathVariable("megazineId") int megazineId,
			@PathVariable("commId") int commId) {
		return 0;
	}

	// 특정 매거진의 특정 댓글 신고 여부
	@GetMapping("/megazines/{megazineId}/comments/{commId}/reports/status")
	public boolean checkReportMegazineComment(
			@PathVariable("megazineId") int megazineId,
			@PathVariable("commId") int commId) {
		return false;
	}

}
