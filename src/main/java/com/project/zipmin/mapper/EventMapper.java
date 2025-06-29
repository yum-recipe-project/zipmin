package com.project.zipmin.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.project.zipmin.dto.EventReadResponseDto;
import com.project.zipmin.entity.ChompEvent;

@Mapper(componentModel = "spring")
public interface EventMapper {
	
	// Read
	ChompEvent toEntity(EventReadResponseDto chompEventDTO);
	EventReadResponseDto toReadResponseDto(ChompEvent chompEvent);
}
