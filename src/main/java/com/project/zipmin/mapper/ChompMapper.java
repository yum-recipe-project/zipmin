package com.project.zipmin.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.project.zipmin.dto.ChompReadResponseDto;
import com.project.zipmin.entity.Chomp;

@Mapper(componentModel = "spring")
public interface ChompMapper {
	
	// Read
	Chomp toEntity(ChompReadResponseDto chompDTO);
	ChompReadResponseDto toReadResponseDto(Chomp chomp);
}
