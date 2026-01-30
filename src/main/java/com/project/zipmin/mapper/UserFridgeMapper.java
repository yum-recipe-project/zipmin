package com.project.zipmin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.project.zipmin.dto.UserUpdateRequestDto;
import com.project.zipmin.dto.fridge.UserFridgeCreateRequestDto;
import com.project.zipmin.dto.fridge.UserFridgeCreateResponseDto;
import com.project.zipmin.dto.fridge.UserFridgeReadResponseDto;
import com.project.zipmin.dto.fridge.UserFridgeUpdateRequestDto;
import com.project.zipmin.dto.fridge.UserFridgeUpdateResponseDto;
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
	
	
	
	// Create
	@Mapping(target = "fridge.id", source = "fridgeId")
	@Mapping(target = "user.id", source = "userId")
	UserFridge toEntity(UserFridgeCreateRequestDto userFridgeDto);
	
	@Mapping(target = "fridgeId", source = "fridge.id")
	@Mapping(target = "userId", source ="user.id")
	UserFridgeCreateRequestDto toCreateRequestDto(UserFridge userFridge);
	
	@Mapping(target = "fridge.id", source = "fridgeId")
	@Mapping(target = "user.id", source = "userId")
	UserFridge toEntity(UserFridgeCreateResponseDto userFridgeDto);
	
	@Mapping(target = "fridgeId", source = "fridge.id")
	@Mapping(target = "userId", source ="user.id")
	UserFridgeCreateResponseDto toCreateResponseDto(UserFridge userFridge);
	
	
	
	// Update
	@Mapping(target = "fridge.id", source = "fridgeId")
	@Mapping(target = "user.id", source = "userId")
	UserFridge toEntity(UserFridgeUpdateRequestDto userFridgeDto);
	
	@Mapping(target = "fridgeId", source = "fridge.id")
	@Mapping(target = "userId", source ="user.id")
	UserFridgeUpdateRequestDto toUpdateRequestDto(UserFridge userFridge);
	
	@Mapping(target = "fridge.id", source = "fridgeId")
	@Mapping(target = "user.id", source = "userId")
	UserFridge toEntity(UserFridgeUpdateResponseDto userFridgeDto);
	
	@Mapping(target = "fridgeId", source = "fridge.id")
	@Mapping(target = "userId", source ="user.id")
	UserFridgeUpdateResponseDto toUpdateResponseDto(UserFridge userFridge);
	
}
