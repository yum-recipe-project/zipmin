package com.project.zipmin.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.zipmin.dto.CommentDTO;

@Service
public interface CommentService {
	
	// 테이블 이름과 일련번호를 이용해 댓글 목록 조회 (오래된순)
	public List<CommentDTO> getCommentListByTablenameAndRecodenumOrderByIdAsc(String tablename, int recodenum);
	
	// 테이블 이름과 일련번호를 이용해 댓글 목록 조회 (최신순)
	public List<CommentDTO> getCommentListByTablenameAndRecodenumOrderByIdDesc(String tablename, int recodenum);
	
	// 테이블 이름과 일련번호를 이용해 댓글 수 조회
	public int getCommentCountByTable(String tablename, int recodenum);
	
	// 사용자 아이디를 이용해 댓글 목록 조회
	public List<CommentDTO> selectCommentListByMemberId(String id);
	
	// 사용자 아이디를 이용해 댓글 수 조회
	public int selectCommentCountByMemberId(String id);
	
	// 댓글 조회
	public CommentDTO selectComment(int commIdx);
	
	// 댓글 작성
	public int insertComment(CommentDTO commentDTO);
	
	// 댓글 수정
	public int updateComment(CommentDTO commentDTO);
	
	// 댓글 삭제
	public int deleteComment(int commIdx);
	
	// 테이블 이름과 일련번호를 이용해 댓글 목록 삭제
	public int deleteCommentListByTable(String tablename, int recodenum);

}
