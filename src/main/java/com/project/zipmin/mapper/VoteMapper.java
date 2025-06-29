package com.project.zipmin.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.project.zipmin.dto.VoteReadResponseDto;
import com.project.zipmin.entity.ChompVote;

@Mapper(componentModel = "spring")
public interface VoteMapper {
	
	// Read
	ChompVote toEntity(VoteReadResponseDto voteDto);
	VoteReadResponseDto toReadResponseDto(ChompVote vote);
	
	
	
	
	
}
