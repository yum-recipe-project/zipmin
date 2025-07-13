package com.project.zipmin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.project.zipmin.dto.RecipeStockCreateRequestDto;
import com.project.zipmin.dto.RecipeStockCreateResponseDto;
import com.project.zipmin.entity.RecipeStock;

@Mapper(componentModel = "spring")
public interface RecipeStockMapper {
	
	// Create
	@Mapping(target = "recipe.id", source = "recipeId")
	RecipeStock toEntity(RecipeStockCreateRequestDto stockDto);
	
	@Mapping(target = "recipeId", source = "recipe.id")
	RecipeStockCreateRequestDto toCreateRequestDto(RecipeStock stock);
	
	@Mapping(target = "recipe.id", source = "recipeId")
	RecipeStock toEntity(RecipeStockCreateResponseDto stockDto);
	
	@Mapping(target = "recipeId", source = "recipe.id")
	RecipeStockCreateResponseDto toCreateResponseDto(RecipeStock stock);
	
}
