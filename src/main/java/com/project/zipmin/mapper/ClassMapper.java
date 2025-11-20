package com.project.zipmin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.project.zipmin.dto.ClassCreateRequestDto;
import com.project.zipmin.dto.ClassCreateResponseDto;
import com.project.zipmin.dto.ClassReadResponseDto;
import com.project.zipmin.dto.UserAppliedClassResponseDto;
import com.project.zipmin.dto.UserClassReadResponseDto;
import com.project.zipmin.entity.Class;
import com.project.zipmin.entity.ClassApply;

@Mapper(componentModel = "spring")
public interface ClassMapper {
	
	// Read
	@Mapping(target = "user.id", source = "userId")
	Class toEntity(ClassReadResponseDto classDto);
	
	@Mapping(target = "userId", source = "user.id")
	ClassReadResponseDto toReadResponseDto(Class classs);
	
	@Mapping(target = "userId", source = "user.id")
	UserClassReadResponseDto toReadUserResponseDto(Class classs);
	
	
	
	Class toEntity(UserAppliedClassResponseDto classDto);
	
	// UserAppliedClassResponseDto toReadUserAppliedResponseDto(Class classs);
	
	
	@Mapping(target = "id", source = "classs.id")
	@Mapping(target = "applyId", source = "apply.id")
	@Mapping(target = "selected", source = "apply.selected")
	UserAppliedClassResponseDto toReadUserAppliedResponseDto(Class classs, ClassApply apply);

	
	
	
	
	// create
    @Mapping(target = "user.id", source = "userId")
    Class toEntity(ClassCreateRequestDto dto);
    
    @Mapping(target = "userId", source = "user.id")
    ClassCreateResponseDto toCreateResponseDto(Class classs);
	
	
}
