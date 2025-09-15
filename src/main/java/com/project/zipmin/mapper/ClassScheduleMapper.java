package com.project.zipmin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.project.zipmin.dto.ClassScheduleReadResponseDto;
import com.project.zipmin.entity.ClassSchedule;

@Mapper(componentModel = "spring")
public interface ClassScheduleMapper {
	
	// Read
	@Mapping(target = "classs.id", source = "classId")
	ClassSchedule toEntity(ClassScheduleReadResponseDto scheduleDto);
	
	@Mapping(target = "classId", source = "classs.id")
	ClassScheduleReadResponseDto toReadResponseDto(ClassSchedule schedule);
	
}
