package com.project.zipmin.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.project.zipmin.dto.MegazineReadResponseDto;
import com.project.zipmin.entity.Megazine;

@Mapper(componentModel = "spring")
public interface MegazineMapper {
//	MegazineReadResponseDto chompMegazineToChompMegazineDTO(ChompMegazine chompMegazine);
//	ChompMegazine toEntity(MegazineReadResponseDto chompMegazineDTO);
//	List<MegazineReadResponseDto> chompMegazineListTChompMegazineDTOList(List<ChompMegazine> chompMegazineList);
	
	
	// Read
	Megazine toEntity(MegazineReadResponseDto megazineDto);
	MegazineReadResponseDto toReadResponseDto(Megazine chompMegazine);
	
	
	

}
