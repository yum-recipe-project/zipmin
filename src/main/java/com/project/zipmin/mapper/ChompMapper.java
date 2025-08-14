package com.project.zipmin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.project.zipmin.dto.ChompCreateRequestDto;
import com.project.zipmin.dto.ChompCreateResponseDto;
import com.project.zipmin.dto.ChompReadResponseDto;
import com.project.zipmin.dto.EventCreateRequestDto;
import com.project.zipmin.dto.EventCreateResponseDto;
import com.project.zipmin.dto.EventReadResponseDto;
import com.project.zipmin.dto.EventUpdateRequestDto;
import com.project.zipmin.dto.EventUpdateResponseDto;
import com.project.zipmin.dto.MegazineCreateRequestDto;
import com.project.zipmin.dto.MegazineCreateResponseDto;
import com.project.zipmin.dto.MegazineReadResponseDto;
import com.project.zipmin.dto.MegazineUpdateResponseDto;
import com.project.zipmin.dto.VoteCreateRequestDto;
import com.project.zipmin.dto.VoteCreateResponseDto;
import com.project.zipmin.dto.VoteReadResponseDto;
import com.project.zipmin.dto.VoteUpdateRequestDto;
import com.project.zipmin.dto.VoteUpdateResponseDto;
import com.project.zipmin.entity.Chomp;

@Mapper(componentModel = "spring")
public interface ChompMapper {
	
	// Create
//	@Mapping(target = "id", ignore = true)
//	Chomp toEntity(ChompCreateRequestDto chompDto);
//	
//	ChompCreateRequestDto toCreateRequestDto(Chomp chomp);
//	
//	Chomp toEntity(ChompCreateResponseDto chompDto);
//	
//	ChompCreateResponseDto toCreateResponseDto(Chomp chomp);
	
	Chomp toEntity(MegazineCreateRequestDto megazineDto);
	MegazineCreateRequestDto toMegazineCreateRequestDto(Chomp chomp);
	
	Chomp toEntity(MegazineCreateResponseDto megazineDto);
	MegazineCreateResponseDto toMegazineCreateResponseDto(Chomp chomp);
	
	Chomp toEntity(EventCreateRequestDto eventDto);
	EventCreateRequestDto toEventCreateRequestDto(Chomp chomp);
	
	Chomp toEntity(EventCreateResponseDto eventDto);
	EventCreateResponseDto toEventCreateResponseDto(Chomp chomp);
	
	Chomp toEntity(VoteCreateRequestDto voteDto);
	VoteCreateRequestDto toVoteCreateRequestDto(Chomp chomp);
	
	Chomp toEntity(VoteCreateResponseDto voteDto);
	VoteCreateResponseDto toVoteCreateResponseDto(Chomp chomp);

	
	
	
	
	// Read
	Chomp toEntity(ChompReadResponseDto chompDto);
	ChompReadResponseDto toReadResponseDto(Chomp chomp);
	
	
	MegazineReadResponseDto toMegazineReadResponseDto(Chomp chomp);
	
	EventReadResponseDto toEventReadResponseDto(Chomp chomp);
	
	VoteReadResponseDto toVoteReadResponseDto(Chomp chomp);
	
	
	
	
	// Update
	MegazineUpdateResponseDto toMegazineUpdateResponseDto(Chomp chomp);
	
	
	Chomp toEntity(EventUpdateRequestDto eventDto);
	EventUpdateRequestDto toEventUpdateRequestDto(Chomp chomp);
	
	Chomp toEntity(EventUpdateResponseDto eventDto);
	EventUpdateResponseDto toEventUpdateResponseDto(Chomp chomp);
	
	
	
	Chomp toEntity(VoteUpdateRequestDto voteDto);
	VoteUpdateRequestDto toVoteUpdateRequestDto(Chomp chomp);
	
	Chomp toEntity(VoteUpdateResponseDto voteDto);
	VoteUpdateResponseDto toVoteUpdateResponseDto(Chomp chomp);
	
	
	
}
