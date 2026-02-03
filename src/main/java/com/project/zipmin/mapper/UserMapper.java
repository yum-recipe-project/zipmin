package com.project.zipmin.mapper;

import org.mapstruct.Mapper;

import com.project.zipmin.dto.user.UserCreateRequestDto;
import com.project.zipmin.dto.user.UserCreateResponseDto;
import com.project.zipmin.dto.user.UserProfileReadResponseDto;
import com.project.zipmin.dto.user.UserReadResponseDto;
import com.project.zipmin.dto.user.UserUpdateRequestDto;
import com.project.zipmin.dto.user.UserUpdateResponseDto;
import com.project.zipmin.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
	
	// Read
	User toEntity(UserReadResponseDto userDto);
	UserReadResponseDto toReadResponseDto(User user);
	
	User toEntity(UserProfileReadResponseDto userDto);
	UserProfileReadResponseDto toReadProfileResponseDto(User user);
	
	
	
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
