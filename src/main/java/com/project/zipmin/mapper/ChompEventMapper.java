package com.project.zipmin.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.project.zipmin.dto.EventResponseDTO;
import com.project.zipmin.entity.ChompEvent;

@Mapper(componentModel = "spring")
public interface ChompEventMapper {
	EventResponseDTO chompEventToChompEventDTO(ChompEvent chompEvent);
	ChompEvent chompEventDTOToChompEvent(EventResponseDTO chompEventDTO);
	List<EventResponseDTO> chompEventListToChompEventDTOList(List<ChompEvent> chompEventList);
}
