package com.project.zipmin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.project.zipmin.dto.RecipeStepCreateRequestDto;
import com.project.zipmin.dto.RecipeStepCreateResponseDto;
import com.project.zipmin.dto.RecipeStepReadResponseDto;
import com.project.zipmin.entity.RecipeStep;

@Mapper(componentModel = "spring")
public interface RecipeStepMapper {
	
	// Create
	@Mapping(target = "recipe.id", source = "recipeId")
	RecipeStep toEntity(RecipeStepCreateRequestDto stepDto);
	
	@Mapping(target = "recipeId", source = "recipe.id")
	RecipeStepCreateRequestDto toCreateRequestDto(RecipeStep step);
	
	@Mapping(target = "recipe.id", source = "recipeId")
	RecipeStep toEntity(RecipeStepCreateResponseDto stepDto);
	
	@Mapping(target = "recipeId", source = "recipe.id")
	RecipeStepCreateResponseDto toCreateResponseDto(RecipeStep step);
	
	
	
	
	// Read
	@Mapping(target = "recipe.id", source = "recipeId")
	RecipeStep toEntity(RecipeStepReadResponseDto stepDto);
	
	@Mapping(target = "recipeId", source = "recipe.id")
	RecipeStepReadResponseDto toReadResponseDto(RecipeStep step);
	
}
