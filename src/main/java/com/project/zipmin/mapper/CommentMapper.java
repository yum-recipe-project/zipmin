package com.project.zipmin.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.project.zipmin.dto.CommentReadResponseDto;
import com.project.zipmin.dto.CommentRequestDto;
import com.project.zipmin.dto.CommentResponseDTO;
import com.project.zipmin.entity.Comment;

@Mapper(componentModel = "spring")
public interface CommentMapper {
	CommentResponseDTO commentToCommentResponseDTO(Comment comment);
	Comment commentResponseDTOToComment(CommentResponseDTO commentDTO);
	List<CommentResponseDTO> commentListToCommentResponseDTOList(List<Comment> commentList);
	
	CommentRequestDto commentToCommentRequestDTO(Comment comment);
	Comment commentRequestDTOToComment(CommentRequestDto commentDTO);
	List<CommentRequestDto> commentListToCommentRequestDTOList(List<Comment> commentList);
	
	
	
	
	
	
	
	
//	Comment toEntity(CommentReadRequestDto commentDto);
//	CommentReadRequestDto toRequestDto(Comment comment);
//	List<CommentReadRequestDto> toRequestDtoList(List<Comment> commentList);
	
	Comment toEntity(CommentReadResponseDto commentDto);
	CommentReadResponseDto toResponseDto(Comment comment);
	List<CommentReadResponseDto> toResponseDtoList(List<Comment> commentList);	
}
