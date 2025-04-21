package com.project.zipmin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.project.zipmin.service.CommentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
public class CommentController {
	
	@Autowired
	CommentService commentService;
	
	// 댓글 조회
	
	
	
	
	
	
	
	
	
	// 댓글 삭제
	@DeleteMapping("/comments/{commId}")
	@Operation(
	    summary = "댓글 삭제",
	    description = "댓글을 삭제합니다."
	)
	@Parameters({
	    @Parameter(name = "commId", description = "삭제할 댓글의 ID", required = true)
	})
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "204", description = "댓글 삭제 성공"),
	    @ApiResponse(responseCode = "400", description = "잘못된 요청"),
	    @ApiResponse(responseCode = "403", description = "권한 없음"),
	    @ApiResponse(responseCode = "404", description = "댓글을 찾을 수 없음"),
	    @ApiResponse(responseCode = "409", description = "중복된 요청 또는 무결성 충돌"),
	    @ApiResponse(responseCode = "500", description = "서버 오류")
	})
	public ResponseEntity<Object> deleteComment(@PathVariable int commId) {
		System.err.println("실행은 됨");
		commentService.deleteCommentById(commId);
	    return ResponseEntity.noContent().build(); // 204 No Content
	}

}
