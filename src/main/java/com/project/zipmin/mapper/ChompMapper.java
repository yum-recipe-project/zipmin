package com.project.zipmin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.project.zipmin.dto.chomp.ChompReadResponseDto;
import com.project.zipmin.dto.chomp.EventCreateRequestDto;
import com.project.zipmin.dto.chomp.EventCreateResponseDto;
import com.project.zipmin.dto.chomp.EventReadResponseDto;
import com.project.zipmin.dto.chomp.EventUpdateRequestDto;
import com.project.zipmin.dto.chomp.EventUpdateResponseDto;
import com.project.zipmin.dto.chomp.MegazineCreateRequestDto;
import com.project.zipmin.dto.chomp.MegazineCreateResponseDto;
import com.project.zipmin.dto.chomp.MegazineReadResponseDto;
import com.project.zipmin.dto.chomp.MegazineUpdateRequestDto;
import com.project.zipmin.dto.chomp.MegazineUpdateResponseDto;
import com.project.zipmin.dto.chomp.VoteCreateRequestDto;
import com.project.zipmin.dto.chomp.VoteCreateResponseDto;
import com.project.zipmin.dto.chomp.VoteReadResponseDto;
import com.project.zipmin.dto.chomp.VoteUpdateRequestDto;
import com.project.zipmin.dto.chomp.VoteUpdateResponseDto;
import com.project.zipmin.entity.Chomp;

@Mapper(componentModel = "spring")
public interface ChompMapper {
	
	// Read
	@Mapping(target = "user.id", source = "userId")
	Chomp toEntity(ChompReadResponseDto chompDto);
	
	@Mapping(target = "userId", source = "user.id")
	ChompReadResponseDto toReadResponseDto(Chomp chomp);
	
	// Read (Vote)
	@Mapping(target = "user.id", source = "userId")
	Chomp toEntity(VoteReadResponseDto voteDto);
	
	@Mapping(target = "userId", source = "user.id")
	VoteReadResponseDto toVoteReadResponseDto(Chomp chomp);
	
	// Read (Megazine)
	@Mapping(target = "user.id", source = "userId")
	Chomp toEntity(MegazineReadResponseDto megazineDto);
	
	@Mapping(target = "userId", source = "user.id")
	MegazineReadResponseDto toMegazineReadResponseDto(Chomp chomp);
	
	// Read (Event)
	@Mapping(target = "user.id", source = "userId")
	Chomp toEntity(EventReadResponseDto eventDto);
	
	@Mapping(target = "userId", source = "user.id")
	EventReadResponseDto toEventReadResponseDto(Chomp chomp);
	
	
	
	// Create (Vote)
	@Mapping(target = "user.id", source = "userId")
	Chomp toEntity(VoteCreateRequestDto voteDto);
	
	@Mapping(target = "userId", source = "user.id")
	VoteCreateRequestDto toVoteCreateRequestDto(Chomp chomp);

	@Mapping(target = "user.id", source = "userId")
	Chomp toEntity(VoteCreateResponseDto voteDto);
	
	@Mapping(target = "userId", source = "user.id")
	VoteCreateResponseDto toVoteCreateResponseDto(Chomp chomp);
	
	// Create (Megazine)
	@Mapping(target = "user.id", source = "userId")
	Chomp toEntity(MegazineCreateRequestDto megazineDto);

	@Mapping(target = "userId", source = "user.id")
	MegazineCreateRequestDto toMegazineCreateRequestDto(Chomp chomp);
	
	@Mapping(target = "user.id", source = "userId")
	Chomp toEntity(MegazineCreateResponseDto megazineDto);
	
	@Mapping(target = "userId", source = "user.id")
	MegazineCreateResponseDto toMegazineCreateResponseDto(Chomp chomp);
	
	// Create (Event)
	@Mapping(target = "user.id", source = "userId")
	Chomp toEntity(EventCreateRequestDto eventDto);
	
	@Mapping(target = "userId", source = "user.id")
	EventCreateRequestDto toEventCreateRequestDto(Chomp chomp);
	
	@Mapping(target = "user.id", source = "userId")
	Chomp toEntity(EventCreateResponseDto eventDto);
	
	@Mapping(target = "userId", source = "user.id")
	EventCreateResponseDto toEventCreateResponseDto(Chomp chomp);
	
	
	
	// Update (Vote)
	@Mapping(target = "user.id", source = "userId")
	Chomp toEntity(VoteUpdateRequestDto voteDto);
	
	@Mapping(target = "userId", source = "user.id")
	VoteUpdateRequestDto toVoteUpdateRequestDto(Chomp chomp);
	
	@Mapping(target = "user.id", source = "userId")
	Chomp toEntity(VoteUpdateResponseDto voteDto);
	
	@Mapping(target = "userId", source = "user.id")
	VoteUpdateResponseDto toVoteUpdateResponseDto(Chomp chomp);
	
	// Update (Megazine)
	@Mapping(target = "user.id", source = "userId")
	Chomp toEntity(MegazineUpdateRequestDto megazineDto);
	
	@Mapping(target = "userId", source = "user.id")
	MegazineUpdateRequestDto toMegazineUpdateRequestDto(Chomp chomp);
	
	@Mapping(target = "user.id", source = "userId")
	Chomp toEntity(MegazineUpdateResponseDto megazineDto);
	
	@Mapping(target = "userId", source = "user.id")
	MegazineUpdateResponseDto toMegazineUpdateResponseDto(Chomp chomp);
	
	// Update (Event)
	@Mapping(target = "user.id", source = "userId")
	Chomp toEntity(EventUpdateRequestDto eventDto);
	
	@Mapping(target = "userId", source = "user.id")
	EventUpdateRequestDto toEventUpdateRequestDto(Chomp chomp);
	
	@Mapping(target = "user.id", source = "userId")
	Chomp toEntity(EventUpdateResponseDto eventDto);
	
	@Mapping(target = "userId", source = "user.id")
	EventUpdateResponseDto toEventUpdateResponseDto(Chomp chomp);
	
	
	
	
	
	
}
