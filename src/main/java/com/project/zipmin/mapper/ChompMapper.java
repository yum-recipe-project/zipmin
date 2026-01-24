package com.project.zipmin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.project.zipmin.dto.chomp.ChompReadResponseDto;
import com.project.zipmin.dto.chomp.VoteCreateRequestDto;
import com.project.zipmin.dto.chomp.VoteCreateResponseDto;
import com.project.zipmin.dto.chomp.VoteReadResponseDto;
import com.project.zipmin.dto.EventCreateRequestDto;
import com.project.zipmin.dto.EventCreateResponseDto;
import com.project.zipmin.dto.EventReadResponseDto;
import com.project.zipmin.dto.EventUpdateRequestDto;
import com.project.zipmin.dto.EventUpdateResponseDto;
import com.project.zipmin.dto.MegazineCreateRequestDto;
import com.project.zipmin.dto.MegazineCreateResponseDto;
import com.project.zipmin.dto.MegazineReadResponseDto;
import com.project.zipmin.dto.MegazineUpdateResponseDto;
import com.project.zipmin.dto.VoteUpdateRequestDto;
import com.project.zipmin.dto.VoteUpdateResponseDto;
import com.project.zipmin.entity.Chomp;

@Mapper(componentModel = "spring")
public interface ChompMapper {
	
	// Read
	@Mapping(target = "user.id", source = "userId")
	Chomp toEntity(ChompReadResponseDto chompDto);
	
	@Mapping(target = "userId", source = "user.id")
	ChompReadResponseDto toReadResponseDto(Chomp chomp);
	
	@Mapping(target = "user.id", source = "userId")
	Chomp toEntity(VoteReadResponseDto voteDto);
	
	@Mapping(target = "userId", source = "user.id")
	VoteReadResponseDto toVoteReadResponseDto(Chomp chomp);
	
	
	
	
	@Mapping(target = "user.id", source = "userId")
	Chomp toEntity(MegazineReadResponseDto megazineDto);
	
	@Mapping(target = "userId", source = "user.id")
	MegazineReadResponseDto toMegazineReadResponseDto(Chomp chomp);
	
	@Mapping(target = "user.id", source = "userId")
	Chomp toEntity(EventReadResponseDto eventDto);
	
	@Mapping(target = "userId", source = "user.id")
	EventReadResponseDto toEventReadResponseDto(Chomp chomp);
	
	
	// Create
	@Mapping(target = "user.id", source = "userId")
	Chomp toEntity(VoteCreateRequestDto voteDto);
	
	@Mapping(target = "userId", source = "user.id")
	VoteCreateRequestDto toVoteCreateRequestDto(Chomp chomp);

	
	
	
	
	@Mapping(target = "user.id", source = "userId")
	Chomp toEntity(MegazineCreateRequestDto megazineDto);

	@Mapping(target = "userId", source = "user.id")
	MegazineCreateRequestDto toMegazineCreateRequestDto(Chomp chomp);
	
	@Mapping(target = "user.id", source = "userId")
	Chomp toEntity(MegazineCreateResponseDto megazineDto);
	
	@Mapping(target = "userId", source = "user.id")
	MegazineCreateResponseDto toMegazineCreateResponseDto(Chomp chomp);
	
	
	@Mapping(target = "user.id", source = "userId")
	Chomp toEntity(VoteCreateResponseDto voteDto);
	
	@Mapping(target = "userId", source = "user.id")
	VoteCreateResponseDto toVoteCreateResponseDto(Chomp chomp);

	@Mapping(target = "user.id", source = "userId")
	Chomp toEntity(EventCreateRequestDto eventDto);
	
	@Mapping(target = "userId", source = "user.id")
	EventCreateRequestDto toEventCreateRequestDto(Chomp chomp);
	
	@Mapping(target = "user.id", source = "userId")
	Chomp toEntity(EventCreateResponseDto eventDto);
	
	@Mapping(target = "userId", source = "user.id")
	EventCreateResponseDto toEventCreateResponseDto(Chomp chomp);
	
	
	
	
	
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
