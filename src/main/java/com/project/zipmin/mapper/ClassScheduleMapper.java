package com.project.zipmin.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.project.zipmin.entity.Class;
import com.project.zipmin.dto.ClassScheduleCreateRequestDto;
import com.project.zipmin.dto.ClassScheduleCreateResponseDto;
import com.project.zipmin.dto.ClassScheduleReadResponseDto;
import com.project.zipmin.dto.recipe.RecipeCategoryCreateRequestDto;
import com.project.zipmin.dto.recipe.RecipeCategoryCreateResponseDto;
import com.project.zipmin.dto.recipe.RecipeStepCreateResponseDto;
import com.project.zipmin.entity.ClassSchedule;
import com.project.zipmin.entity.RecipeCategory;

@Mapper(componentModel = "spring")
public interface ClassScheduleMapper {
	
	// Read
	@Mapping(target = "classs.id", source = "classId")
	ClassSchedule toEntity(ClassScheduleReadResponseDto scheduleDto);
	
	@Mapping(target = "classId", source = "classs.id")
	ClassScheduleReadResponseDto toReadResponseDto(ClassSchedule schedule);
	
	
	// Create
	@Mapping(target = "classs.id", source = "classId")
	ClassSchedule toEntity(ClassScheduleCreateRequestDto scheduleDto);
	
	@Mapping(target = "classId", source = "classs.id")
	ClassScheduleCreateRequestDto toCreateRequestDto(ClassSchedule schedule);
	
	@Mapping(target = "classs.id", source = "classId")
	ClassSchedule toEntity(ClassScheduleCreateResponseDto scheduleDto);
	
	@Mapping(target = "classId", source = "classs.id")
	ClassScheduleCreateResponseDto toCreateResponseDto(ClassSchedule schedule);
	
	
	
	
	// Create
	// DTO → Entity 변환
    default ClassSchedule toEntity(ClassScheduleCreateRequestDto scheduleDto, Class classEntity) {
        if (scheduleDto == null || classEntity == null) return null;

        return ClassSchedule.builder()
                .title(scheduleDto.getTitle())
                .starttime(scheduleDto.getStarttime())
                .endtime(scheduleDto.getEndtime())
                .classs(classEntity)  // Class 엔티티 연결
                .build();
    }

    // 리스트 변환
    default List<ClassSchedule> toEntityList(List<ClassScheduleCreateRequestDto> scheduleList, Class classEntity) {
        if (scheduleList == null || scheduleList.isEmpty()) return List.of();

        return scheduleList.stream()
                .map(schedule -> toEntity(schedule, classEntity))
                .collect(Collectors.toList());
    }
}


