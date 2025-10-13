package com.project.zipmin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.project.zipmin.dto.ClassCreateRequestDto;
import com.project.zipmin.dto.ClassMyApplyReadResponseDto;
import com.project.zipmin.dto.ClassReadResponseDto;
import com.project.zipmin.dto.UserClassReadResponseDto;
import com.project.zipmin.entity.Class;

@Mapper(componentModel = "spring")
public interface ClassMapper {
	
	// Read
	@Mapping(target = "user.id", source = "userId")
	Class toEntity(ClassReadResponseDto classDto);
	
	@Mapping(target = "userId", source = "user.id")
	ClassReadResponseDto toReadResponseDto(Class classs);
	
	@Mapping(target = "userId", source = "user.id")
	UserClassReadResponseDto toReadUserResponseDto(Class classs);
	
	
	
	Class toEntity(ClassMyApplyReadResponseDto classDto);
	
	ClassMyApplyReadResponseDto toReadMyApplyResponseDto(Class classs);
	
	
	
	
	// create
    @Mapping(target = "user.id", source = "userId")
    Class toEntity(ClassCreateRequestDto dto);
	
	
}
