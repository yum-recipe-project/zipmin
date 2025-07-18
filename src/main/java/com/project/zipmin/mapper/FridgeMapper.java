package com.project.zipmin.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.project.zipmin.dto.FridgeCreateRequestDto;
import com.project.zipmin.dto.FridgeCreateResponseDto;
import com.project.zipmin.dto.FridgeDeleteRequestDto;
import com.project.zipmin.dto.FridgeReadResponseDto;
import com.project.zipmin.dto.FridgeUpdateRequestDto;
import com.project.zipmin.dto.FridgeUpdateResponseDto;
import com.project.zipmin.entity.Fridge;

@Mapper(componentModel = "spring")
public interface FridgeMapper {
	
	// Read
	@Mapping(target = "user.id", source = "userId")
	Fridge toEntity(FridgeReadResponseDto fridgeDto);
	
	@Mapping(target = "userId", source = "user.id")
	FridgeReadResponseDto toReadResponseDto(Fridge fridge);
	
	@Mapping(target = "userId", source = "user.id")
	List<FridgeReadResponseDto> toReadResponseDtoList(List<Fridge> fridgeList);

	
	
	// Create
	@Mapping(target = "user.id", source = "userId")
	Fridge toEntity(FridgeCreateRequestDto fridgeDto);
	
	@Mapping(target = "userId", source = "user.id")
	FridgeCreateRequestDto toCreateRequestDto(Fridge fridge);
	
	@Mapping(target = "user.id", source = "userId")
	Fridge toEntity(FridgeCreateResponseDto fridgeDto);
	
	@Mapping(target = "userId", source = "user.id")
	FridgeCreateResponseDto toCreateResponseDto(Fridge fridge);
	
	
	
	// Update
	@Mapping(target = "user.id", source = "userId")
	Fridge toEntity(FridgeUpdateRequestDto fridgeDto);
	
	@Mapping(target = "userId", source = "user.id")
	FridgeUpdateRequestDto toUpdateRequestDto(Fridge fridge);
	
	@Mapping(target = "user.id", source = "userId")
	Fridge toEntity(FridgeUpdateResponseDto fridgeDto);
	
	@Mapping(target = "userId", source = "user.id")
	FridgeUpdateResponseDto toUpdateResponseDto(Fridge fridge);
	
	
	
	// Delete
	@Mapping(target = "user.id", source = "userId")
	Fridge toEntity(FridgeDeleteRequestDto fridgeDto);
	
	@Mapping(target = "userId", source = "user.id")
	FridgeDeleteRequestDto toDeleteRequestDto(Fridge fridge);
	
}
