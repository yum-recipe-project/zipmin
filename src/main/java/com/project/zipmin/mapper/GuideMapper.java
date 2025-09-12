package com.project.zipmin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.project.zipmin.dto.GuideCreateRequestDto;
import com.project.zipmin.dto.GuideCreateResponseDto;
import com.project.zipmin.dto.GuideReadResponseDto;
import com.project.zipmin.entity.Guide;

@Mapper(componentModel = "spring")
public interface GuideMapper {
	
	// Read
	@Mapping(target = "user.id", source = "userId")
	Guide toEntity(GuideReadResponseDto guideDTO);
	
	@Mapping(target = "userId", source = "user.id")
	GuideReadResponseDto toReadResponseDto(Guide guide);
	
	// Create
	@Mapping(target = "user.id", source = "userId")
	Guide toEntity(GuideCreateRequestDto guideDTO);
	
	@Mapping(target = "userId", source = "user.id")
	GuideCreateResponseDto toCreateResponseDto(Guide guide);
	
}

