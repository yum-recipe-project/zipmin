package com.project.zipmin.controller.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.project.zipmin.dto.ChompDTO;
import com.project.zipmin.service.ChompService;

@Controller
public class ChompessorViewController {
	
	@Autowired
	ChompService chompService;

	@GetMapping("/chompessor/listChomp.do")
	public String listForum() {
		List<ChompDTO> chompList = chompService.getChompList();
		System.err.println(chompList);
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
	
	
	@GetMapping("/chompessor/viewMegazine.do")
	public String viewMegazine() {
		return "chompessor/viewMegazine";
	}
	
	@GetMapping("/chompessor/viewEvent.do")
	public String viewEvent() {
		return "chompessor/viewEvent";
	}

	
}
