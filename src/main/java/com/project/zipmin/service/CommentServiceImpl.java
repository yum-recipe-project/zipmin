package com.project.zipmin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.project.zipmin.api.ApiException;
import com.project.zipmin.api.CommentErrorCode;
import com.project.zipmin.dto.CommentCreateRequestDto;
import com.project.zipmin.dto.CommentReadResponseDto;
import com.project.zipmin.dto.CommentUpdateRequestDto;
import com.project.zipmin.dto.CommentUpdateResponseDto;
import com.project.zipmin.dto.LikeCreateRequestDto;
import com.project.zipmin.entity.Comment;
import com.project.zipmin.mapper.CommentMapper;
import com.project.zipmin.repository.CommentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private LikeService likeService;

	private final CommentMapper commentMapper;
	
	
	
	// 댓글 목록 조회 (오래된순)
	@Override
	public Page<CommentReadResponseDto> getCommentPageByTablenameAndRecodenumOrderByIdAsc(String tablename, int recodenum, Pageable pageable) {
		
		Page<Comment> commentPage = commentRepository.findByTablenameAndRecodenumOrderByIdAsc(tablename, recodenum, pageable);

		List<CommentReadResponseDto> commentDtoList = new ArrayList<CommentReadResponseDto>();
		for (Comment comment : commentPage) {
			CommentReadResponseDto commentDTO = commentMapper.toReadResponseDto(comment);
			commentDTO.setCommId(comment.getComment().getId());
			commentDTO.setUserId(comment.getUser().getId());
			commentDTO.setNickname(comment.getUser().getNickname());
			commentDTO.setLikecount(likeService.countLikesByTablenameAndRecodenum("comments", comment.getId()));
			commentDtoList.add(commentDTO);
		}
	    return new PageImpl<>(commentDtoList, pageable, commentPage.getTotalElements());
	}

	
	
	// 댓글 목록 조회 (최신순)
	@Override
	public Page<CommentReadResponseDto> getCommentPageByTablenameAndRecodenumOrderByIdDesc(String tablename, int recodenum, Pageable pageable) {
		
		Page<Comment> commentPage = commentRepository.findByTablenameAndRecodenumOrderByIdDesc(tablename, recodenum, pageable);
		
		List<CommentReadResponseDto> commentDtoList = new ArrayList<CommentReadResponseDto>();
		for (Comment comment : commentPage) {
			CommentReadResponseDto commentDTO = commentMapper.toReadResponseDto(comment);
			commentDTO.setCommId(comment.getComment().getId());
			commentDTO.setUserId(comment.getUser().getId());
			commentDTO.setNickname(comment.getUser().getNickname());
			commentDTO.setLikecount(likeService.countLikesByTablenameAndRecodenum("comments", comment.getId()));
			commentDtoList.add(commentDTO);
		}
	    return new PageImpl<>(commentDtoList, pageable, commentPage.getTotalElements());
	}

	
	
	// 댓글 목록 조회 (인기순)
	@Override
	public Page<CommentReadResponseDto> getCommentPageByTablenameAndRecodenumOrderByLikecount(String tablename, int recodenum, Pageable pageable) {
		
		Page<Comment> commentPage = commentRepository.findByTablenameAndRecodenumOrderByLikecount(tablename, recodenum, pageable);

		List<CommentReadResponseDto> commentDtoList = new ArrayList<CommentReadResponseDto>();
		for (Comment comment : commentPage) {
			CommentReadResponseDto commentDTO = commentMapper.toReadResponseDto(comment);
			commentDTO.setCommId(comment.getComment().getId());
			commentDTO.setUserId(comment.getUser().getId());
			commentDTO.setNickname(comment.getUser().getNickname());
			commentDTO.setLikecount(likeService.countLikesByTablenameAndRecodenum("comments", comment.getId()));
			commentDtoList.add(commentDTO);
		}
	    return new PageImpl<>(commentDtoList, pageable, commentPage.getTotalElements());
	}

	@Override
	public int countCommentsByUserId(String userId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void createComment(CommentCreateRequestDto commentDto) {
		System.err.println("comment service 실행");
		Comment comment = commentMapper.toEntity(commentDto);
		System.err.println("변환");
		
		// 기본 저장
		comment = commentRepository.save(comment);
		
	    // 부모가 없는 루트 댓글일 경우 셀프 참조
	    if (comment.getComment() == null) {
	    	comment.setComment(comment);
	        commentRepository.save(comment);
	    }
	}

	
	
	
	// 댓글 수정
	@Override
	public CommentUpdateResponseDto updateComment(int id, CommentUpdateRequestDto commentDto) {

		Comment comment = commentRepository.findById(id)
				.orElseThrow(() -> new ApiException(CommentErrorCode.COMMENT_NOT_FOUND));
		
		comment.setContent(commentDto.getContent());
		comment = commentRepository.save(comment);
		
		return commentMapper.toUpdateResponseDto(comment);
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



	@Override
	public void likeComment(LikeCreateRequestDto likeDto) {
		likeService.addLike(likeDto);
	}



	@Override
	public void unlikeComment(int id) {
		likeService.removeLike(id);
	}
	
	
	
	
	
	
	
	

}
