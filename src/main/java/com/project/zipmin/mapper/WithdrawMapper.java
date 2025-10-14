package com.project.zipmin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.project.zipmin.dto.WithdrawReadResponseDto;
import com.project.zipmin.entity.Withdraw;

@Mapper(componentModel = "spring")
public interface WithdrawMapper {
	
//	WithdrawReadResponseDto toReadResponseDto(Withdraw withdraw);
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "username", source = "user.username")
    WithdrawReadResponseDto toReadResponseDto(Withdraw withdraw);
	
}
