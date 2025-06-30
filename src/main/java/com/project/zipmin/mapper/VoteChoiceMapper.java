package com.project.zipmin.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.project.zipmin.dto.VoteChoiceReadResponseDto;
import com.project.zipmin.entity.VoteChoice;

@Mapper(componentModel = "spring")
public interface VoteChoiceMapper {
	
	// Read
	VoteChoice toEntity(VoteChoiceReadResponseDto choiceDto);
	VoteChoiceReadResponseDto toReadResponseDto(VoteChoice choice);
	List<VoteChoiceReadResponseDto> toReadResponseDtoList(List<VoteChoice> choiceList);

}
