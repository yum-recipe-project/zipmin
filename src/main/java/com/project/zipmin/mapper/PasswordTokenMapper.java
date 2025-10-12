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
import com.project.zipmin.dto.PasswordTokenDto;
import com.project.zipmin.dto.VoteCreateRequestDto;
import com.project.zipmin.dto.VoteCreateResponseDto;
import com.project.zipmin.dto.VoteReadResponseDto;
import com.project.zipmin.dto.VoteUpdateRequestDto;
import com.project.zipmin.dto.VoteUpdateResponseDto;
import com.project.zipmin.entity.Chomp;
import com.project.zipmin.entity.PasswordToken;

@Mapper(componentModel = "spring")
public interface PasswordTokenMapper {
	
	@Mapping(target = "user.id", source = "userId")
	PasswordToken toEntity(PasswordTokenDto tokenDto);
	
	@Mapping(target = "userId", source = "user.id")
	PasswordTokenDto toDto(PasswordToken token);
	
}