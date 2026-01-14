package com.project.zipmin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.project.zipmin.dto.UserAccountCreateRequestDto;
import com.project.zipmin.dto.UserAccountCreateResponseDto;
import com.project.zipmin.dto.UserAccountReadResponseDto;
import com.project.zipmin.dto.UserAccountUpdateRequestDto;
import com.project.zipmin.dto.UserAccountUpdateResponseDto;
import com.project.zipmin.entity.UserAccount;

@Mapper(componentModel = "spring")
public interface UserAccountMapper {
	
	@Mapping(target = "user.id", source = "userId")
	UserAccount toEntity(UserAccountReadResponseDto accountDto);
	
	@Mapping(target = "userId", source = "user.id")
	UserAccountReadResponseDto toReadResponseDto(UserAccount account);
	
	
	
	@Mapping(target = "user.id", source = "userId")
	UserAccount toEntity(UserAccountCreateRequestDto accountDto);
	
	@Mapping(target = "userId", source = "user.id")
	UserAccountCreateRequestDto toCreateRequestDto(UserAccount account);
	
	@Mapping(target = "user.id", source = "userId")
	UserAccount toEntity(UserAccountCreateResponseDto accountDto);
	
	@Mapping(target = "userId", source = "user.id")
	UserAccountCreateResponseDto toCreateResponseDto(UserAccount account);
	
	
		
	@Mapping(target = "user.id", source = "userId")
	UserAccount toEntity(UserAccountUpdateRequestDto accountDto);
	
	@Mapping(target = "userId", source = "user.id")
	UserAccountUpdateRequestDto toUpdateRequestDto(UserAccount account);
	
	@Mapping(target = "user.id", source = "userId")
	UserAccount toEntity(UserAccountUpdateResponseDto accountDto);
	
	@Mapping(target = "userId", source = "user.id")
	UserAccountUpdateResponseDto toUpdateResponseDto(UserAccount account);
	
}
