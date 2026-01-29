package com.project.zipmin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.project.zipmin.dto.cooking.ClassScheduleCreateRequestDto;
import com.project.zipmin.dto.cooking.ClassScheduleCreateResponseDto;
import com.project.zipmin.dto.cooking.ClassScheduleReadResponseDto;
import com.project.zipmin.entity.ClassSchedule;

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
	
}


