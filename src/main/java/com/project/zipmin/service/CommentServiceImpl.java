package com.project.zipmin.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.zipmin.dto.CommentDTO;
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
	public List<CommentDTO> getCommentListByTablenameAndRecodenumOrderByIdAsc(String tablename, int recodenum) {
		List<Comment> commentList = commentRepository.findAllByTablenameAndRecodenumOrderByIdAsc(tablename, recodenum);
		List<CommentDTO> commentDTOList = new ArrayList<CommentDTO>();
		for (Comment comment : commentList) {
			CommentDTO commentDTO = commentMapper.commentToCommentDTO(comment);
			commentDTO.setCommId(comment.getComment().getId());
			commentDTO.setUserId(comment.getUser().getId());
			commentDTO.setNickname(comment.getUser().getNickname());
			commentDTO.setLikecount(likeRepository.countByTablenameAndRecodenum("comments", comment.getId()));
			commentDTOList.add(commentDTO);
		}
	    return commentDTOList;
	}

	@Override
	public List<CommentDTO> getCommentListByTablenameAndRecodenumOrderByIdDesc(String tablename, int recodenum) {
		List<Comment> commentList = commentRepository.findAllByTablenameAndRecodenumOrderByIdDesc(tablename, recodenum);
		List<CommentDTO> commentDTOList = new ArrayList<CommentDTO>();
		for (Comment comment : commentList) {
			CommentDTO commentDTO = commentMapper.commentToCommentDTO(comment);
			commentDTO.setCommId(comment.getComment().getId());
			commentDTO.setUserId(comment.getUser().getId());
			commentDTO.setNickname(comment.getUser().getNickname());
			commentDTO.setLikecount(likeRepository.countByTablenameAndRecodenum("comments", comment.getId()));
			commentDTOList.add(commentDTO);
		}
	    return commentDTOList;
	}

	@Override
	public List<CommentDTO> getCommentListByTablenameAndRecodenumOrderByLikecount(String tablename, int recodenum) {
		List<Comment> commentList = commentRepository.findAllByTablenameAndRecodenumOrderByLikecount(tablename, recodenum);
		List<CommentDTO> commentDTOList = new ArrayList<CommentDTO>();
		for (Comment comment : commentList) {
			CommentDTO commentDTO = commentMapper.commentToCommentDTO(comment);
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
	public int getCommentCountByTable(String tablename, int recodenum) {
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
