package com.project.zipmin.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.project.zipmin.dto.RecipeIngredientDTO;
import com.project.zipmin.entity.RecipeIngredient;

@Mapper(componentModel = "spring")
public interface RecipeIngredientMapper {
	RecipeIngredientDTO recipeIngredientToRecipeIngredientDTO(RecipeIngredient recipeIngredient);
	RecipeIngredient recipeIngredientDTOToRecipeIngredient(RecipeIngredientDTO recipeIngredientDTO);
	List<RecipeIngredientDTO> recipeIngredientListToRecipeIngredientDTOList(List<RecipeIngredient> recipeIngredientList);
}
