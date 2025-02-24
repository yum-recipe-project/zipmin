package com.project.zipmin.service;

import org.springframework.stereotype.Service;

@Service
public interface ILikeService {
	
	// 테이블 이름과 일련번호를 이용해 좋아요 수 조회
	public int selectLikeCountByTable(String tablename, int recodenum);
	
	// 팔로워 수 조회
	public int selectFollowerCountByMemberId(String id);
	
	// 팔로잉 수 조회
	public int selectFollowingCountByMemberId(String id);
	
	// 로그인한 유저가 특정 테이블의 레코드에 좋아요를 눌렀는지 확인
	public boolean selectLikeStatusByTable(String id, String tablename, int recodenum);
    
    // 테이블 이름과 일련번호를 이용해 좋아요 목록 삭제
    public int deleteLikesByTable(String tablename, int recodenum);
    
}
