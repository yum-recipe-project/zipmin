package com.project.zipmin.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.project.zipmin.dto.ChompMegazineResponseDTO;
import com.project.zipmin.entity.ChompMegazine;

@Mapper(componentModel = "spring")
public interface ChompMegazineMapper {
	ChompMegazineResponseDTO chompMegazineToChompMegazineResponseDTO(ChompMegazine chompMegazine);
	ChompMegazine chompMegazineResponseDTOToChompMegazine(ChompMegazineResponseDTO chompMegazineDTO);
	List<ChompMegazineResponseDTO> chompMegazineListTChompMegazineResponseDTOList(List<ChompMegazine> chompMegazineList);
}
