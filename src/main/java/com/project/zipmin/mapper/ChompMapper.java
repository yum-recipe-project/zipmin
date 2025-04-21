package com.project.zipmin.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.project.zipmin.dto.ChompResponseDTO;
import com.project.zipmin.entity.Chomp;

@Mapper(componentModel = "spring")
public interface ChompMapper {
	ChompResponseDTO chompToChompResponseDTO(Chomp chomp);
	Chomp chompResponseDTOToChomp(ChompResponseDTO chompDTO);
	List<ChompResponseDTO> chompListToChompResponseDTOList(List<Chomp> chompList);
}
