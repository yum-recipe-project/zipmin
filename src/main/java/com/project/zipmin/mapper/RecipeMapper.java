package com.project.zipmin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.project.zipmin.dto.recipe.RecipeCreateRequestDto;
import com.project.zipmin.dto.recipe.RecipeCreateResponseDto;
import com.project.zipmin.dto.recipe.RecipeReadResponseDto;
import com.project.zipmin.entity.Recipe;

@Mapper(componentModel = "spring")
public interface RecipeMapper {
	
	// Read
	@Mapping(target = "user.id", source = "userId")
	Recipe toEntity(RecipeReadResponseDto recipeDto);
	
	@Mapping(target = "userId", source = "user.id")
	RecipeReadResponseDto toReadResponseDto(Recipe recipe);
	
	
	
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
