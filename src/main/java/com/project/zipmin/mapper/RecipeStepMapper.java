package com.project.zipmin.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.project.zipmin.dto.RecipeStepDTO;
import com.project.zipmin.entity.RecipeStep;

@Mapper(componentModel = "spring")
public interface RecipeStepMapper {
	RecipeStepDTO recipeStepToRecipeStepDTO(RecipeStep recipeStep);
	RecipeStep recipeStepDTOToRecipeStep(RecipeStepDTO recipeStepDTO);
	List<RecipeStepDTO> recipeStepListToRecipeStepDTOList(List<RecipeStep> recipeStepList);
}
