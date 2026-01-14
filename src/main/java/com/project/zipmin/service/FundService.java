package com.project.zipmin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.zipmin.api.ApiException;
import com.project.zipmin.api.FundErrorCode;
import com.project.zipmin.api.UserAccountErrorCode;
import com.project.zipmin.dto.FundCreateRequestDto;
import com.project.zipmin.dto.FundCreateResponseDto;
import com.project.zipmin.dto.FundReadResponseDto;
import com.project.zipmin.dto.UserAccountCreateRequestDto;
import com.project.zipmin.dto.UserAccountCreateResponseDto;
import com.project.zipmin.dto.UserAccountReadResponseDto;
import com.project.zipmin.dto.UserAccountUpdateRequestDto;
import com.project.zipmin.dto.UserReadResponseDto;
import com.project.zipmin.entity.Fund;
import com.project.zipmin.entity.Role;
import com.project.zipmin.entity.UserAccount;
import com.project.zipmin.mapper.FundMapper;
import com.project.zipmin.mapper.UserAccountMapper;
import com.project.zipmin.mapper.UserMapper;
import com.project.zipmin.repository.FundRepository;
import com.project.zipmin.repository.UserAccountRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class FundService {
	
	private final FundRepository fundRepository;
	private final UserAccountRepository accountRepository;
	
	private final FundMapper fundMapper;
	private final UserAccountMapper accountMapper;
	private final UserMapper userMapper;
	
	private final UserService userService;
	
	
	
	
	
	// 후원 작성
	public FundCreateResponseDto createFund(FundCreateRequestDto fundRequestDto) {

		// 입력값 검증
		if (fundRequestDto == null
				|| fundRequestDto.getFundeeId() == null || fundRequestDto.getFundeeId() == null
				|| fundRequestDto.getRecipeId() == null || fundRequestDto.getPoint() == null) {
			throw new ApiException(FundErrorCode.FUND_INVALID_INPUT);
		}
		
		// 사용자 조회
		UserReadResponseDto funderDto = userService.readUserById(fundRequestDto.getFunderId());
		UserReadResponseDto fundeeDto = userService.readUserById(fundRequestDto.getFundeeId());
		
		// 입력값 검증
		if (funderDto.getPoint() < fundRequestDto.getPoint()) {
			throw new ApiException(FundErrorCode.FUND_INVALID_POINT);
		}
		
		// 후원 저장
		Fund fund = fundMapper.toEntity(fundRequestDto);
		try {
			fund = fundRepository.save(fund);
			
			// 사용자 갱신
			funderDto.setPoint(funderDto.getPoint() - fundRequestDto.getPoint());
			fundeeDto.setRevenue(fundeeDto.getRevenue() + fundRequestDto.getPoint());
			userService.updateUser(userMapper.toUpdateRequestDto(userMapper.toEntity(funderDto)));
			userService.updateUser(userMapper.toUpdateRequestDto(userMapper.toEntity(fundeeDto)));
			
			return fundMapper.toFundCreateResponseDto(fund);
		}
		catch (Exception e) {
			throw new ApiException(FundErrorCode.FUND_CREATE_FAIL);
		}
	}
	
	
	// 계좌 상세 조회
	public UserAccountReadResponseDto readAccountById(Integer id) {

		// 입력값 검증
		if (id == null) {
			throw new ApiException(UserAccountErrorCode.USER_ACCOUNT_INVALID_INPUT);
		}
		
		// 계좌 조회
		UserAccount userAccount = accountRepository.findById(id)
				.orElseThrow(() -> new ApiException(UserAccountErrorCode.USER_ACCOUNT_NOT_FOUND));

		return accountMapper.toReadResponseDto(userAccount);
	}
	
	
	
	
	
	// 계좌 작성
	public UserAccountCreateResponseDto createAccount(UserAccountCreateRequestDto accountRequestDto) {
		
		// 입력값 검증
		if (accountRequestDto == null 
				|| accountRequestDto.getBank() == null 
				|| accountRequestDto.getAccountnum() == null
				|| accountRequestDto.getName() == null
				|| accountRequestDto.getUserId() == 0) {
			throw new ApiException(UserAccountErrorCode.USER_ACCOUNT_INVALID_INPUT);
		}
		
		// 계좌 저장
		UserAccount userAccount = accountMapper.toEntity(accountRequestDto);
		try {
			userAccount = accountRepository.save(userAccount);
			return accountMapper.toCreateResponseDto(userAccount);
		}
		catch (Exception e) {
			throw new ApiException(UserAccountErrorCode.USER_ACCOUNT_CREATE_FAIL);
		}
	}
	
	
	
	
	// 계좌 수정
	public UserAccountCreateResponseDto updateAccount(UserAccountUpdateRequestDto accountRequestDto) {

		// 입력값 검증
		if (accountRequestDto == null 
				|| accountRequestDto.getUserId() == 0
				|| accountRequestDto.getBank() == null
				|| accountRequestDto.getAccountnum() == null
				|| accountRequestDto.getName() == null) {
			throw new ApiException(UserAccountErrorCode.USER_ACCOUNT_INVALID_INPUT);
		}
		
		// 계좌 조회
		UserAccount account = accountRepository.findById(accountRequestDto.getUserId())
				.orElseThrow(() -> new ApiException(UserAccountErrorCode.USER_ACCOUNT_NOT_FOUND));

		// 권한 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserReadResponseDto userDto = userService.readUserByUsername(username);
		if (!userDto.getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			if (userDto.getRole().equals(Role.ROLE_ADMIN.name())) {
				if (account.getUser().getRole().equals(Role.ROLE_SUPER_ADMIN)) {
					throw new ApiException(UserAccountErrorCode.USER_ACCOUNT_FORBIDDEN);
				}
				else if (account.getUser().getRole().equals(Role.ROLE_ADMIN)) {
					if (userDto.getId() != account.getUser().getId()) {
						throw new ApiException(UserAccountErrorCode.USER_ACCOUNT_FORBIDDEN);
					}
				}
			}
			else {
				if (userDto.getId() != account.getUser().getId()) {
					throw new ApiException(UserAccountErrorCode.USER_ACCOUNT_FORBIDDEN);
				}
			}
		}
		
		// 변경 값 설정
		account.setBank(accountRequestDto.getBank());
		account.setAccountnum(accountRequestDto.getAccountnum());
		account.setName(accountRequestDto.getName());

		try {
			account = accountRepository.save(account);
			return accountMapper.toCreateResponseDto(account);
		}
		catch (Exception e) {
			throw new ApiException(UserAccountErrorCode.USER_ACCOUNT_UPDATE_FAIL);
		}
	}
	
	
	
	
	
	// 사용자의 후원 목록 조회
	public Page<FundReadResponseDto> readFundPageByUserId(Integer userId, Pageable pageable) {

		// 입력값 검증
		if (userId == null || pageable == null) {
			throw new ApiException(FundErrorCode.FUND_INVALID_INPUT);
		}
		
		// 권한 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserReadResponseDto userDto = userService.readUserByUsername(username);
		if (!userDto.getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			if (userDto.getRole().equals(Role.ROLE_ADMIN.name())) {
				if (userService.readUserById(userId).getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
					throw new ApiException(FundErrorCode.FUND_FORBIDDEN);
				}
				if (userService.readUserById(userId).getRole().equals(Role.ROLE_ADMIN.name())) {
					if (userDto.getId() != userId) {
						throw new ApiException(FundErrorCode.FUND_FORBIDDEN);
					}
				}
			}
			else {
				if (userDto.getId() != userId) {
					throw new ApiException(FundErrorCode.FUND_FORBIDDEN);
				}
			}
		}
		
		// 후원 목록 조회
 		Page<Fund> fundPage;
 		try {
 			fundPage = fundRepository.findAllByFundeeId(userId, pageable);
 		}
 		catch (Exception e) {
 			throw new ApiException(FundErrorCode.FUND_READ_LIST_FAIL);
 		}

 		// 후원 목록 응답 구성
 		List<FundReadResponseDto> fundDtoList = new ArrayList<FundReadResponseDto>();
 		for (Fund fund : fundPage) {
 			FundReadResponseDto fundDto = fundMapper.toFundReadResponseDto(fund);
 			
 			fundDto.setNickname(fund.getFunder().getNickname());
			fundDto.setTitle(fund.getRecipe().getTitle());

			fundDtoList.add(fundDto);
		}
 		
		return new PageImpl<>(fundDtoList, pageable, fundPage.getTotalElements());
	}
	

	
	
	
	// 사용자의 후원 총합 조회
	public int sumFundByUserId(Integer userId) {
		
		// 입력값 검증
		if (userId == null) {
			throw new ApiException(FundErrorCode.FUND_INVALID_INPUT);
		}
		
		// 권한 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserReadResponseDto userDto = userService.readUserByUsername(username);
		if (!userDto.getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			if (userDto.getRole().equals(Role.ROLE_ADMIN.name())) {
				if (userService.readUserById(userId).getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
					throw new ApiException(FundErrorCode.FUND_FORBIDDEN);
				}
				if (userService.readUserById(userId).getRole().equals(Role.ROLE_ADMIN.name())) {
					if (userDto.getId() != userId) {
						throw new ApiException(FundErrorCode.FUND_FORBIDDEN);
					}
				}
			}
			else {
				if (userDto.getId() != userId) {
					throw new ApiException(FundErrorCode.FUND_FORBIDDEN);
				}
			}
		}
		
		// 후원 총합 조회
		try {
			 return fundRepository.sumPointByFundeeId(userId);
		}
		catch (Exception e) {
			throw new ApiException(FundErrorCode.FUND_READ_SUM_FAIL);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}
