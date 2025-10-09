package com.project.zipmin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.project.zipmin.dto.MemoCreateRequestDto;
import com.project.zipmin.dto.MemoCreateResponseDto;
import com.project.zipmin.dto.MemoReadResponseDto;
import com.project.zipmin.dto.MemoUpdateRequestDto;
import com.project.zipmin.dto.MemoUpdateResponseDto;
import com.project.zipmin.entity.Memo;

@Mapper(componentModel = "spring")
public interface MemoMapper {
	
	
	// Read
    @Mapping(target = "user.id", source = "userId")
    Memo toEntity(MemoReadResponseDto memoDTO);
    
    @Mapping(target = "userId", source = "user.id")
    MemoReadResponseDto toReadResponseDto(Memo memo);
   
	
	// Create
    @Mapping(target = "id", ignore = true)
	@Mapping(target = "user.id", source = "userId")
	Memo toEntity(MemoCreateRequestDto memoDTO);
	
	@Mapping(target = "userId", source = "user.id")
	MemoCreateResponseDto toCreateResponseDto(Memo memo);
	
	// Update
    Memo toEntity(MemoUpdateRequestDto memoDTO);

    MemoUpdateResponseDto toUpdateResponseDto(Memo memo);
    
	
}

