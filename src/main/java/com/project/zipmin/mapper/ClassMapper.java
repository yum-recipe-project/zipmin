package com.project.zipmin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.project.zipmin.dto.cooking.ClassCreateRequestDto;
import com.project.zipmin.dto.cooking.ClassCreateResponseDto;
import com.project.zipmin.dto.cooking.ClassReadResponseDto;
import com.project.zipmin.entity.Class;

@Mapper(componentModel = "spring")
public interface ClassMapper {
	
	// Read
	@Mapping(target = "user.id", source = "userId")
	Class toEntity(ClassReadResponseDto classDto);
	
	@Mapping(target = "userId", source = "user.id")
	ClassReadResponseDto toReadResponseDto(Class classs);
	
	
	
	// create
	@Mapping(target = "user.id", source = "userId")
	Class toEntity(ClassCreateRequestDto classDto);
	
	@Mapping(target = "userId", source = "user.id")
	ClassCreateResponseDto toCreateResponseDto(Class classs);
	
}
