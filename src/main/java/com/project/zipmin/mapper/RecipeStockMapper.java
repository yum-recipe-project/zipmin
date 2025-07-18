package com.project.zipmin.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.project.zipmin.dto.RecipeStockCreateRequestDto;
import com.project.zipmin.dto.RecipeStockCreateResponseDto;
import com.project.zipmin.dto.RecipeStockReadResponseDto;
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
	
	
	
	// Read
	@Mapping(target = "recipe.id", source = "recipeId")
	RecipeStock toEntity(RecipeStockReadResponseDto stockDto);
	
	@Mapping(target = "recipeId", source = "recipe.id")
	RecipeStockReadResponseDto toReadResponseDto(RecipeStock stock);
	
	@Mapping(target = "recipeId", source = "recipe.id")
	List<RecipeStockReadResponseDto> toReadResponseDtoList(List<RecipeStock> stockList);
	
}
