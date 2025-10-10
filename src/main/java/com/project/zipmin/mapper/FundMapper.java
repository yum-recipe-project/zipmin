package com.project.zipmin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.project.zipmin.dto.UserRevenueReadResponseDto;
import com.project.zipmin.entity.Fund;

@Mapper(componentModel = "spring")
public interface FundMapper {
	
//	@Mapping(source = "funder.id", target = "funderId")
//	UserRevenueReadResponseDto toUserRevenueDto(Fund fund);
	
	
//	@Mapping(target = "fundeeId", source = "fundee.id")
//	UserRevenueReadResponseDto toReadRevenueResponseDto(Fund fund);
	
	
	
    @Mapping(target = "fundId", source = "id")
    @Mapping(target = "funderId", source = "funder.id")
//    @Mapping(target = "funderNickname", ignore = true)
    @Mapping(target = "recipeId", source = "recipe.id")
//    @Mapping(target = "recipeTitle", ignore = true)
//    @Mapping(target = "point", source = "point")
    @Mapping(target = "fundDate", source = "funddate")
    @Mapping(target = "status", source = "status")
    UserRevenueReadResponseDto toReadRevenueResponseDto(Fund fund);
	
}
