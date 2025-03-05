package com.project.zipmin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.zipmin.service.ILikeService;

@RestController
@RequestMapping("/likes")
public class LikeController {
	
	ILikeService likeDAO;
	
	// 좋아요 수
	@GetMapping("/count")
	public int getLikeCount(
			@RequestParam(name = "tablename") String tablename,
			@RequestParam(name = "recodenum") int recodenum) {
		
		return likeDAO.selectLikeCountByTable(tablename, recodenum);
	}

}
