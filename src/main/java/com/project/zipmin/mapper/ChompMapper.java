package com.project.zipmin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.project.zipmin.dto.ChompCreateRequestDto;
import com.project.zipmin.dto.ChompCreateResponseDto;
import com.project.zipmin.dto.ChompReadResponseDto;
import com.project.zipmin.entity.Chomp;

@Mapper(componentModel = "spring")
public interface ChompMapper {
	
	// Create
	@Mapping(target = "id", ignore = true)
	Chomp toEntity(ChompCreateRequestDto chompDto);
	
	ChompCreateRequestDto toCreateRequestDto(Chomp chomp);
	
	Chomp toEntity(ChompCreateResponseDto chompDto);
	
	ChompCreateResponseDto toCreateResponseDto(Chomp chomp);
	
	
	
	// Read
	Chomp toEntity(ChompReadResponseDto chompDto);
	
	@Mapping(target = "voteDto", ignore = true)
	@Mapping(target = "megazineDto", ignore = true)
	@Mapping(target = "eventDto", ignore = true)
	ChompReadResponseDto toReadResponseDto(Chomp chomp);
	
}
