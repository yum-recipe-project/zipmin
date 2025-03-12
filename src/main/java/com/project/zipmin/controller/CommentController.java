package com.project.zipmin.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.zipmin.dto.CommentDTO;
import com.project.zipmin.service.CommentService;

//NOTE: 테스트용 컨트롤러입니다.
@RestController
public class CommentController {

 private final CommentService commentService;

 public CommentController(CommentService commentService) {
     this.commentService = commentService;
 }

 // 특정 이벤트의 댓글 목록 조회
 @GetMapping("/event/{eventId}/comments")
 public List<CommentDTO> listEventComment(
         @PathVariable("eventId") int eventId,
         @RequestParam(name = "sort", defaultValue = "asc") String sort) {
     
     return commentService.selectCommentListByTableSortAsc("comments", eventId);
 }
}

