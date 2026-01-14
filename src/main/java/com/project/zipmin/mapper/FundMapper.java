package com.project.zipmin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.project.zipmin.dto.FundReadResponseDto;
import com.project.zipmin.entity.Fund;

@Mapper(componentModel = "spring")
public interface FundMapper {
	
    @Mapping(target = "funderId", source = "funder.id")
    @Mapping(target = "fundeeId", source = "fundee.id")
    @Mapping(target = "recipeId", source = "recipe.id")
    FundReadResponseDto toReadRevenueResponseDto(Fund fund);
	
}
