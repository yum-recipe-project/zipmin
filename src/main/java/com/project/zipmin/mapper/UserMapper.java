package com.project.zipmin.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.project.zipmin.dto.UserJoinRequestDto;
import com.project.zipmin.dto.UserResponseDto;
import com.project.zipmin.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
	User toEntity(UserJoinRequestDto userDto);
	UserJoinRequestDto toRequestDto(User user);
	List<UserJoinRequestDto> toRequestDtoList(List<User> users);
	
	User toEntity(UserResponseDto userDto);
	UserResponseDto toResponseDto(User user);
	List<UserResponseDto> toResponseDtoList(List<User> users);
}
