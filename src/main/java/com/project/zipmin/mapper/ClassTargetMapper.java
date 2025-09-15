package com.project.zipmin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.project.zipmin.dto.ClassTargetReadResponseDto;
import com.project.zipmin.entity.ClassTarget;

@Mapper(componentModel = "spring")
public interface ClassTargetMapper {
	
	// Read
	@Mapping(target = "classs.id", source = "classId")
	ClassTarget toEntity(ClassTargetReadResponseDto targetDto);
	
	@Mapping(target = "classId", source = "classs.id")
	ClassTargetReadResponseDto toReadResponseDto(ClassTarget target);
	
}
