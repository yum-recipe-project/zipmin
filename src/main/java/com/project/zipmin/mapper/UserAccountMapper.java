package com.project.zipmin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.project.zipmin.dto.UserAccountCreateRequestDto;
import com.project.zipmin.dto.UserAccountReadResponseDto;
import com.project.zipmin.entity.UserAccount;

@Mapper(componentModel = "spring")
public interface UserAccountMapper {
	
	@Mapping(target = "user.id", source = "userId")
	UserAccount toEntity(UserAccountReadResponseDto accountDto);
	
	@Mapping(target = "userId", source = "user.id")
	UserAccountReadResponseDto toReadResponseDto(UserAccount account);
	
	@Mapping(target = "user.id", source = "userId")
	UserAccount toEntity(UserAccountCreateRequestDto accountDto);
	
	
}
