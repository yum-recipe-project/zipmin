package com.project.zipmin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.project.zipmin.dto.EventCreateRequestDto;
import com.project.zipmin.dto.EventCreateResponseDto;
import com.project.zipmin.dto.EventReadResponseDto;
import com.project.zipmin.dto.EventUpdateRequestDto;
import com.project.zipmin.dto.EventUpdateResponseDto;
import com.project.zipmin.entity.Event;

@Mapper(componentModel = "spring")
public interface EventMapper {
	
	// Create
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "chomp.id", source = "chompId")
	Event toEntity(EventCreateRequestDto eventDto);
	
	@Mapping(target = "chompId", source = "chomp.id")
	EventCreateRequestDto toCreateRequestDto(Event event);
	
	@Mapping(target = "chomp.id", source = "chompId")
	Event toEntity(EventCreateResponseDto eventDto);
	
	@Mapping(target = "chompId", source = "chomp.id")
	EventCreateResponseDto toCreateResponseDto(Event event);
	
	
	
	// Read
	Event toEntity(EventReadResponseDto eventDto);
	
	@Mapping(target = "status", ignore = true)
	EventReadResponseDto toReadResponseDto(Event event);
	
	
	
	// Update
	Event toEntity(EventUpdateRequestDto eventDto);
	EventUpdateRequestDto toUpdateResquestDto(Event event);
	
	Event toEntity(EventUpdateResponseDto eventDto);
	EventUpdateResponseDto toUpdateResponseDto(Event event);
}
