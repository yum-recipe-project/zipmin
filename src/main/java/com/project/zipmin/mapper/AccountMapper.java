package com.project.zipmin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.project.zipmin.dto.fund.AccountCreateRequestDto;
import com.project.zipmin.dto.fund.AccountCreateResponseDto;
import com.project.zipmin.dto.fund.AccountReadResponseDto;
import com.project.zipmin.dto.fund.AccountUpdateRequestDto;
import com.project.zipmin.dto.fund.AccountUpdateResponseDto;
import com.project.zipmin.entity.Account;

@Mapper(componentModel = "spring")
public interface AccountMapper {
	
	@Mapping(target = "user.id", source = "userId")
	Account toEntity(AccountReadResponseDto accountDto);
	
	@Mapping(target = "userId", source = "user.id")
	AccountReadResponseDto toReadResponseDto(Account account);
	
	
	
	@Mapping(target = "user.id", source = "userId")
	Account toEntity(AccountCreateRequestDto accountDto);
	
	@Mapping(target = "userId", source = "user.id")
	AccountCreateRequestDto toCreateRequestDto(Account account);
	
	@Mapping(target = "user.id", source = "userId")
	Account toEntity(AccountCreateResponseDto accountDto);
	
	@Mapping(target = "userId", source = "user.id")
	AccountCreateResponseDto toCreateResponseDto(Account account);
	
	
		
	@Mapping(target = "user.id", source = "userId")
	Account toEntity(AccountUpdateRequestDto accountDto);
	
	@Mapping(target = "userId", source = "user.id")
	AccountUpdateRequestDto toUpdateRequestDto(Account account);
	
	@Mapping(target = "user.id", source = "userId")
	Account toEntity(AccountUpdateResponseDto accountDto);
	
	@Mapping(target = "userId", source = "user.id")
	AccountUpdateResponseDto toUpdateResponseDto(Account account);
	
}
