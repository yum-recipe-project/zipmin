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
import com.project.zipmin.dto.CommentCreateResponseDto;
import com.project.zipmin.dto.CommentDeleteRequestDto;
import com.project.zipmin.dto.CommentReadResponseDto;
import com.project.zipmin.dto.CommentUpdateRequestDto;
import com.project.zipmin.dto.CommentUpdateResponseDto;
import com.project.zipmin.dto.LikeCreateRequestDto;
import com.project.zipmin.entity.Comment;
import com.project.zipmin.entity.Role;
import com.project.zipmin.mapper.CommentMapper;
import com.project.zipmin.repository.CommentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private UserService userService;
	@Autowired
	private LikeService likeService;

	private final CommentMapper commentMapper;
	
	
	
	// 댓글 목록 조회 (오래된순)
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

	
	
	
	
	
	public int countCommentsByUserId(String userId) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	
	
	
	
	
	
	
	// 댓글 작성
	public CommentCreateResponseDto createComment(CommentCreateRequestDto commentDto) {

		// 입력값 검증
		if (commentDto == null || commentDto.getContent() == null || commentDto.getTablename() == null || commentDto.getRecodenum() == null || commentDto.getUserId() == null) {
			throw new ApiException(CommentErrorCode.COMMENT_INVALID_INPUT);
		}
		
		Comment comment = commentMapper.toEntity(commentDto);
		
	    // 대댓글이면 댓글 참조
	    if (commentDto.getCommId() != null) {
	        Comment parent = commentRepository.findById(commentDto.getCommId())
	            .orElseThrow(() -> new ApiException(CommentErrorCode.COMMENT_NOT_FOUND));
	        comment.setComment(parent);
	    }
	    
		// 댓글 저장
		try {
			comment = commentRepository.save(comment);
			return commentMapper.toCreateResponseDto(comment);
		}
		catch (Exception e) {
			throw new ApiException(CommentErrorCode.COMMENT_CREATE_FAIL);
		}
		
	}

	
	
	
	// 댓글 수정
	public CommentUpdateResponseDto updateComment(CommentUpdateRequestDto commentRequestDto) {
		
		// 입력값 검증
		if (commentRequestDto == null || commentRequestDto.getId() == null || commentRequestDto.getContent() == null || commentRequestDto.getUserId() == null) {
			throw new ApiException(CommentErrorCode.COMMENT_INVALID_INPUT);
		}

		// 댓글 존재 여부 판단
		Comment comment = commentRepository.findById(commentRequestDto.getId())
				.orElseThrow(() -> new ApiException(CommentErrorCode.COMMENT_NOT_FOUND));
		
		
		// 소유자 검증 (관리자면 소유자 검증 무시)
		if (!userService.getUserById(commentRequestDto.getUserId()).getRole().equals(Role.ROLE_ADMIN)) {
			if (comment.getUser().getId() != commentRequestDto.getUserId()) {
				throw new ApiException(CommentErrorCode.COMMENT_FORBIDDEN);
			}
		}
		
		// 필요한 필드만 수정
		comment.setContent(commentRequestDto.getContent());
		
		// 댓글 수정
		try {
			comment = commentRepository.save(comment);
			return commentMapper.toUpdateResponseDto(comment);
		}
		catch (Exception e) {
			throw new ApiException(CommentErrorCode.COMMENT_UPDATE_FAIL);
		}
		
	}
	
	
	
	public void deleteComment(CommentDeleteRequestDto commentDto) {
		
		// 입력값 검증
		if (commentDto == null || commentDto.getId() == null || commentDto.getUserId() == null) {
			throw new ApiException(CommentErrorCode.COMMENT_INVALID_INPUT);
		}
		
		// 댓글 존재 여부 판단
		Comment comment = commentRepository.findById(commentDto.getId())
				.orElseThrow(() -> new ApiException(CommentErrorCode.COMMENT_NOT_FOUND));
		
		// 소유자 검증 (관리자면 소유자 검증 무시)
		if (!userService.getUserById(commentDto.getUserId()).getRole().equals(Role.ROLE_ADMIN)) {
			if (comment.getUser().getId() != commentDto.getUserId()) {
				throw new ApiException(CommentErrorCode.COMMENT_FORBIDDEN);
			}
		}
	    
		// 댓글 삭제
		try {
			commentRepository.deleteById(commentDto.getId());
		}
		catch (Exception e) {
			throw new ApiException(CommentErrorCode.COMMENT_DELETE_FAIL);
		}

	}



	public void likeComment(LikeCreateRequestDto likeDto) {
		likeService.addLike(likeDto);
	}



	public void unlikeComment(int id) {
		likeService.removeLike(id);
	}
	
	
	
	
	
	
	
	

}
