package com.project.zipmin.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.project.zipmin.dto.VoteReadResponseDto;
import com.project.zipmin.entity.Vote;

@Mapper(componentModel = "spring")
public interface VoteMapper {
	
	// Read
	Vote toEntity(VoteReadResponseDto voteDto);
	VoteReadResponseDto toReadResponseDto(Vote vote);
	
	
	
	
	
}
