package com.project.zipmin.mapper;

import org.mapstruct.Mapper;

import com.project.zipmin.dto.WithdrawReadResponseDto;
import com.project.zipmin.entity.Withdraw;

@Mapper(componentModel = "spring")
public interface WithdrawMapper {
	
	WithdrawReadResponseDto toReadResponseDto(Withdraw withdraw);
}
