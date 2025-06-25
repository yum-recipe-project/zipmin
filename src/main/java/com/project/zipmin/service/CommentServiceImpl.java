package com.project.zipmin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.project.zipmin.dto.CommentReadResponseDto;
import com.project.zipmin.dto.CommentRequestDto;
import com.project.zipmin.dto.CommentResponseDTO;
import com.project.zipmin.entity.Comment;
import com.project.zipmin.mapper.CommentMapper;
import com.project.zipmin.repository.CommentRepository;
import com.project.zipmin.repository.LikeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private LikeRepository likeRepository;

	private final CommentMapper commentMapper;
	
	@Override
	public Page<CommentReadResponseDto> getCommentPageByTablenameAndRecodenumOrderByIdAsc(String tablename, int recodenum, Pageable pageable) {
		
		Page<Comment> commentPage = commentRepository.findByTablenameAndRecodenumOrderByIdAsc(tablename, recodenum, pageable);

		List<CommentReadResponseDto> commentDtoList = new ArrayList<CommentReadResponseDto>();
		for (Comment comment : commentPage) {
			CommentReadResponseDto commentDTO = commentMapper.toResponseDto(comment);
			commentDTO.setCommId(comment.getComment().getId());
			commentDTO.setUserId(comment.getUser().getId());
			commentDTO.setNickname(comment.getUser().getNickname());
			commentDTO.setLikecount(likeRepository.countByTablenameAndRecodenum("comments", comment.getId()));
			commentDtoList.add(commentDTO);
		}
	    return new PageImpl<>(commentDtoList, pageable, commentPage.getTotalElements());
	}

	
	
	@Override
	public Page<CommentReadResponseDto> getCommentPageByTablenameAndRecodenumOrderByIdDesc(String tablename, int recodenum, Pageable pageable) {
		
		Page<Comment> commentPage = commentRepository.findByTablenameAndRecodenumOrderByIdDesc(tablename, recodenum, pageable);
		
		List<CommentReadResponseDto> commentDtoList = new ArrayList<CommentReadResponseDto>();
		for (Comment comment : commentPage) {
			CommentReadResponseDto commentDTO = commentMapper.toResponseDto(comment);
			commentDTO.setCommId(comment.getComment().getId());
			commentDTO.setUserId(comment.getUser().getId());
			commentDTO.setNickname(comment.getUser().getNickname());
			commentDTO.setLikecount(likeRepository.countByTablenameAndRecodenum("comments", comment.getId()));
			commentDtoList.add(commentDTO);
		}
	    return new PageImpl<>(commentDtoList, pageable, commentPage.getTotalElements());
	}

	
	
	@Override
	public Page<CommentReadResponseDto> getCommentPageByTablenameAndRecodenumOrderByLikecount(String tablename, int recodenum, Pageable pageable) {
		
		Page<Comment> commentPage = commentRepository.findByTablenameAndRecodenumOrderByLikecount(tablename, recodenum, pageable);

		List<CommentReadResponseDto> commentDtoList = new ArrayList<CommentReadResponseDto>();
		for (Comment comment : commentPage) {
			CommentReadResponseDto commentDTO = commentMapper.toResponseDto(comment);
			commentDTO.setCommId(comment.getComment().getId());
			commentDTO.setUserId(comment.getUser().getId());
			commentDTO.setNickname(comment.getUser().getNickname());
			commentDTO.setLikecount(likeRepository.countByTablenameAndRecodenum("comments", comment.getId()));
			commentDtoList.add(commentDTO);
		}
	    return new PageImpl<>(commentDtoList, pageable, commentPage.getTotalElements());
	}
	
	
	
	
	
	
	

	@Override
	public int countCommentsByTableNameAndRecordNum(String tablename, int recordnum) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<CommentResponseDTO> getCommentListByUserId(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int countCommentsByUserId(String userId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public CommentResponseDTO getCommentById(int commentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createComment(CommentRequestDto commentDto) {
		System.err.println("comment service 실행");
		Comment comment = commentMapper.commentRequestDTOToComment(commentDto);
		System.err.println("변환");
		
		// 기본 저장
		comment = commentRepository.save(comment);
		
	    // 부모가 없는 루트 댓글일 경우 셀프 참조
	    if (comment.getComment() == null) {
	    	comment.setComment(comment);
	        commentRepository.save(comment);
	    }
	}

	@Override
	public int updateComment(CommentRequestDto commentDTO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteCommentById(int commentId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteCommentListByTablenameAndRecodenum(String tablename, int recodenum) {
		// TODO Auto-generated method stub
		return 0;
	}
	


}
