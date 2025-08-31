package com.project.zipmin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.project.zipmin.dto.UserFridgeReadResponseDto;
import com.project.zipmin.entity.UserFridge;

@Mapper(componentModel = "spring")
public interface UserFridgeMapper {
	
	// Read
	@Mapping(target = "fridge.id", source = "fridgeId")
	@Mapping(target = "user.id", source = "userId")
	UserFridge toEntity(UserFridgeReadResponseDto userFridgeDto);
		
	@Mapping(target = "fridgeId", source = "fridge.id")
	@Mapping(target = "userId", source ="user.id")
	UserFridgeReadResponseDto toReadResponseDto(UserFridge userFridge);
	
	
}
