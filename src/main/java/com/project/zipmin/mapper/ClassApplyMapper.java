package com.project.zipmin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.project.zipmin.dto.ClassApplyCreateRequestDto;
import com.project.zipmin.dto.ClassApplyCreateResponseDto;
import com.project.zipmin.dto.ClassApplyReadResponseDto;
import com.project.zipmin.dto.ClassApplyUpdateRequestDto;
import com.project.zipmin.dto.ClassApplyUpdateResponseDto;
import com.project.zipmin.entity.ClassApply;

@Mapper(componentModel = "spring")
public interface ClassApplyMapper {
	
	// Read
	@Mapping(target = "classs.id", source = "classId")
	@Mapping(target = "user.id", source = "userId")
	ClassApply toEntity(ClassApplyReadResponseDto applyDto);
	
	@Mapping(target = "classId", source = "classs.id")
	@Mapping(target = "userId", source = "user.id")
	ClassApplyReadResponseDto toReadResponseDto(ClassApply apply);
	
	
	
	// Create
	@Mapping(target = "classs.id", source = "classId")
	@Mapping(target = "user.id", source = "userId")
	ClassApply toEntity(ClassApplyCreateRequestDto applyDto);
	
	@Mapping(target = "classId", source = "classs.id")
	@Mapping(target = "userId", source = "user.id")
	ClassApplyCreateRequestDto toCreateRequestDto(ClassApply apply);

	@Mapping(target = "classs.id", source = "classId")
	@Mapping(target = "user.id", source = "userId")
	ClassApply toEntity(ClassApplyCreateResponseDto applyDto);
	
	@Mapping(target = "classId", source = "classs.id")
	@Mapping(target = "userId", source = "user.id")
	ClassApplyCreateResponseDto toCreateResponseDto(ClassApply apply);
	
	
	
	// Update
	@Mapping(target = "classs.id", source = "classId")
	ClassApply toEntity(ClassApplyUpdateRequestDto applyDto);
	
	@Mapping(target = "classId", source = "classs.id")
	ClassApplyUpdateRequestDto toUpdateRequestDto(ClassApply apply);
	
	@Mapping(target = "classs.id", source = "classId")
	ClassApply toEntity(ClassApplyUpdateResponseDto applyDto);
	
	@Mapping(target = "classId", source = "classs.id")
	ClassApplyUpdateResponseDto toUpdateResponseDto(ClassApply apply);
	
}
