package com.project.zipmin.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.project.zipmin.entity.Class;
import com.project.zipmin.dto.ClassScheduleCreateDto;
import com.project.zipmin.dto.ClassScheduleReadResponseDto;
import com.project.zipmin.entity.ClassSchedule;

@Mapper(componentModel = "spring")
public interface ClassScheduleMapper {
	
	// Read
	@Mapping(target = "classs.id", source = "classId")
	ClassSchedule toEntity(ClassScheduleReadResponseDto scheduleDto);
	
	@Mapping(target = "classId", source = "classs.id")
	ClassScheduleReadResponseDto toReadResponseDto(ClassSchedule schedule);
	
	
	
	
	
	// Create
	// DTO → Entity 변환
    default ClassSchedule toEntity(ClassScheduleCreateDto scheduleDto, Class classEntity) {
        if (scheduleDto == null || classEntity == null) return null;

        return ClassSchedule.builder()
                .title(scheduleDto.getTitle())
                .starttime(scheduleDto.getStarttime())
                .endtime(scheduleDto.getEndtime())
                .classs(classEntity)  // Class 엔티티 연결
                .build();
    }

    // 리스트 변환
    default List<ClassSchedule> toEntityList(List<ClassScheduleCreateDto> scheduleList, Class classEntity) {
        if (scheduleList == null || scheduleList.isEmpty()) return List.of();

        return scheduleList.stream()
                .map(schedule -> toEntity(schedule, classEntity))
                .collect(Collectors.toList());
    }
}


