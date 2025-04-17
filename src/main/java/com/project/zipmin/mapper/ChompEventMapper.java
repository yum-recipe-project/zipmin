package com.project.zipmin.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.project.zipmin.dto.ChompEventResponseDTO;
import com.project.zipmin.entity.ChompEvent;

@Mapper(componentModel = "spring")
public interface ChompEventMapper {
	ChompEventResponseDTO chompEventToChompEventResponseDTO(ChompEvent chompEvent);
	ChompEvent chompEventResponseDTOToChompEvent(ChompEventResponseDTO chompEventDTO);
	List<ChompEventResponseDTO> chompEventListTChompEventResponseDTOList(List<ChompEvent> chompEventList);
}
