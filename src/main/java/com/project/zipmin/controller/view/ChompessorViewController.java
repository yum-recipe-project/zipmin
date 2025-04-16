package com.project.zipmin.controller.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.project.zipmin.service.ChompService;
import com.project.zipmin.service.CommentService;

@Controller
public class ChompessorViewController {
	
	@Autowired
	ChompService chompService;
	@Autowired
	CommentService commentService;
	

	@GetMapping("/chompessor/listChomp.do")
	public String listForum() {
		return "chompessor/listChomp";
	}
	
	@GetMapping("/chompessor/viewVote.do")
	public String viewVote() {
		System.err.println(chompService.getVoteById(1));
		return "chompessor/viewVote";
	}
	
	@GetMapping("/chompessor/viewVote/comment.do")
	public String viewVoteComment() {
		return "chompessor/voteCommentContent";
	}
	
	
	@GetMapping("/chompessor/viewMegazine.do")
	public String viewMegazine() {
		return "chompessor/viewMegazine";
	}
	
	@GetMapping("/chompessor/viewEvent.do")
	public String viewEvent() {
		return "chompessor/viewEvent";
	}

	
}
