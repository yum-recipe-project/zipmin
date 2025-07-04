package com.project.zipmin.mapper;

import org.mapstruct.Mapper;

import com.project.zipmin.dto.CommentCreateRequestDto;
import com.project.zipmin.dto.CommentReadResponseDto;
import com.project.zipmin.dto.CommentUpdateRequestDto;
import com.project.zipmin.dto.CommentUpdateResponseDto;
import com.project.zipmin.entity.Comment;

@Mapper(componentModel = "spring")
public interface CommentMapper {
	
	// Create
	Comment toEntity(CommentCreateRequestDto commentDto);
	CommentCreateRequestDto toCreateRequestDto(Comment comment);
	
	// Read
	Comment toEntity(CommentReadResponseDto commentDto);
	CommentReadResponseDto toReadResponseDto(Comment comment);
	
	// Update
	Comment toEntity(CommentUpdateRequestDto commentDto);
	Comment toEntity(CommentUpdateResponseDto commentDto);
	CommentUpdateRequestDto toUpdateRequestDto(Comment comment);
	CommentUpdateResponseDto toUpdateResponseDto(Comment comment);
	
}
