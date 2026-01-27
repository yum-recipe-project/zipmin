package com.project.zipmin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.project.zipmin.dto.recipe.RecipeStepCreateRequestDto;
import com.project.zipmin.dto.recipe.RecipeStepCreateResponseDto;
import com.project.zipmin.dto.recipe.RecipeStepReadResponseDto;
import com.project.zipmin.entity.RecipeStep;

@Mapper(componentModel = "spring")
public interface RecipeStepMapper {
	
	// Read
	@Mapping(target = "recipe.id", source = "recipeId")
	RecipeStep toEntity(RecipeStepReadResponseDto stepDto);
	
	@Mapping(target = "recipeId", source = "recipe.id")
	RecipeStepReadResponseDto toReadResponseDto(RecipeStep step);
	
	
	
	// Create
	@Mapping(target = "recipe.id", source = "recipeId")
	RecipeStep toEntity(RecipeStepCreateRequestDto stepDto);
	
	@Mapping(target = "recipeId", source = "recipe.id")
	RecipeStepCreateRequestDto toCreateRequestDto(RecipeStep step);
	
	@Mapping(target = "recipe.id", source = "recipeId")
	RecipeStep toEntity(RecipeStepCreateResponseDto stepDto);
	
	@Mapping(target = "recipeId", source = "recipe.id")
	RecipeStepCreateResponseDto toCreateResponseDto(RecipeStep step);
	
}
