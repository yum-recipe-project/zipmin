package com.project.zipmin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.zipmin.mapper.GuideMapper;
import com.project.zipmin.repository.KitchenRepository;
import com.project.zipmin.repository.LikeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {
	
	@Autowired
	private final LikeRepository likeRepository;

	@Override
	public int selectLikeCountByTable(String tablename, int recodenum) {
	    return (int) likeRepository.countByTablenameAndRecodenum(tablename, recodenum);
	}

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

	@Override
	public boolean selectLikeStatusByTable(String id, String tablename, int recodenum) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int deleteLikesByTable(String tablename, int recodenum) {
		// TODO Auto-generated method stub
		return 0;
	}

	
}
