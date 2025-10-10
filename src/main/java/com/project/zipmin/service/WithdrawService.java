package com.project.zipmin.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.zipmin.api.ApiException;
import com.project.zipmin.api.UserErrorCode;
import com.project.zipmin.dto.WithdrawCreateRequestDto;
import com.project.zipmin.dto.WithdrawReadResponseDto;
import com.project.zipmin.entity.User;
import com.project.zipmin.entity.UserAccount;
import com.project.zipmin.entity.Withdraw;
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
	private final UserRepository userRepository;
	private final UserAccountRepository userAccountRepository;
	
	private final UserService userService;
	
	private final WithdrawMapper withdrawMapper;

	// 사용자 포인트 출금 신청
    public WithdrawReadResponseDto createWithdrawRequest(Integer userId, WithdrawCreateRequestDto withdrawRequestDto) {

    	System.err.println("서비스 출금 신청자: " + userId + "출금 정보: " + withdrawRequestDto);
    	// 입력값 검증
        if (userId == null || userId <= 0 || withdrawRequestDto == null || withdrawRequestDto.getPoint() <= 0) {
            throw new ApiException(UserErrorCode.USER_INVALID_INPUT);
        }

        // 사용자 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));

        // 사용자 계좌 조회
        UserAccount account = userAccountRepository.findByUserId(userId)
                .orElseThrow(() -> new ApiException(UserErrorCode.USER_ACCOUNT_NOT_FOUND));

        
        // 출금 신청 엔티티 
        Withdraw withdraw = Withdraw.builder()
                .user(user)
                .account(account)
                .requestPoint(withdrawRequestDto.getPoint())
                .status(0) 
                .build();
        
        System.err.println("출금 엔티티: " + withdraw);

        try {
            withdraw = withdrawRepository.saveAndFlush(withdraw);
            
            return withdrawMapper.toReadResponseDto(withdraw);
        } catch (Exception e) {
            throw new ApiException(UserErrorCode.USER_WITHDRAW_REQUEST_FAIL);
        }
    }
	
    
    
    
	
}
