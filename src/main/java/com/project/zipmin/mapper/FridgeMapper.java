package com.project.zipmin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.project.zipmin.dto.FridgeCreateRequestDto;
import com.project.zipmin.dto.FridgeCreateResponseDto;
import com.project.zipmin.dto.FridgeReadResponseDto;
import com.project.zipmin.dto.FridgeUpdateRequestDto;
import com.project.zipmin.dto.FridgeUpdateResponseDto;
import com.project.zipmin.entity.Fridge;

@Mapper(componentModel = "spring")
public interface FridgeMapper {
	
	// Read
	@Mapping(target = "user.id", source = "userId")
	Fridge toEntity(FridgeReadResponseDto fridgeDto);
	
	@Mapping(target = "userId", source ="user.id")
	FridgeReadResponseDto toReadResponseDto(Fridge fridge);
	

	

	
	// Create
	@Mapping(target = "user.id", source = "userId")
	Fridge toEntity(FridgeCreateRequestDto fridgeDto);
	
	@Mapping(target = "userId", source ="user.id")
	FridgeCreateRequestDto toCreateRequestDto(Fridge fridge);
	
	@Mapping(target = "user.id", source = "userId")
	Fridge toEntity(FridgeCreateResponseDto fridgeDto);
	
	@Mapping(target = "userId", source ="user.id")
	FridgeCreateResponseDto toCreateResponseDto(Fridge fridge);
	
	
	
	
	
	// Update
	@Mapping(target = "user.id", source = "userId")
	Fridge toEntity(FridgeUpdateRequestDto fridgeDto);
	
	@Mapping(target = "userId", source ="user.id")
	FridgeUpdateRequestDto toUpdateRequestDto(Fridge fridge);
	
	@Mapping(target = "user.id", source = "userId")
	Fridge toEntity(FridgeUpdateResponseDto fridgeDto);
	
	@Mapping(target = "userId", source ="user.id")
	FridgeUpdateResponseDto toUpdateResponseDto(Fridge fridge);
	

	
}
