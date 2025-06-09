package com.project.zipmin.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.project.zipmin.dto.MegazineResponseDTO;
import com.project.zipmin.entity.ChompMegazine;

@Mapper(componentModel = "spring")
public interface ChompMegazineMapper {
	MegazineResponseDTO chompMegazineToChompMegazineDTO(ChompMegazine chompMegazine);
	ChompMegazine chompMegazineDTOToChompMegazine(MegazineResponseDTO chompMegazineDTO);
	List<MegazineResponseDTO> chompMegazineListTChompMegazineDTOList(List<ChompMegazine> chompMegazineList);
}
