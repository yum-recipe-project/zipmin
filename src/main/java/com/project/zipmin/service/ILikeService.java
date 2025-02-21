package com.project.zipmin.service;

import org.springframework.stereotype.Service;

@Service
public interface ILikeService {
	
	// 로그인한 유저가 특정 레시피에 좋아요를 눌렀는지 확인
    Boolean selectLikeStatusByRecipeIdx(String id, Integer recipeIdx);

    // 로그인한 유저가 특정 댓글에 좋아요를 눌렀는지 확인
    Boolean selectLikeStatusByCommentIdx(String id, Integer commentIdx);
    
    // 로그인한 유저가 특정 사용자를 팔로우하는지 확인
    Boolean selectLikeStatusByChefIdx(String id, String chefIdx);
    
    // 팔로워
    Integer selectLikeCountByChefIdx(String chefIdx);
    
    // 로그인한 유저의 팔로우 수
    Integer selectLikeCountById(String id);
}
