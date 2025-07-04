package com.project.zipmin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.project.zipmin.dto.CommentCreateRequestDto;
import com.project.zipmin.dto.CommentCreateResponseDto;
import com.project.zipmin.dto.CommentReadResponseDto;
import com.project.zipmin.dto.CommentUpdateRequestDto;
import com.project.zipmin.dto.CommentUpdateResponseDto;
import com.project.zipmin.entity.Comment;

@Mapper(componentModel = "spring")
public interface CommentMapper {
	
	// Create
	@Mapping(target = "comment", ignore = true)
	@Mapping(target = "user.id", source = "userId")
	Comment toEntity(CommentCreateRequestDto commentDto);
	
	@Mapping(target = "commId", source = "comment.id")
	@Mapping(target = "userId", source = "user.id")
	CommentCreateRequestDto toCreateRequestDto(Comment comment);
	
	@Mapping(target = "comment.id", source = "commId")
	@Mapping(target = "user.id", source = "userId")
	Comment toEntity(CommentCreateResponseDto commentDto);

	@Mapping(target = "commId", source = "comment.id")
	@Mapping(target = "userId", source = "user.id")
	CommentCreateResponseDto toCreateResponseDto(Comment comment);
	
	
	
	// Read
	Comment toEntity(CommentReadResponseDto commentDto);
	CommentReadResponseDto toReadResponseDto(Comment comment);
	
	
	
	// Update
	Comment toEntity(CommentUpdateRequestDto commentDto);
	Comment toEntity(CommentUpdateResponseDto commentDto);
	CommentUpdateRequestDto toUpdateRequestDto(Comment comment);
	CommentUpdateResponseDto toUpdateResponseDto(Comment comment);
	
}
