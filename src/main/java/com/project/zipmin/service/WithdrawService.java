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
import com.project.zipmin.api.UserErrorCode;
import com.project.zipmin.api.WithdrawErrorCode;
import com.project.zipmin.dto.UserAccountReadResponseDto;
import com.project.zipmin.dto.UserDto;
import com.project.zipmin.dto.UserReadResponseDto;
import com.project.zipmin.dto.WithdrawCreateRequestDto;
import com.project.zipmin.dto.WithdrawReadResponseDto;
import com.project.zipmin.entity.Role;
import com.project.zipmin.entity.User;
import com.project.zipmin.entity.UserAccount;
import com.project.zipmin.entity.Withdraw;
import com.project.zipmin.mapper.UserMapper;
import com.project.zipmin.mapper.WithdrawMapper;
import com.project.zipmin.repository.UserAccountRepository;
import com.project.zipmin.repository.UserRepository;
import com.project.zipmin.repository.WithdrawRepository;

import lombok.RequiredArgsConstructor;
	
@Service
@Transactional
@RequiredArgsConstructor
public class WithdrawService {
	
	private final WithdrawRepository withdrawRepository;
	
	private final UserMapper userMapper;
	private final WithdrawMapper withdrawMapper;
	
	private final UserService userService;
	
	
	
	
	
	// 출금 목록 조회 (관리자)
	public Page<WithdrawReadResponseDto> readAdminWithdrawPage(Pageable pageable) {
		
		// 입력값 검증
		if (pageable == null) {
			throw new ApiException(WithdrawErrorCode.WITHDRAW_INVALID_INPUT);
		}
		
		// TODO : 정렬 문자열을 객체로 변환

		// 출금 목록 조회
		Page<Withdraw> withdrawPage = null;
		try {
			withdrawPage = withdrawRepository.findAll(pageable);
		}
		catch (Exception e) {
			throw new ApiException(WithdrawErrorCode.WITHDRAW_READ_LIST_FAIL);
		}

		// 출금 목록 응답 구성
		List<WithdrawReadResponseDto> withdrawDtoList = new ArrayList<>();
		for (Withdraw withdraw : withdrawPage) {
			WithdrawReadResponseDto dto = withdrawMapper.toReadResponseDto(withdraw);
			withdrawDtoList.add(dto);
		}

		return new PageImpl<>(withdrawDtoList, pageable, withdrawPage.getTotalElements());
	}
	
	
	
	
	
	// 사용자의 출금 목록 조회
	public Page<WithdrawReadResponseDto> readWithdrawPageByUserId(int userId, Pageable pageable) {

		// 입력값 검증
		if (userId == 0 || pageable == null) {
			throw new ApiException(WithdrawErrorCode.WITHDRAW_INVALID_INPUT);
		}
		
		// 권한 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserReadResponseDto userDto = userService.readUserByUsername(username);
		if (!userDto.getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			if (userDto.getRole().equals(Role.ROLE_ADMIN.name())) {
				if (userService.readUserById(userId).getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
					throw new ApiException(WithdrawErrorCode.WITHDRAW_FORBIDDEN);
				}
				if (userService.readUserById(userId).getRole().equals(Role.ROLE_ADMIN.name())) {
					if (userDto.getId() != userId) {
						throw new ApiException(WithdrawErrorCode.WITHDRAW_FORBIDDEN);
					}
				}
			}
			else {
				if (userDto.getId() != userId) {
					throw new ApiException(WithdrawErrorCode.WITHDRAW_FORBIDDEN);
				}
			}
		}
		
		// 출금 목록 조회
		Page<Withdraw> withdrawPage;
		try {
			withdrawPage = withdrawRepository.findByUserId(userId, pageable);
		}
		catch (Exception e) {
			throw new ApiException(WithdrawErrorCode.WITHDRAW_READ_LIST_FAIL);
		}

		// 출금 목록 응답 구성
		List<WithdrawReadResponseDto> withdrawDtoList = new ArrayList<>();
		for (Withdraw withdraw : withdrawPage) {
			WithdrawReadResponseDto withdrawDto = withdrawMapper.toReadResponseDto(withdraw);
			withdrawDtoList.add(withdrawDto);
		}

		return new PageImpl<>(withdrawDtoList, pageable, withdrawPage.getTotalElements());
	}

	
	
	
	
	// 포인트 출금 신청
	public WithdrawReadResponseDto createWithdraw(WithdrawCreateRequestDto withdrawRequestDto) {

		// 입력값 검증
		if (withdrawRequestDto == null
				|| withdrawRequestDto.getUserId() == 0
				|| withdrawRequestDto.getPoint() == 0) {
			throw new ApiException(WithdrawErrorCode.WITHDRAW_INVALID_INPUT);
		}
		
		// 사용자 조회
		UserReadResponseDto userDto = userService.readUserById(withdrawRequestDto.getUserId());
		
		// TODO : 권한 확인
		
		// TODO : AccountId 설정
		
		
		// 출금 저장
		Withdraw withdraw = withdrawMapper.toEntity(withdrawRequestDto);
		try {
			withdraw = withdrawRepository.save(withdraw);
			
			// 사용자 갱신
			userDto.setRevenue(userDto.getRevenue() - withdrawRequestDto.getPoint());
			userService.updateUser(userMapper.toUpdateRequestDto(userMapper.toEntity(userDto)));

			return withdrawMapper.toReadResponseDto(withdraw);
		}
		catch (Exception e) {
			throw new ApiException(WithdrawErrorCode.WITHDRAW_CREATE_FAIL);
		}
	}

}
