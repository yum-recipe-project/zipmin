package com.project.zipmin.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.project.zipmin.dto.CommentRequestDTO;
import com.project.zipmin.dto.CommentResponseDTO;
import com.project.zipmin.entity.Comment;

@Mapper(componentModel = "spring")
public interface CommentMapper {
	CommentResponseDTO commentToCommentResponseDTO(Comment comment);
	Comment commentResponseDTOToComment(CommentResponseDTO commentDTO);
	List<CommentResponseDTO> commentListToCommentResponseDTOList(List<Comment> commentList);
	
	CommentRequestDTO commentToCommentRequestDTO(Comment comment);
	Comment commentRequestDTOToComment(CommentRequestDTO commentDTO);
	List<CommentRequestDTO> commentListToCommentRequestDTOList(List<Comment> commentList);
}
