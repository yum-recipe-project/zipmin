package com.project.zipmin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.project.zipmin.dto.VoteChoiceCreateRequestDto;
import com.project.zipmin.dto.VoteChoiceReadResponseDto;
import com.project.zipmin.dto.VoteChoiceUpdateRequestDto;
import com.project.zipmin.dto.VoteChoiceUpdateResponseDto;
import com.project.zipmin.entity.VoteChoice;

@Mapper(componentModel = "spring")
public interface VoteChoiceMapper {
	
	// Create
	@Mapping(target = "vote.id", source = "voteId")
	VoteChoice toEntity(VoteChoiceCreateRequestDto choiceDto);
	
	@Mapping(target = "voteId", source = "vote.id")
	VoteChoiceCreateRequestDto toCreateRequestDto(VoteChoice choice);
	
	
	
	// Read
	VoteChoice toEntity(VoteChoiceReadResponseDto choiceDto);
	
	@Mapping(target = "count", ignore = true)
	@Mapping(target = "rate", ignore = true)
	VoteChoiceReadResponseDto toReadResponseDto(VoteChoice choice);
	
	
	
	// Update
	VoteChoice toEntity(VoteChoiceUpdateRequestDto choiceDto);
	VoteChoiceUpdateRequestDto toUpdateRequestDto(VoteChoice choice);
	
	@Mapping(target = "vote.id", source = "voteId")
	VoteChoice toEntity(VoteChoiceUpdateResponseDto choiceDto);
	
	@Mapping(target = "voteId", source = "vote.id")
	VoteChoiceUpdateResponseDto toUpdateResponseDto(VoteChoice choice);
	
}
