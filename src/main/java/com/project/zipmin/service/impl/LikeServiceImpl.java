package com.project.zipmin.service.impl;

import org.springframework.stereotype.Service;

import com.project.zipmin.service.ILikeService;

@Service
public class LikeServiceImpl implements ILikeService {

	@Override
	public Boolean selectLikeStatusByRecipeIdx(String id, Integer recipeIdx) {
		return true;
	}

	@Override
	public Boolean selectLikeStatusByCommentIdx(String id, Integer commentIdx) {
		return true;
	}

	@Override
	public Boolean selectLikeStatusByChefIdx(String id, String chefIdx) {
		return true;
	}

	@Override
	public Integer selectLikeCountByChefIdx(String chefIdx) {
		return 34;
	}

	@Override
	public Integer selectLikeCountById(String id) {
		return 12;
	}
	
}
