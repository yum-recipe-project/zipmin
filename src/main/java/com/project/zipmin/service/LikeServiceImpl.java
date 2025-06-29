package com.project.zipmin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.zipmin.dto.LikeCreateRequestDto;
import com.project.zipmin.entity.Like;
import com.project.zipmin.mapper.CommentMapper;
import com.project.zipmin.mapper.LikeMapper;
import com.project.zipmin.repository.LikeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {
	
	@Autowired
	private LikeRepository likeRepository;
	
	private final LikeMapper likeMapper;
	

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

	
	
	// 좋아요 등록
	@Override
	public void addLike(LikeCreateRequestDto likeDto) {
		Like like = likeMapper.toEntity(likeDto);
		likeRepository.save(like);
	}
	
	
	
	// 좋아요 삭제
	@Override
	public void removeLike(int id) {
		likeRepository.findById(id).ifPresent(likeRepository::delete);
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
	
	
	
	
	
	
	




	
}
