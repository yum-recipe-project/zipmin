package com.project.zipmin.mapper;

import java.util.List;
import java.util.stream.Collectors;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.project.zipmin.entity.Class;
import com.project.zipmin.dto.ClassTargetReadResponseDto;
import com.project.zipmin.entity.ClassTarget;

@Mapper(componentModel = "spring")
public interface ClassTargetMapper {
	
	// Read
	@Mapping(target = "classs.id", source = "classId")
	ClassTarget toEntity(ClassTargetReadResponseDto targetDto);
	
	@Mapping(target = "classId", source = "classs.id")
	ClassTargetReadResponseDto toReadResponseDto(ClassTarget target);
	
	
	
	// Create
	
	// DTO → Entity 변환
    default ClassTarget toEntity(String targetContent, Class classEntity) {
        if (targetContent == null || classEntity == null) return null;

        return ClassTarget.builder()
                .content(targetContent)
                .classs(classEntity)
                .build();
    }

    // 리스트 변환
    default List<ClassTarget> toEntityList(List<String> targetList, Class classEntity) {
        if (targetList == null || targetList.isEmpty()) return List.of();
        return targetList.stream()
                .map(target -> toEntity(target, classEntity))
                .collect(Collectors.toList());
    }
	
}
