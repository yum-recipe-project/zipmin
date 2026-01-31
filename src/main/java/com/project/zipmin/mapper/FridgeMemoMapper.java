package com.project.zipmin.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.project.zipmin.dto.fridge.MemoCreateRequestDto;
import com.project.zipmin.dto.fridge.MemoCreateResponseDto;
import com.project.zipmin.dto.fridge.MemoReadResponseDto;
import com.project.zipmin.dto.fridge.MemoUpdateRequestDto;
import com.project.zipmin.dto.fridge.MemoUpdateResponseDto;
import com.project.zipmin.entity.FridgeMemo;

@Mapper(componentModel = "spring")
public interface FridgeMemoMapper {
	
	// Read
	@Mapping(target = "user.id", source = "userId")
	FridgeMemo toEntity(MemoReadResponseDto memoDTO);
	
	@Mapping(target = "userId", source = "user.id")
	MemoReadResponseDto toReadResponseDto(FridgeMemo memo);
	
	@Mapping(target = "userId", source = "user.id")
	List<MemoReadResponseDto> toReadResponseDtoList(List<FridgeMemo> memoList);
	
	
	
	// Create
	@Mapping(target = "user.id", source = "userId")
	FridgeMemo toEntity(MemoCreateRequestDto memoDTO);
	
	@Mapping(target = "userId", source = "user.id")
	MemoCreateResponseDto toCreateResponseDto(FridgeMemo memo);
	
	
	
	// Update
	@Mapping(target = "user.id", source = "userId")
	FridgeMemo toEntity(MemoUpdateRequestDto memoDTO);

	@Mapping(target = "userId", source = "user.id")
	MemoUpdateResponseDto toUpdateResponseDto(FridgeMemo memo);
	
}

