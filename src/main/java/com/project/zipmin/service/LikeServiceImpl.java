package com.project.zipmin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.zipmin.repository.LikeRepository;

@Service
public class LikeServiceImpl implements LikeService {
	
	@Autowired
	private LikeRepository likeRepository;

	@Override
	public int selectFollowerCountByMemberId(String id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int selectFollowingCountByMemberId(String id) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	
	// 특정 사용자가 특정 테이블의 특정 레코드에 좋아요 표시 여부 확인
	@Override
	public boolean hasUserLikedRecode(int userId, String tablename, int recodenum) {
		return likeRepository.existsByUserIdAndTablenameAndRecodenum(userId, tablename, recodenum);
	}

	
	// 테이블 이름과 일련번호를 이용해 좋아요 수 조회
	@Override
	public long countLikesByTablenameAndRecodenum(String tablename, int recodenum) {
		return likeRepository.countByTablenameAndRecodenum(tablename, recodenum);
	}
	
	
	@Override
	public int deleteLikesByTable(String tablename, int recodenum) {
		// TODO Auto-generated method stub
		return 0;
	}


	
}
