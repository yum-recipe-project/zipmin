package com.project.zipmin.service.impl;

import org.springframework.stereotype.Service;

import com.project.zipmin.service.ICommentService;

@Service
public class CommentServiceImpl implements ICommentService {

	@Override
	public Integer selectCommentCountByRecipeIdx(Integer recipeIdx) {
		return 10;
	}

}
