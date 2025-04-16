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
		System.err.println(commentDTOList);
	    return commentDTOList;
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
	public int createComment(CommentRequestDTO commentDTO) {
		System.err.println("commentDTO = " + commentDTO);
		Comment comment = commentMapper.commentRequestDTOToComment(commentDTO);
		System.err.println("comment = " + comment);
		commentRepository.save(comment);
		return 0;
	}

	@Override
	public int updateComment(CommentRequestDTO commentDTO) {
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
