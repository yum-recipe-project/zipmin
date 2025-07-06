package com.project.zipmin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.project.zipmin.dto.LikeCreateRequestDto;
import com.project.zipmin.dto.LikeCreateResponseDto;
import com.project.zipmin.dto.LikeDeleteRequestDto;
import com.project.zipmin.entity.Like;

@Mapper(componentModel = "spring")
public interface LikeMapper {
	
	// Create
	@Mapping(target = "user.id", source = "userId")
	Like toEntity(LikeCreateRequestDto likeDto);
	
	@Mapping(target = "userId", source = "user.id")
	LikeCreateRequestDto toRequestDto(Like like);
	
	@Mapping(target = "user.id", source = "userId")
	Like toEntity(LikeCreateResponseDto likeDto);
	
	@Mapping(target = "userId", source = "user.id")
	LikeCreateResponseDto toResponseDto(Like like);
	
	
	
	// Delete
	@Mapping(target = "user.id", source = "userId")
	Like toEntity(LikeDeleteRequestDto likeDto);
	
	@Mapping(target = "userId", source = "user.id")
	LikeDeleteRequestDto toDeleteRequestDto(Like like);
	
}
