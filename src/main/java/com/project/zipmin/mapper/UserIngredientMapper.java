package com.project.zipmin.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.project.zipmin.dto.UserIngredientDTO;
import com.project.zipmin.entity.Fridge;

@Mapper(componentModel = "spring")
public interface UserIngredientMapper {
	UserIngredientDTO userIngredientToUserIngredientDTO(Fridge userIngredient);
	Fridge userIngredientDTOToUserIngredient(UserIngredientDTO userIngredientDTO);
	List<UserIngredientDTO> userIngredientListToUserIngredientDTOList(List<Fridge> userIngredientList);
}
