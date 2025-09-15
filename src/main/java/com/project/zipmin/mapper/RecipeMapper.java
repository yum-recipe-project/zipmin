package com.project.zipmin.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.project.zipmin.dto.RecipeCreateRequestDto;
import com.project.zipmin.dto.RecipeCreateResponseDto;
import com.project.zipmin.dto.RecipeReadMyResponseDto;
import com.project.zipmin.dto.RecipeReadMySavedResponseDto;
import com.project.zipmin.dto.RecipeReadResponseDto;
import com.project.zipmin.entity.Recipe;

@Mapper(componentModel = "spring")
public interface RecipeMapper {
	
	// Read
	@Mapping(target = "user.id", source = "userId")
	Recipe toEntity(RecipeReadResponseDto recipeDto);
	
	@Mapping(target = "userId", source = "user.id")
	RecipeReadResponseDto toReadResponseDto(Recipe recipe);
	
	@Mapping(target = "userId", source = "user.id")
	List<RecipeReadResponseDto> toReadResponseDtoList(List<Recipe> recipeList);
	
	
	
	// Create
	@Mapping(target = "user.id", source = "userId")
	Recipe toEntity(RecipeCreateRequestDto recipeDto);
	
	@Mapping(target = "userId", source = "user.id")
	RecipeCreateRequestDto toCreateRequestDto(Recipe recipe);
	
	
	@Mapping(target = "user.id", source = "userId")
	Recipe toEntity(RecipeCreateResponseDto recipeDto);
	
	@Mapping(target = "userId", source = "user.id")
	RecipeCreateResponseDto toCreateResponseDto(Recipe recipe);
	
	
	
	
	// 저장한(좋아요) 레시피용
    RecipeReadMySavedResponseDto toReadMySavedResponseDto(Recipe recipe);
    
    
//	@Mapping(target = "commId", source = "comment.id")
	@Mapping(target = "userId", source = "user.id")
	RecipeReadMyResponseDto toReadMyResponseDto(Recipe recipe);
	
	
	
}
