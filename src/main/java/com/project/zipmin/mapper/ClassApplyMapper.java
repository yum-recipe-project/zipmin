package com.project.zipmin.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.project.zipmin.dto.cooking.ClassApplyCreateRequestDto;
import com.project.zipmin.dto.cooking.ClassApplyCreateResponseDto;
import com.project.zipmin.dto.cooking.ClassApplyReadResponseDto;
import com.project.zipmin.dto.cooking.ClassApplyUpdateRequestDto;
import com.project.zipmin.dto.cooking.ClassApplyUpdateResponseDto;
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
	
	@Mapping(target = "classId", source = "classs.id")
	@Mapping(target = "userId", source = "user.id")
	List<ClassApplyReadResponseDto> toReadResponseDtoList(List<ClassApply> applyList);
	
	
	
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
	@Mapping(target = "user.id", source = "userId")
	ClassApply toEntity(ClassApplyUpdateRequestDto applyDto);
	
	@Mapping(target = "classId", source = "classs.id")
	@Mapping(target = "userId", source = "user.id")
	ClassApplyUpdateRequestDto toUpdateRequestDto(ClassApply apply);
	
	@Mapping(target = "classs.id", source = "classId")
	@Mapping(target = "user.id", source = "userId")
	ClassApply toEntity(ClassApplyUpdateResponseDto applyDto);
	
	@Mapping(target = "classId", source = "classs.id")
	@Mapping(target = "userId", source = "user.id")
	ClassApplyUpdateResponseDto toUpdateResponseDto(ClassApply apply);
	
}
