package com.project.zipmin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.project.zipmin.service.CommentService;
import com.project.zipmin.service.UserService;

@RestController
public class ReviewController {
	
	@Autowired
	CommentService commentService;
	
	@Autowired
	UserService userService;
	
	
	
	
	
	
}


