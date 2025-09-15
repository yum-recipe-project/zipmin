package com.project.zipmin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.project.zipmin.dto.GuideCreateRequestDto;
import com.project.zipmin.dto.GuideCreateResponseDto;
import com.project.zipmin.dto.GuideReadMySavedResponseDto;
import com.project.zipmin.dto.GuideReadResponseDto;
import com.project.zipmin.dto.GuideUpdateRequestDto;
import com.project.zipmin.dto.GuideUpdateResponseDto;
import com.project.zipmin.entity.Guide;

@Mapper(componentModel = "spring")
public interface GuideMapper {
	
	// Read
	@Mapping(target = "user.id", source = "userId")
	Guide toEntity(GuideReadResponseDto guideDTO);
	
	@Mapping(target = "userId", source = "user.id")
	GuideReadResponseDto toReadResponseDto(Guide guide);
	
	// Create
	@Mapping(target = "user.id", source = "userId")
	Guide toEntity(GuideCreateRequestDto guideDTO);
	
	@Mapping(target = "userId", source = "user.id")
	GuideCreateResponseDto toCreateResponseDto(Guide guide);
	
	// Update
    @Mapping(target = "id", source = "id")
    Guide toEntity(GuideUpdateRequestDto guideDTO);
    
    @Mapping(target = "userId", source = "user.id")
    GuideUpdateResponseDto toUpdateResponseDto(Guide guide);
    
    
    // 내 저장 가이드용 Read DTO 매핑
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "likecount", ignore = true) // 서비스에서 세팅
    GuideReadMySavedResponseDto toReadMySavedResponseDto(Guide guide);
	
	
}

