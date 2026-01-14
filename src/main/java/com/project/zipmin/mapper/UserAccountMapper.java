package com.project.zipmin.mapper;

import org.mapstruct.Mapper;

import com.project.zipmin.dto.UserAccountCreateRequestDto;
import com.project.zipmin.entity.UserAccount;

@Mapper(componentModel = "spring")
public interface UserAccountMapper {
	
	UserAccount toEntity(UserAccountCreateRequestDto accountRequestDto);
}
