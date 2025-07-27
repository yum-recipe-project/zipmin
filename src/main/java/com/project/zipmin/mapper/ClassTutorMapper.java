package com.project.zipmin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.project.zipmin.dto.ClassTutorReadResponseDto;
import com.project.zipmin.entity.ClassTutor;

@Mapper(componentModel = "spring")
public interface ClassTutorMapper {
	
	// Read
	@Mapping(target = "classs.id", source = "classId")
	ClassTutor toEntity(ClassTutorReadResponseDto tutorDto);
	
	@Mapping(target = "classId", source = "classs.id")
	ClassTutorReadResponseDto toReadResponseDto(ClassTutor tutor);
	
}
