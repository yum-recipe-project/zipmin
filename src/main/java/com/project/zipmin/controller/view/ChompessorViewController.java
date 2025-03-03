package com.project.zipmin.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChompessorViewController {
	
	
	@GetMapping("/chompessor/listChomp.do")
	public String listForum() {
		return "chompessor/listChomp";
	}
	
	@GetMapping("/chompessor/viewVote.do")
	public String viewVote() {
		return "chompessor/viewVote";
	}
	
	@GetMapping("/chompessor/viewVote/comment.do")
	public String viewVoteComment() {
		return "chompessor/voteCommentContent";
	}

	
}
