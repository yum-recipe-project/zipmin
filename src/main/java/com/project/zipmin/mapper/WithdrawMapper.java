package com.project.zipmin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.project.zipmin.dto.WithdrawCreateRequestDto;
import com.project.zipmin.dto.WithdrawReadResponseDto;
import com.project.zipmin.entity.Withdraw;

@Mapper(componentModel = "spring")
public interface WithdrawMapper {
	
	@Mapping(target = "user.id", source = "userId")
	Withdraw toEntity(WithdrawReadResponseDto withdrawDto);
	
	@Mapping(target = "userId", source = "user.id")
	WithdrawReadResponseDto toReadResponseDto(Withdraw withdraw);
	
	
	
	@Mapping(target = "user.id", source = "userId")
	Withdraw toEntity(WithdrawCreateRequestDto withdrawDto);
	
	@Mapping(target = "userId", source = "user.id")
	WithdrawCreateRequestDto toCreateRequestDto(Withdraw withdraw);
	
	
	
	
}
