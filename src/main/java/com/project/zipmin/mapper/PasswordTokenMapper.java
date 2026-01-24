package com.project.zipmin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.project.zipmin.dto.PasswordTokenDto;
import com.project.zipmin.entity.PasswordToken;

@Mapper(componentModel = "spring")
public interface PasswordTokenMapper {
	
	@Mapping(target = "user.id", source = "userId")
	PasswordToken toEntity(PasswordTokenDto tokenDto);
	
	@Mapping(target = "userId", source = "user.id")
	PasswordTokenDto toDto(PasswordToken token);
	
}