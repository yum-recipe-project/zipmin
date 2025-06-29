package com.project.zipmin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.zipmin.dto.ChompReadResponseDto;
import com.project.zipmin.dto.EventReadResponseDto;
import com.project.zipmin.api.ApiResponse;
import com.project.zipmin.api.ChompSuccessCode;
import com.project.zipmin.api.EventSuccessCode;
import com.project.zipmin.api.MegazineSuccessCode;
import com.project.zipmin.dto.MegazineReadResponseDto;
import com.project.zipmin.service.ChompService;
import com.project.zipmin.service.CommentService;

@RestController
public class ChompessorController {
	
	@Autowired
	ChompService chompService;
	@Autowired
	CommentService commentService;

	
	
	// 쩝쩝박사 목록 조회
	@GetMapping("/chomp")
	public ResponseEntity<?> listChomp(@RequestParam String category, @RequestParam int page, @RequestParam int size) {
		
		Pageable pageable = PageRequest.of(page, size);
		Page<ChompReadResponseDto> chompPage = chompService.readChompPage(category, pageable);

		return ResponseEntity.status(ChompSuccessCode.CHOMP_READ_LIST_SUCCESS.getStatus())
				.body(ApiResponse.success(ChompSuccessCode.CHOMP_READ_LIST_SUCCESS, chompPage));
	}
	
	
	
	// 특정 투표 조회
	@GetMapping("/votes/{id}")
	public ResponseEntity<?> viewVote(@PathVariable int id) {
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
	


	
	
	
	// 특정 매거진 조회
	@GetMapping("/megazines/{id}")
	public ResponseEntity<?> readMegazine(@PathVariable int id) {
		MegazineReadResponseDto megazineDto = chompService.readMegazineById(id);
		
		return ResponseEntity.status(MegazineSuccessCode.MEGAZINE_READ_SUCCESS.getStatus())
				.body(ApiResponse.success(MegazineSuccessCode.MEGAZINE_READ_SUCCESS, megazineDto));	
	}
	
	

	// 새 매거진 등록 (관리자)
	@PostMapping("/megazines")
	public ResponseEntity<?> writeMegazines() {
		return null;
	}

	
	
	// 특정 매거진 수정 (관리자)
	@PutMapping("/megazines/{id}")
	public ResponseEntity<?> editMegazine(@PathVariable int id) {
		return null;
	}

	
	
	// 특정 매거진 삭제 (관리자)
	@DeleteMapping("/megazines/{id}")
	public ResponseEntity<?> deleteMegazine(@PathVariable int id) {
		return null;
	}
	
	
	
	
	// 특정 이벤트 조회
	@GetMapping("/events/{id}")
	public ResponseEntity<?> readEvent(@PathVariable int id) {
		EventReadResponseDto eventDto = chompService.readEventById(id);
		
		return ResponseEntity.status(EventSuccessCode.EVENT_READ_SUCCESS.getStatus())
				.body(ApiResponse.success(EventSuccessCode.EVENT_READ_SUCCESS, eventDto));
	}

}
