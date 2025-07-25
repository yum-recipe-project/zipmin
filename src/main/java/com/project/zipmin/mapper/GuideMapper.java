package com.project.zipmin.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.project.zipmin.dto.GuideReadResponseDto;
import com.project.zipmin.entity.Guide;

@Mapper(componentModel = "spring")
public interface GuideMapper {
	
	// Read
	Guide toEntity(GuideReadResponseDto guideDTO);
	GuideReadResponseDto toReadResponseDto(Guide guide);
	
	
	
	
}
