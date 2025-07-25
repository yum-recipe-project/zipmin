package com.project.zipmin.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.zipmin.repository.LikeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {
	
	@Autowired
	private final LikeRepository likeRepository;

	@Override
	public int selectLikeCountByTable(String tablename, int recodenum) {
	    return likeRepository.countByTablenameAndRecodenum(tablename, recodenum);
	}
	
	@Override
	public Map<Integer, Integer> selectLikeCountsByTable(String tablename, List<Integer> recordNums) {
		// TODO Auto-generated method stub
		return null;
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
