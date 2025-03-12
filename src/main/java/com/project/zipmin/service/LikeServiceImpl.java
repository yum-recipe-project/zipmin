package com.project.zipmin.service;

import org.springframework.stereotype.Service;

@Service
public class LikeServiceImpl implements LikeService {

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
