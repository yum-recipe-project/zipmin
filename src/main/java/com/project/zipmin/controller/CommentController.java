package com.project.zipmin.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.zipmin.api.ApiResponse;
import com.project.zipmin.api.CommentSuccessCode;
import com.project.zipmin.dto.CommentReadResponseDto;
import com.project.zipmin.service.CommentService;

@RestController
public class CommentController {
	
	@Autowired
	CommentService commentService;
	
	
	// 댓글 목록 조회
	@GetMapping("/comments")
	public ResponseEntity<?> readComment(@RequestParam String tablename, @RequestParam int recodenum, @RequestParam String sort, @RequestParam int page, @RequestParam int size) {
		
		Pageable pageable = PageRequest.of(page, size);
		Page<CommentReadResponseDto> commentPage = null;
		
		if (sort.equals("new")) {
			commentPage = commentService.getCommentPageByTablenameAndRecodenumOrderByIdDesc(tablename, recodenum, pageable);
		}
		else if (sort.equals("old")) {
			commentPage = commentService.getCommentPageByTablenameAndRecodenumOrderByIdAsc(tablename, recodenum, pageable);
		}
		else if (sort.equals("hot")) {
			commentPage = commentService.getCommentPageByTablenameAndRecodenumOrderByLikecount(tablename, recodenum, pageable);
		}
		
		return ResponseEntity.status(CommentSuccessCode.COMMENT_READ_SUCCESS.getStatus())
        		.body(ApiResponse.success(CommentSuccessCode.COMMENT_READ_SUCCESS, commentPage));
	}
	
	
	
	
//	@PostMapping("/comments")
//	public ResponseEntity<?> writeComment(@RequestBody) {
//		
//		
//		commentService.createComment(commentDto);
//		
//		
//		
//		return null;
//	}
	
	
	// 특정 댓글 좋아요
	@GetMapping("/comments/{id}/likes")
	public ResponseEntity<?> likeComment(@PathVariable int id) {
		
		return null;
	}
	
	// 특정 댓글 좋아요 취소
	@PostMapping("/comments/{id}/likes")
	public ResponseEntity<?> unlikeComment(@PathVariable int id) {
		return null;
	}
	
	// 특정 댓글 좋아요 여부 확인
	@GetMapping("/comments/{id}/likes/status")
	public ResponseEntity<?> checkCommentStatus(@PathVariable int id) {
		return null;
	}
	
	
	
	
	
}


