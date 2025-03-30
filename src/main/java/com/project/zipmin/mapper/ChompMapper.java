package com.project.zipmin.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.project.zipmin.dto.ChompDTO;
import com.project.zipmin.entity.Chomp;

@Mapper(componentModel = "spring")
public interface ChompMapper {
	// ChompMapper INSTANCE = Mappers.getMapper(ChompMapper.class);
	
	ChompDTO chompToChompDTO(Chomp chomp);
	Chomp chompDTOToChomp(ChompDTO chompDTO);
	List<ChompDTO> chompListToChompDTOList(List<Chomp> chompList);
}
