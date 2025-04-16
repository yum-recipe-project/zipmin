package com.project.zipmin.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.project.zipmin.dto.ChompEventDTO;
import com.project.zipmin.entity.ChompEvent;

@Mapper(componentModel = "spring")
public interface ChompEventMapper {
	ChompEventDTO chompEventToChompEventDTO(ChompEvent chompEvent);
	ChompEvent chompEventDTOToChompEvent(ChompEventDTO chompEventDTO);
	List<ChompEventDTO> chompEventListToChompEventDTOList(List<ChompEvent> chompEventList);
}
