package com.project.zipmin.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.project.zipmin.dto.LikeCreateRequestDto;
import com.project.zipmin.dto.LikeDTO;
import com.project.zipmin.entity.Like;

@Mapper(componentModel = "spring")
public interface LikeMapper {
	Like toEntity(LikeCreateRequestDto likeDto);
	LikeCreateRequestDto toRequestDto(Like like);
	List<LikeCreateRequestDto> toResponseDtoList(List<Like> likeList);
}
