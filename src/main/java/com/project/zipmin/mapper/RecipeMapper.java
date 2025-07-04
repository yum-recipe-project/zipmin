package com.project.zipmin.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.project.zipmin.dto.RecipeDTO;
import com.project.zipmin.entity.Recipe;

@Mapper(componentModel = "spring")
public interface RecipeMapper {
	RecipeDTO recipeToRecipeDTO(Recipe recipe);
	Recipe recipeDTOToRecipe(RecipeDTO recipeDTO);
	List<RecipeDTO> recipeListToRecipeDTOList(List<Recipe> recipeList);
}
