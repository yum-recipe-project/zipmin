package com.project.zipmin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.project.zipmin.dto.UserFridgeCreateRequestDto;
import com.project.zipmin.dto.UserFridgeCreateResponseDto;
import com.project.zipmin.dto.UserFridgeReadResponseDto;
import com.project.zipmin.dto.UserFridgeUpdateResponseDto;
import com.project.zipmin.dto.UserUpdateRequestDto;
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
	UserFridge toEntity(UserUpdateRequestDto userFridgeDto);
	
	@Mapping(target = "fridgeId", source = "fridge.id")
	@Mapping(target = "userId", source ="user.id")
	UserUpdateRequestDto toUpdateRequestDto(UserFridge userFridge);
	
	@Mapping(target = "fridge.id", source = "fridgeId")
	@Mapping(target = "user.id", source = "userId")
	UserFridge toEntity(UserFridgeUpdateResponseDto userFridgeDto);
	
	@Mapping(target = "fridgeId", source = "fridge.id")
	@Mapping(target = "userId", source ="user.id")
	UserFridgeUpdateResponseDto toUpdateResponseDto(UserFridge userFridge);
	
}
