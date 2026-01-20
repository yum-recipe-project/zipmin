package com.project.zipmin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.project.zipmin.dto.FundCreateRequestDto;
import com.project.zipmin.dto.FundCreateResponseDto;
import com.project.zipmin.dto.FundReadResponseDto;
import com.project.zipmin.entity.Fund;

@Mapper(componentModel = "spring")
public interface FundMapper {
	
	@Mapping(target = "funder.id", source = "funderId")
	@Mapping(target = "fundee.id", source = "fundeeId")
	@Mapping(target = "recipe.id", source = "recipeId")
	Fund toEntity(FundReadResponseDto fundDto);
	
	@Mapping(target = "funderId", source = "funder.id")
	@Mapping(target = "fundeeId", source = "fundee.id")
	@Mapping(target = "recipeId", source = "recipe.id")
	FundReadResponseDto toReadResponseDto(Fund fund);
	
	

	@Mapping(target = "funder.id", source = "funderId")
	@Mapping(target = "fundee.id", source = "fundeeId")
	@Mapping(target = "recipe.id", source = "recipeId")
	Fund toEntity(FundCreateRequestDto fundDto);
	
	@Mapping(target = "funderId", source = "funder.id")
	@Mapping(target = "fundeeId", source = "fundee.id")
	@Mapping(target = "recipeId", source = "recipe.id")
	FundCreateResponseDto toCreateResponseDto(Fund fund);
	
	@Mapping(target = "funder.id", source = "funderId")
	@Mapping(target = "fundee.id", source = "fundeeId")
	@Mapping(target = "recipe.id", source = "recipeId")
	Fund toEntity(FundCreateResponseDto fundDto);
	
}
