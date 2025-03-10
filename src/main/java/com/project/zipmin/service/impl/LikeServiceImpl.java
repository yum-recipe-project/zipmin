package com.project.zipmin.service.impl;

import org.springframework.stereotype.Service;

import com.project.zipmin.service.ILikeService;

@Service
public class LikeServiceImpl implements ILikeService {

	@Override
	public int selectLikeCountByTable(String tablename, int recodenum) {
		// TODO Auto-generated method stub
		return 0;
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
