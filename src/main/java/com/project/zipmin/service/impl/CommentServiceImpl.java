package com.project.zipmin.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.zipmin.dto.CommentDTO;
import com.project.zipmin.service.ICommentService;

@Service
public class CommentServiceImpl implements ICommentService {

	@Override
	public List<CommentDTO> selectCommentListByTableSortAsc(String tablename, int recodenum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CommentDTO> selectCommentListByTableSortDesc(String tablename, int recodenum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int selectCommentCountByTable(String tablename, int recodenum) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<CommentDTO> selectCommentListByMemberId(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int selectCommentCountByMemberId(String id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public CommentDTO selectComment(int commIdx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insertComment(CommentDTO commentDTO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateComment(CommentDTO commentDTO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteComment(int commIdx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteCommentListByTable(String tablename, int recodenum) {
		// TODO Auto-generated method stub
		return 0;
	}

}
