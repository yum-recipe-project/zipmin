package com.project.zipmin.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.project.zipmin.dto.UserJoinDto;
import com.project.zipmin.dto.UserResponseDto;
import com.project.zipmin.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
	User toEntity(UserJoinDto userDto);
	UserJoinDto toRequestDto(User user);
	List<UserJoinDto> toRequestDtoList(List<User> users);
	
	User toEntity(UserResponseDto userDto);
	UserResponseDto toResponseDto(User user);
	List<UserResponseDto> toResponseDtoList(List<User> users);
}
