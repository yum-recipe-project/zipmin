package com.project.zipmin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.project.zipmin.dto.GuideReadResponseDto;
import com.project.zipmin.entity.Guide;

@Mapper(componentModel = "spring")
public interface GuideMapper {
	
	// Read
	@Mapping(target = "user.id", source = "userId")
	Guide toEntity(GuideReadResponseDto guideDTO);
	
	@Mapping(target = "userId", source = "user.id")
	GuideReadResponseDto toReadResponseDto(Guide guide);
	
}

