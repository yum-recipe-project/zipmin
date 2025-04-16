package com.project.zipmin.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.project.zipmin.dto.RecipeCategoryDTO;
import com.project.zipmin.entity.RecipeCategory;

@Mapper(componentModel = "spring")
public interface RecipeCategoryMapper {
	RecipeCategoryDTO recipeCategoryToRecipeCategoryDTO(RecipeCategory recipeCategory);
	RecipeCategory recipeCategoryDTOToRecipeCategory(RecipeCategoryDTO recipeCategoryDTO);
	List<RecipeCategoryDTO> recipeCategoryListToRecipeCategoryDTOList(List<RecipeCategory> recipeCategoryList);
}
