package com.project.zipmin.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.project.zipmin.dto.CommentReadResponseDto;
import com.project.zipmin.dto.CommentRequestDto;
import com.project.zipmin.dto.CommentResponseDTO;

@Service
public interface CommentService {
	
	// 테이블 이름과 일련번호를 이용해 댓글 목록 조회 (오래된순)
	public Page<CommentReadResponseDto> getCommentPageByTablenameAndRecodenumOrderByIdAsc(String tablename, int recodenum, Pageable pageable);
	
	// 테이블 이름과 일련번호를 이용해 댓글 목록 조회 (최신순)
	public Page<CommentReadResponseDto> getCommentPageByTablenameAndRecodenumOrderByIdDesc(String tablename, int recodenum, Pageable pageable);
	
	// 테이블 이름과 일련번호를 이용해 댓글 목록 조회 (인기순)
	public Page<CommentReadResponseDto> getCommentPageByTablenameAndRecodenumOrderByLikecount(String tablename, int recodenum, Pageable pageable);
	
	// 테이블 이름과 일련번호를 이용해 댓글 수 조회
	public int countCommentsByTableNameAndRecordNum(String tablename, int recordnum);
	
	// 사용자 아이디를 이용해 댓글 목록 조회
	public List<CommentResponseDTO> getCommentListByUserId(String userId);
	
	// 사용자 아이디를 이용해 댓글 수 조회
	public int countCommentsByUserId(String userId);
	
	// 댓글 조회
	public CommentResponseDTO getCommentById(int commentId);
	
	// 댓글 작성
	public void createComment(CommentRequestDto commentDTO);
	
	// 댓글 수정
	public int updateComment(CommentRequestDto commentDTO);
	
	// 댓글 삭제
	public int deleteCommentById(int commentId);
	
	// 테이블 이름과 일련번호를 이용해 댓글 목록 삭제
	public int deleteCommentListByTablenameAndRecodenum(String tablename, int recodenum);
	
	
	


}
