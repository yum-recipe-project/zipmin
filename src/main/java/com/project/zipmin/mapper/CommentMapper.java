package com.project.zipmin.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.project.zipmin.dto.CommentDTO;
import com.project.zipmin.entity.Comment;

@Mapper(componentModel = "spring")
public interface CommentMapper {
	CommentDTO commentToCommentDTO(Comment comment);
	Comment commentDTOToComment(CommentDTO commentDTO);
	List<CommentDTO> commentListToCommentDTOList(List<Comment> commentList);
}
