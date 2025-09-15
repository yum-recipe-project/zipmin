package com.project.zipmin.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.project.zipmin.dto.VoteRecordCreateRequestDto;
import com.project.zipmin.dto.VoteRecordCreateResponseDto;
import com.project.zipmin.dto.VoteRecordReadResponseDto;
import com.project.zipmin.entity.VoteRecord;

@Mapper(componentModel = "spring")
public interface VoteRecordMapper {
	
	// Create
	@Mapping(target = "chomp.id", source = "chompId")
	@Mapping(target = "user.id", source = "userId")
	@Mapping(target = "choice.id", source = "choiceId")
	VoteRecord toEntity(VoteRecordCreateRequestDto recordDto);
	
	@Mapping(target = "chompId", source = "chomp.id")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "choiceId", source = "choice.id")
	VoteRecordCreateRequestDto toCreateRequestDto(VoteRecord record);
	
	@Mapping(target = "chomp.id", source = "chompId")
	@Mapping(target = "user.id", source = "userId")
	@Mapping(target = "choice.id", source = "choiceId")
	VoteRecord toEntity(VoteRecordCreateResponseDto recordDto);
	
	@Mapping(target = "chompId", source = "chomp.id")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "choiceId", source = "choice.id")
	VoteRecordCreateResponseDto toCreateResponseDto(VoteRecord record);
	
	// Read
	VoteRecord toEntity(VoteRecordReadResponseDto recordDto);
	VoteRecordReadResponseDto toReadResponseDto(VoteRecord record);
	
}
