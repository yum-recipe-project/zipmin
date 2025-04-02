package com.project.zipmin.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.project.zipmin.dto.ChompMegazineDTO;
import com.project.zipmin.entity.ChompMegazine;

@Mapper(componentModel = "spring")
public interface ChompMegazineMapper {
	ChompMegazineDTO chompMegazineToChompMegazineDTO(ChompMegazine chompMegazine);
	ChompMegazine chompMegazineDTOToChompMegazine(ChompMegazineDTO chompMegazineDTO);
	List<ChompMegazineDTO> chompMegazineListTChompMegazineDTOList(List<ChompMegazine> chompMegazineList);
}
