package com.project.zipmin.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.project.zipmin.dto.ChompResponseDTO;
import com.project.zipmin.entity.Chomp;

@Mapper(componentModel = "spring")
public interface ChompMapper {
	

	Chomp toEntity(ChompResponseDTO chompDTO);
	ChompResponseDTO toResponseDto(Chomp chomp);
	List<ChompResponseDTO> toResponseDtoList(List<Chomp> chompList);
}
