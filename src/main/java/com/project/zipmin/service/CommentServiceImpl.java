package com.project.zipmin.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.zipmin.dto.CommentRequestDTO;
import com.project.zipmin.dto.CommentResponseDTO;
import com.project.zipmin.dto.LikeDTO;
import com.project.zipmin.dto.UserDTO;
import com.project.zipmin.entity.Comment;
import com.project.zipmin.entity.Like;
import com.project.zipmin.exception.CommentNotFoundException;
import com.project.zipmin.mapper.CommentMapper;
import com.project.zipmin.mapper.UserMapper;
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
	private final UserMapper userMapper;

	@Override
	public List<CommentResponseDTO> getCommentListByTablenameAndRecodenumOrderByIdAsc(String tablename, int recodenum) {
		List<Comment> commentList = commentRepository.findAllByTablenameAndRecodenumOrderByIdAsc(tablename, recodenum);
		List<CommentResponseDTO> commentDTOList = new ArrayList<CommentResponseDTO>();
		for (Comment comment : commentList) {
			CommentResponseDTO commentDTO = commentMapper.commentToCommentResponseDTO(comment);
			commentDTO.setCommId(comment.getComment().getId());
			commentDTO.setUserId(comment.getUser().getId());
			commentDTO.setNickname(comment.getUser().getNickname());
			commentDTO.setLikecount(likeRepository.countByTablenameAndRecodenum("comments", comment.getId()));
			commentDTOList.add(commentDTO);
		}
	    return commentDTOList;
	}

	@Override
	public List<CommentResponseDTO> getCommentListByTablenameAndRecodenumOrderByIdDesc(String tablename, int recodenum) {
		List<Comment> commentList = commentRepository.findAllByTablenameAndRecodenumOrderByIdDesc(tablename, recodenum);
		List<CommentResponseDTO> commentDTOList = new ArrayList<CommentResponseDTO>();
		for (Comment comment : commentList) {
			CommentResponseDTO commentDTO = commentMapper.commentToCommentResponseDTO(comment);
			commentDTO.setCommId(comment.getComment().getId());
			commentDTO.setUserId(comment.getUser().getId());
			commentDTO.setNickname(comment.getUser().getNickname());
			commentDTO.setLikecount(likeRepository.countByTablenameAndRecodenum("comments", comment.getId()));
			commentDTOList.add(commentDTO);
		}
	    return commentDTOList;
	}

	@Override
	public List<CommentResponseDTO> getCommentListByTablenameAndRecodenumOrderByLikecount(String tablename, int recodenum) {
		List<Comment> commentList = commentRepository.findAllByTablenameAndRecodenumOrderByLikecount(tablename, recodenum);
		List<CommentResponseDTO> commentDTOList = new ArrayList<CommentResponseDTO>();
		for (Comment comment : commentList) {
			CommentResponseDTO commentDTO = commentMapper.commentToCommentResponseDTO(comment);
			commentDTO.setCommId(comment.getComment().getId());
			commentDTO.setUserId(comment.getUser().getId());
			commentDTO.setNickname(comment.getUser().getNickname());
			commentDTO.setLikecount(likeRepository.countByTablenameAndRecodenum("comments", comment.getId()));
			commentDTOList.add(commentDTO);
		}
	    return commentDTOList;
	}

	@Override
	public int countCommentByTableNameAndRecordnum(String tablename, int recordnum) {
		return commentRepository.countByTablenameAndRecodenum(tablename, recordnum);
	}

	@Override
	public List<CommentResponseDTO> getCommentListByUserId(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int countCommentByUserId(String userId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public CommentResponseDTO getCommentById(int commentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int createComment(CommentRequestDTO commentDTO) {
		Comment comment = commentMapper.commentRequestDTOToComment(commentDTO);
		commentRepository.save(comment);
		return 0;
	}

	@Override
	public int updateComment(CommentRequestDTO commentDTO) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void deleteCommentById(int commentId) {
	    Comment comment = commentRepository.findById(commentId)
	        .orElseThrow(() -> new CommentNotFoundException("댓글이 존재하지 않습니다."));
//	    if (comment.isDeleted()) {
//	        throw new InvalidCommentContentException("이미 삭제된 댓글입니다.");
//	    }
//	    if (comment.getUserId() != userId) {
//	        throw new NoPermissionException("댓글 삭제 권한이 없습니다.");
//	    }
	    commentRepository.delete(comment);
	}



	@Override
	public int deleteCommentListByTablenameAndRecodenum(String tablename, int recodenum) {
		// TODO Auto-generated method stub
		return 0;
	}



}
