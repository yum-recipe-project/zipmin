package com.project.zipmin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.project.zipmin.dto.VoteChoiceUpdateRequestDto;
import com.project.zipmin.dto.VoteChoiceUpdateResponseDto;
import com.project.zipmin.dto.chomp.VoteChoiceCreateRequestDto;
import com.project.zipmin.dto.chomp.VoteChoiceReadResponseDto;
import com.project.zipmin.entity.VoteChoice;

@Mapper(componentModel = "spring")
public interface VoteChoiceMapper {
	
	// Read
	@Mapping(target = "chomp.id", source = "chompId")
	VoteChoice toEntity(VoteChoiceReadResponseDto choiceDto);
	
	@Mapping(target = "chompId", source = "chomp.id")
	VoteChoiceReadResponseDto toReadResponseDto(VoteChoice choice);
	
	
	
	// Create
	@Mapping(target = "chomp.id", source = "chompId")
	VoteChoice toEntity(VoteChoiceCreateRequestDto choiceDto);
	
	@Mapping(target = "chompId", source = "chomp.id")
	VoteChoiceCreateRequestDto toCreateRequestDto(VoteChoice choice);
	
	
	
	// Update
	@Mapping(target = "chomp.id", source = "chompId")
	VoteChoice toEntity(VoteChoiceUpdateRequestDto choiceDto);
	
	@Mapping(target = "chompId", source = "chomp.id")
	VoteChoiceUpdateRequestDto toUpdateRequestDto(VoteChoice choice);
	
	@Mapping(target = "chomp.id", source = "chompId")
	VoteChoice toEntity(VoteChoiceUpdateResponseDto choiceDto);
	
	@Mapping(target = "chompId", source = "chomp.id")
	VoteChoiceUpdateResponseDto toUpdateResponseDto(VoteChoice choice);
	
}
