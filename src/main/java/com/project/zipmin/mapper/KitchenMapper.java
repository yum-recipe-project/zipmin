package com.project.zipmin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.project.zipmin.dto.kitchen.GuideCreateRequestDto;
import com.project.zipmin.dto.kitchen.GuideCreateResponseDto;
import com.project.zipmin.dto.kitchen.GuideReadResponseDto;
import com.project.zipmin.dto.kitchen.GuideUpdateRequestDto;
import com.project.zipmin.dto.kitchen.GuideUpdateResponseDto;
import com.project.zipmin.entity.Guide;

@Mapper(componentModel = "spring")
public interface KitchenMapper {
	
	// Read
	@Mapping(target = "user.id", source = "userId")
	Guide toEntity(GuideReadResponseDto guideDTO);
	
	@Mapping(target = "userId", source = "user.id")
	GuideReadResponseDto toReadResponseDto(Guide guide);
	
	
	
	// Create
	@Mapping(target = "user.id", source = "userId")
	Guide toEntity(GuideCreateRequestDto guideDTO);
	
	@Mapping(target = "userId", source = "user.id")
	GuideCreateRequestDto toCreateRequestDto(Guide guide);
	
	@Mapping(target = "user.id", source = "userId")
	Guide toEntity(GuideCreateResponseDto guideDTO);
	
	@Mapping(target = "userId", source = "user.id")
	GuideCreateResponseDto toCreateResponseDto(Guide guide);
	
	
	
	// Update
    @Mapping(target = "id", source = "id")
    Guide toEntity(GuideUpdateRequestDto guideDTO);
    
    @Mapping(target = "userId", source = "user.id")
    GuideUpdateRequestDto toUpdateRequestDto(Guide guide);
    
    @Mapping(target = "id", source = "id")
    Guide toEntity(GuideUpdateResponseDto guideDTO);
    
    @Mapping(target = "userId", source = "user.id")
    GuideUpdateResponseDto toUpdateResponseDto(Guide guide);
    
}

