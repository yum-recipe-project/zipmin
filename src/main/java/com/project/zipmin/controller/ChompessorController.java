package com.project.zipmin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChompessorController {
	
	@GetMapping("/chompessor/viewForum.do")
	public String viewForum() {
		return "chompessor/viewForum";
	}
	
	@GetMapping("/chompessor/viewForum/comment.do")
	public String viewForumComment() {
		return "chompessor/forumCommentContent";
	}
	
	@GetMapping("/chompessor/listForum.do")
	public String listForum() {
		return "chompessor/listForum";
	}
	
}
