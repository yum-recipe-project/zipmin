package com.project.zipmin.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.project.zipmin.dto.ClassTutorCreateDto;
import com.project.zipmin.dto.ClassTutorReadResponseDto;
import com.project.zipmin.entity.ClassTutor;
import com.project.zipmin.entity.Class;

@Mapper(componentModel = "spring")
public interface ClassTutorMapper {
	
	// Read
	@Mapping(target = "classs.id", source = "classId")
	ClassTutor toEntity(ClassTutorReadResponseDto tutorDto);
	
	@Mapping(target = "classId", source = "classs.id")
	ClassTutorReadResponseDto toReadResponseDto(ClassTutor tutor);
	
	
	
	
	// Create
	// DTO → Entity 변환
    default ClassTutor toEntity(ClassTutorCreateDto tutorDto, Class classEntity) {
        if (tutorDto == null || classEntity == null) return null;

        return ClassTutor.builder()
                .name(tutorDto.getName())
                .image(tutorDto.getImage())
                .career(tutorDto.getCareer())
                .classs(classEntity)  // Class 엔티티 연결
                .build();
    }

    // 리스트 변환
    default List<ClassTutor> toEntityList(List<ClassTutorCreateDto> tutorList, Class classEntity) {
        if (tutorList == null || tutorList.isEmpty()) return List.of();

        return tutorList.stream()
                .map(tutor -> toEntity(tutor, classEntity))
                .collect(Collectors.toList());
    }
	
}
