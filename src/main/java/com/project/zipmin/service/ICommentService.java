package com.project.zipmin.service;

import org.springframework.stereotype.Service;

@Service
public interface ICommentService {
	
	// 레시피의 댓글 수
	public Integer selectCommentCountByRecipeIdx(Integer recipeIdx);
}
