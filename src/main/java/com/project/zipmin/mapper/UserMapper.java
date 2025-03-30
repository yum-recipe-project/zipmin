package com.project.zipmin.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.project.zipmin.dto.UserDTO;
import com.project.zipmin.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
	UserDTO userToUserDTO(User user);
	User userDTOToUser(UserDTO userDTO);
	List<UserDTO> userListToUserDTOList(List<User> userList);
}
