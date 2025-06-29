package com.project.zipmin.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.project.zipmin.dto.EventReadResponseDto;
import com.project.zipmin.entity.Event;

@Mapper(componentModel = "spring")
public interface EventMapper {
	
	// Read
	Event toEntity(EventReadResponseDto chompEventDTO);
	EventReadResponseDto toReadResponseDto(Event chompEvent);
}
