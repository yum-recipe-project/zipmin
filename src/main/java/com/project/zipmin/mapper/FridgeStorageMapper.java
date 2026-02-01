package com.project.zipmin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.project.zipmin.dto.fridge.FridgeStorageCreateRequestDto;
import com.project.zipmin.dto.fridge.FridgeStorageCreateResponseDto;
import com.project.zipmin.dto.fridge.FridgeStorageReadResponseDto;
import com.project.zipmin.dto.fridge.FridgeStorageUpdateRequestDto;
import com.project.zipmin.dto.fridge.FridgeStorageUpdateResponseDto;
import com.project.zipmin.entity.FridgeStorage;

@Mapper(componentModel = "spring")
public interface FridgeStorageMapper {
	
	// Read
	@Mapping(target = "fridge.id", source = "fridgeId")
	@Mapping(target = "user.id", source = "userId")
	FridgeStorage toEntity(FridgeStorageReadResponseDto storageDto);
		
	@Mapping(target = "fridgeId", source = "fridge.id")
	@Mapping(target = "userId", source ="user.id")
	FridgeStorageReadResponseDto toReadResponseDto(FridgeStorage storage);
	
	
	
	// Create
	@Mapping(target = "fridge.id", source = "fridgeId")
	@Mapping(target = "user.id", source = "userId")
	FridgeStorage toEntity(FridgeStorageCreateRequestDto storageDto);
	
	@Mapping(target = "fridgeId", source = "fridge.id")
	@Mapping(target = "userId", source ="user.id")
	FridgeStorageCreateRequestDto toCreateRequestDto(FridgeStorage storage);
	
	@Mapping(target = "fridge.id", source = "fridgeId")
	@Mapping(target = "user.id", source = "userId")
	FridgeStorage toEntity(FridgeStorageCreateResponseDto storageDto);
	
	@Mapping(target = "fridgeId", source = "fridge.id")
	@Mapping(target = "userId", source ="user.id")
	FridgeStorageCreateResponseDto toCreateResponseDto(FridgeStorage storage);
	
	
	
	// Update
	@Mapping(target = "fridge.id", source = "fridgeId")
	@Mapping(target = "user.id", source = "userId")
	FridgeStorage toEntity(FridgeStorageUpdateRequestDto storageDto);
	
	@Mapping(target = "fridgeId", source = "fridge.id")
	@Mapping(target = "userId", source ="user.id")
	FridgeStorageUpdateRequestDto toUpdateRequestDto(FridgeStorage storage);
	
	@Mapping(target = "fridge.id", source = "fridgeId")
	@Mapping(target = "user.id", source = "userId")
	FridgeStorage toEntity(FridgeStorageUpdateResponseDto storageDto);
	
	@Mapping(target = "fridgeId", source = "fridge.id")
	@Mapping(target = "userId", source ="user.id")
	FridgeStorageUpdateResponseDto toUpdateResponseDto(FridgeStorage storage);
	
}
