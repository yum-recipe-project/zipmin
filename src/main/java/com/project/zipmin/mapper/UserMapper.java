package com.project.zipmin.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.project.zipmin.dto.UserRequestDto;
import com.project.zipmin.dto.UserResponseDto;
import com.project.zipmin.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
	User toEntity(UserRequestDto userDto);
	UserRequestDto toRequestDto(User user);
	List<UserRequestDto> toRequestDtoList(List<User> users);
	
	User toEntity(UserResponseDto userDto);
	UserResponseDto toResponseDto(User user);
	List<UserResponseDto> toResponseDtoList(List<User> users);
}
