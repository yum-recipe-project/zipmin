package com.project.zipmin.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.project.zipmin.dto.UserRequestDto;
import com.project.zipmin.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
	User toEntity(UserRequestDto userDto);
	UserRequestDto toRequestDto(User user);
	List<UserRequestDto> toRequestDtoList(List<User> users);
	
//	UserResponseDTO userToUserResponseDTO(User user);
//	User userResponseDTOToUser(UserResponseDTO userDTO);
//	List<UserResponseDTO> userListToUserResponseDTOList(List<User> userList);
}
