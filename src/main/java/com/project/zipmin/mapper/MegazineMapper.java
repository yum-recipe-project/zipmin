package com.project.zipmin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.project.zipmin.dto.MegazineCreateRequestDto;
import com.project.zipmin.dto.MegazineCreateResponseDto;
import com.project.zipmin.dto.MegazineReadResponseDto;
import com.project.zipmin.dto.MegazineUpdateRequestDto;
import com.project.zipmin.dto.MegazineUpdateResponseDto;
import com.project.zipmin.entity.Megazine;

@Mapper(componentModel = "spring")
public interface MegazineMapper {
	
	// Create
	@Mapping(target = "chomp.id", source = "chompId")
	@Mapping(target = "user.id", source = "userId")
	Megazine toEntity(MegazineCreateRequestDto megazineDto);
	
	@Mapping(target = "chompId", source = "chomp.id")
	@Mapping(target = "userId", source = "user.id")
	MegazineCreateRequestDto toCreateRequestDto(Megazine megazine);
	
	Megazine toEntity(MegazineCreateResponseDto megazineDto);
	MegazineCreateResponseDto toCreateResponseDto(Megazine megazine);
	
	
	
	// Read
	Megazine toEntity(MegazineReadResponseDto megazineDto);
	MegazineReadResponseDto toReadResponseDto(Megazine megazine);
	
	
	
	// Update
	Megazine toEntity(MegazineUpdateRequestDto megazineDto);
	MegazineUpdateRequestDto toUpdateRequestDto(Megazine megazine);
	
	Megazine toEntity(MegazineUpdateResponseDto megazineDto);
	MegazineUpdateResponseDto toUpdateResponseDto(Megazine megazine);

}
