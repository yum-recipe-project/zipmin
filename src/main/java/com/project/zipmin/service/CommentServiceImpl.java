package com.project.zipmin.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.zipmin.dto.CommentDTO;

@Service
public class CommentServiceImpl implements CommentService {

	@Override
	public List<CommentDTO> selectCommentListByTableSortAsc(String tablename, int recodenum) {
		// NOTE: 테스트용 코드입니다.
		List<CommentDTO> comments = new ArrayList<>();

		String table = "comments";
		int recodenumT = 123;

		comments.add(new CommentDTO(1, 1, new Date(), "첫 번째 댓글", table, recodenumT, 1001));
		comments.add(new CommentDTO(2, 1, new Date(), "첫 번째 댓글의 대댓글", table, recodenumT, 1002));
		comments.add(new CommentDTO(3, 1, new Date(), "첫 번째 댓글의 또 다른 대댓글", table, recodenumT, 1003));
		comments.add(new CommentDTO(4, 4, new Date(), "두 번째 댓글", table, recodenumT, 1004));
		comments.add(new CommentDTO(5, 4, new Date(), "두 번째 댓글의 대댓글", table, recodenumT, 1005));
	    
	    return comments;
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
