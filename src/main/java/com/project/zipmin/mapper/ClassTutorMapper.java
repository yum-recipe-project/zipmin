package com.project.zipmin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.project.zipmin.dto.cooking.ClassTutorCreateReqeustDto;
import com.project.zipmin.dto.cooking.ClassTutorCreateResponseDto;
import com.project.zipmin.dto.cooking.ClassTutorReadResponseDto;
import com.project.zipmin.entity.ClassTutor;

@Mapper(componentModel = "spring")
public interface ClassTutorMapper {
	
	// Read
	@Mapping(target = "classs.id", source = "classId")
	ClassTutor toEntity(ClassTutorReadResponseDto tutorDto);
	
	@Mapping(target = "classId", source = "classs.id")
	ClassTutorReadResponseDto toReadResponseDto(ClassTutor tutor);
	
	
	
	// Create
	@Mapping(target = "classs.id", source = "classId")
	ClassTutor toEntity(ClassTutorCreateReqeustDto tutorDto);
	
	@Mapping(target = "classId", source = "classs.id")
	ClassTutorCreateReqeustDto toCreateRequestDto(ClassTutor tutor);
	
	@Mapping(target = "classs.id", source = "classId")
	ClassTutor toEntity(ClassTutorCreateResponseDto tutorDto);
	
	@Mapping(target = "classId", source = "classs.id")
	ClassTutorCreateResponseDto toCreateResponseDto(ClassTutor tutor);
	
}
