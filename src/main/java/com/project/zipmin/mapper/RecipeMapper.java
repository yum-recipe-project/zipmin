package com.project.zipmin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.project.zipmin.dto.RecipeCreateRequestDto;
import com.project.zipmin.dto.RecipeCreateResponseDto;
import com.project.zipmin.entity.Recipe;

@Mapper(componentModel = "spring")
public interface RecipeMapper {
	
	// Create
	@Mapping(target = "user.id", source = "userId")
	Recipe toEntity(RecipeCreateRequestDto recipeDto);
	
	@Mapping(target = "userId", source = "user.id")
	RecipeCreateRequestDto toCreateRequestDto(Recipe recipe);
	
	
	@Mapping(target = "user.id", source = "userId")
	Recipe toEntity(RecipeCreateResponseDto recipeDto);
	
	@Mapping(target = "userId", source = "user.id")
	RecipeCreateResponseDto toCreateResponseDto(Recipe recipe);
	
}
