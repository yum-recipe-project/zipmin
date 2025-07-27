package com.project.zipmin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.project.zipmin.dto.ChompCreateRequestDto;
import com.project.zipmin.dto.ChompCreateResponseDto;
import com.project.zipmin.dto.ChompReadResponseDto;
import com.project.zipmin.dto.ClassReadResponseDto;
import com.project.zipmin.entity.Chomp;
import com.project.zipmin.entity.Class;

@Mapper(componentModel = "spring")
public interface ClassMapper {
	
	// Read
	@Mapping(target = "user.id", source = "userId")
	Class toEntity(ClassReadResponseDto classDto);
	
	@Mapping(target = "userId", source = "user.id")
	ClassReadResponseDto toReadResponseDto(Class classs);
	
}
