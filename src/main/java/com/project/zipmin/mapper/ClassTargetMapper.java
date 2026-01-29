package com.project.zipmin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.project.zipmin.dto.cooking.ClassTargetCreateRequestDto;
import com.project.zipmin.dto.cooking.ClassTargetCreateResponseDto;
import com.project.zipmin.dto.cooking.ClassTargetReadResponseDto;
import com.project.zipmin.entity.ClassTarget;

@Mapper(componentModel = "spring")
public interface ClassTargetMapper {
	
	// Read
	@Mapping(target = "classs.id", source = "classId")
	ClassTarget toEntity(ClassTargetReadResponseDto targetDto);
	
	@Mapping(target = "classId", source = "classs.id")
	ClassTargetReadResponseDto toReadResponseDto(ClassTarget target);
	
	
	
	// Create
	@Mapping(target = "classs.id", source = "classId")
	ClassTarget toEntity(ClassTargetCreateRequestDto targetDto);
	
	@Mapping(target = "classId", source = "classs.id")
	ClassTargetCreateRequestDto toCreateRequestDto(ClassTarget target);
	
	@Mapping(target = "classId", source = "classs.id")
	ClassTargetCreateResponseDto toCreateResponseDto(ClassTarget target);

}
