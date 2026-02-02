package com.project.zipmin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.project.zipmin.dto.fund.WithdrawCreateRequestDto;
import com.project.zipmin.dto.fund.WithdrawCreateResponseDto;
import com.project.zipmin.dto.fund.WithdrawReadResponseDto;
import com.project.zipmin.dto.fund.WithdrawUpdateRequestDto;
import com.project.zipmin.dto.fund.WithdrawUpdateResponseDto;
import com.project.zipmin.entity.Withdraw;

@Mapper(componentModel = "spring")
public interface WithdrawMapper {
	
	// Read
	@Mapping(target = "user.id", source = "userId")
	@Mapping(target = "account.id", source = "accountId")
	Withdraw toEntity(WithdrawReadResponseDto withdrawDto);
	
	@Mapping(target = "userId", source = "user.id")
	@Mapping(target = "accountId", source = "account.id")
	WithdrawReadResponseDto toReadResponseDto(Withdraw withdraw);
	
	
	
	// Create
	@Mapping(target = "user.id", source = "userId")
	@Mapping(target = "account.id", source = "accountId")
	Withdraw toEntity(WithdrawCreateRequestDto withdrawDto);
	
	@Mapping(target = "userId", source = "user.id")
	@Mapping(target = "accountId", source = "account.id")
	WithdrawCreateRequestDto toCreateRequestDto(Withdraw withdraw);
	
	@Mapping(target = "user.id", source = "userId")
	@Mapping(target = "account.id", source = "accountId")
	@Mapping(target = "admin.id", source = "adminId")
	Withdraw toEntity(WithdrawCreateResponseDto withdrawDto);
	
	@Mapping(target = "userId", source = "user.id")
	@Mapping(target = "accountId", source = "account.id")
	@Mapping(target = "adminId", source = "admin.id")
	WithdrawCreateResponseDto toCreateResponseDto(Withdraw withdraw);
	
	
	
	// Update
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
