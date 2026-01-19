package com.project.zipmin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.project.zipmin.dto.WithdrawCreateRequestDto;
import com.project.zipmin.dto.WithdrawReadResponseDto;
import com.project.zipmin.dto.WithdrawUpdateRequestDto;
import com.project.zipmin.dto.WithdrawUpdateResponseDto;
import com.project.zipmin.entity.Withdraw;

@Mapper(componentModel = "spring")
public interface WithdrawMapper {
	
	@Mapping(target = "user.id", source = "userId")
	@Mapping(target = "account.id", source = "accountId")
	Withdraw toEntity(WithdrawReadResponseDto withdrawDto);
	
	@Mapping(target = "userId", source = "user.id")
	@Mapping(target = "accountId", source = "account.id")
	WithdrawReadResponseDto toReadResponseDto(Withdraw withdraw);
	
	
	
	@Mapping(target = "user.id", source = "userId")
	@Mapping(target = "account.id", source = "accountId")
	Withdraw toEntity(WithdrawCreateRequestDto withdrawDto);
	
	@Mapping(target = "userId", source = "user.id")
	@Mapping(target = "accountId", source = "account.id")
	WithdrawCreateRequestDto toCreateRequestDto(Withdraw withdraw);
	
	
	
	@Mapping(target = "user.id", source = "userId")
	@Mapping(target = "account.id", source = "accountId")
	@Mapping(target = "admin.id", source = "adminId")
	Withdraw toEntity(WithdrawUpdateRequestDto withdrawDto);
	
	@Mapping(target = "userId", source = "user.id")
	@Mapping(target = "accountId", source = "account.id")
	@Mapping(target = "adminId", source = "admin.id")
	WithdrawUpdateRequestDto toUpdateRequestDto(Withdraw withdraw);
	
	@Mapping(target = "user.id", source = "userId")
	@Mapping(target = "account.id", source = "accountId")
	@Mapping(target = "admin.id", source = "adminId")
	Withdraw toEntity(WithdrawUpdateResponseDto withdrawDto);
	
	@Mapping(target = "userId", source = "user.id")
	@Mapping(target = "accountId", source = "account.id")
	@Mapping(target = "adminId", source = "admin.id")
	WithdrawUpdateResponseDto toUpdateResponseDto(Withdraw withdraw);
	
	
}
