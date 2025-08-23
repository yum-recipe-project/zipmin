package com.project.zipmin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.project.zipmin.dto.RecipeCategoryCreateRequestDto;
import com.project.zipmin.dto.RecipeCategoryCreateResponseDto;
import com.project.zipmin.dto.RecipeCategoryReadResponseDto;
import com.project.zipmin.dto.RecipeStepCreateResponseDto;
import com.project.zipmin.entity.RecipeCategory;

@Mapper(componentModel = "spring")
public interface RecipeCategoryMapper {
	
	// Create
	@Mapping(target = "recipe.id", source = "recipeId")
	RecipeCategory toEntity(RecipeCategoryCreateRequestDto categoryDto);
	
	@Mapping(target = "recipeId", source = "recipe.id")
	RecipeCategoryCreateRequestDto toCreateRequestDto(RecipeCategory category);
	
	@Mapping(target = "recipe.id", source = "recipeId")
	RecipeCategory toEntity(RecipeStepCreateResponseDto categoryDto);
	
	@Mapping(target = "recipeId", source = "recipe.id")
	RecipeCategoryCreateResponseDto toCreateResponseDto(RecipeCategory category);
	
	
	
	// Read
	@Mapping(target = "recipe.id", source = "recipeId")
	RecipeCategory toEntity(RecipeCategoryReadResponseDto categoryDto);
	
	@Mapping(target = "recipeId", source = "recipe.id")
	RecipeCategoryReadResponseDto toReadResponseDto(RecipeCategory category);
	
}
