package com.project.zipmin.service;

import org.springframework.stereotype.Service;

import com.project.zipmin.dto.LikeCreateRequestDto;

@Service
public interface LikeService {
	
	// 팔로워 수 조회
	public int selectFollowerCountByMemberId(String id);
	
	// 팔로잉 수 조회
	public int selectFollowingCountByMemberId(String id);
	
	// 좋아요 등록
	public void addLike(LikeCreateRequestDto likeDto);

    // 좋아요 삭제
    void removeLike(int id); 
	
	// 특정 사용자가 특정 테이블의 특정 레코드에 좋아요 표시 여부 확인
	public boolean hasUserLikedRecode(int userId, String tablename, int recodenum);
    
	// 테이블 이름과 일련번호를 이용해 좋아요 수 조회
	public long countLikesByTablenameAndRecodenum(String tablename, int recodenum);
	

}
