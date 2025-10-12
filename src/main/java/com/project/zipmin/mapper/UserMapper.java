package com.project.zipmin.mapper;

import org.mapstruct.Mapper;

import com.project.zipmin.dto.UserAccountReadResponseDto;
import com.project.zipmin.dto.UserCreateRequestDto;
import com.project.zipmin.dto.UserCreateResponseDto;
import com.project.zipmin.dto.UserPointReadResponseDto;
import com.project.zipmin.dto.UserProfileReadResponseDto;
import com.project.zipmin.dto.UserReadResponseDto;
import com.project.zipmin.dto.UserUpdateRequestDto;
import com.project.zipmin.dto.UserUpdateResponseDto;
import com.project.zipmin.entity.User;
import com.project.zipmin.entity.UserAccount;

@Mapper(componentModel = "spring")
public interface UserMapper {
	
	// Read
	User toEntity(UserReadResponseDto userDto);
	UserReadResponseDto toReadResponseDto(User user);
	
	User toEntity(UserProfileReadResponseDto userDto);
	UserProfileReadResponseDto toReadProfileResponseDto(User user);
	
	UserPointReadResponseDto toReadPointResponseDto(User user);
	UserAccountReadResponseDto toReadAccountResponseDto(UserAccount account);
	
	
	
	
	// Create
	User toEntity(UserCreateRequestDto userDto);
	UserCreateRequestDto toCreateRequestDto(User user);
	
	User toEntity (UserCreateResponseDto userDto);
	UserCreateResponseDto toCreateResponseDto(User user);
	
	
	
	// Update
	User toEntity(UserUpdateRequestDto userDto);
	UserUpdateRequestDto toUpdateRequestDto(User user);
	
	User toEntity(UserUpdateResponseDto userDto);
	UserUpdateResponseDto toUpdateResponseDto(User user);
	
}
