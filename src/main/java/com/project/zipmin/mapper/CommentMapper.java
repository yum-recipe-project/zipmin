package com.project.zipmin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.project.zipmin.dto.UserCommentReadesponseDto;
import com.project.zipmin.dto.comment.CommentCreateRequestDto;
import com.project.zipmin.dto.comment.CommentCreateResponseDto;
import com.project.zipmin.dto.comment.CommentReadResponseDto;
import com.project.zipmin.dto.comment.CommentUpdateRequestDto;
import com.project.zipmin.dto.comment.CommentUpdateResponseDto;
import com.project.zipmin.entity.Comment;

@Mapper(componentModel = "spring")
public interface CommentMapper {
	
	// Read
	@Mapping(target = "comment.id", source = "commId")
	@Mapping(target = "user.id", source = "userId")
	Comment toEntity(CommentReadResponseDto commentDto);
	
	@Mapping(target = "commId", source = "comment.id")
	@Mapping(target = "userId", source = "user.id")
	CommentReadResponseDto toReadResponseDto(Comment comment);
	
	@Mapping(target = "comment.id", source = "commId")
	@Mapping(target = "user.id", source = "userId")
	Comment toEntity(UserCommentReadesponseDto commentDto);
	
	@Mapping(target = "commId", source = "comment.id")
	@Mapping(target = "userId", source = "user.id")
	UserCommentReadesponseDto toReadMyResponseDto(Comment comment);
	
	
	
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
	
	
	
	// Update
	Comment toEntity(CommentUpdateRequestDto commentDto);
	
	CommentUpdateRequestDto toUpdateRequestDto(Comment comment);
	
	@Mapping(target = "comment.id", source = "commId")
	@Mapping(target = "user.id", source = "userId")
	Comment toEntity(CommentUpdateResponseDto commentDto);
	
	@Mapping(target = "commId", source = "comment.id")
	@Mapping(target = "userId", source = "user.id")
	CommentUpdateResponseDto toUpdateResponseDto(Comment comment);
	
}
