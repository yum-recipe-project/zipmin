package com.project.zipmin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.project.zipmin.dto.VoteCreateRequestDto;
import com.project.zipmin.dto.VoteCreateResponseDto;
import com.project.zipmin.dto.VoteReadResponseDto;
import com.project.zipmin.dto.VoteUpdateRequestDto;
import com.project.zipmin.dto.VoteUpdateResponseDto;
import com.project.zipmin.entity.Vote;

@Mapper(componentModel = "spring")
public interface VoteMapper {
	
	// Create
	@Mapping(target = "chomp.id", source = "chompId")
	Vote toEntity(VoteCreateRequestDto voteDto);
	
	@Mapping(target = "chompId", source = "chomp.id")
	VoteCreateRequestDto toCreateRequestDto(Vote vote);
	
	Vote toEntity(VoteCreateResponseDto voteDto);
	VoteCreateResponseDto toCreateResponseDto(Vote vote);
	
	
	
	// Read
	Vote toEntity(VoteReadResponseDto voteDto);
	VoteReadResponseDto toReadResponseDto(Vote vote);
	
	
	
	// Update
	Vote toEntity(VoteUpdateRequestDto voteDto);
	VoteUpdateRequestDto toUpdateRequestDto(Vote vote);
	
	Vote toEntity(VoteUpdateResponseDto voteDto);
	VoteUpdateResponseDto toUpdateResponseDto(Vote vote);
	
}
