package com.project.zipmin.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.project.zipmin.dto.UserIngredientDTO;
import com.project.zipmin.entity.UserIngredient;

@Mapper(componentModel = "spring")
public interface UserIngredientMapper {
	UserIngredientDTO userIngredientToUserIngredientDTO(UserIngredient userIngredient);
	UserIngredient userIngredientDTOToUserIngredient(UserIngredientDTO userIngredientDTO);
	List<UserIngredientDTO> userIngredientListToUserIngredientDTOList(List<UserIngredient> userIngredientList);
}
