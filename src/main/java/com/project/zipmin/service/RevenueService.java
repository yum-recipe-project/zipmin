package com.project.zipmin.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.zipmin.api.ApiException;
import com.project.zipmin.api.FundErrorCode;
import com.project.zipmin.api.KitchenErrorCode;
import com.project.zipmin.api.UserErrorCode;
import com.project.zipmin.dto.UserAccountCreateRequestDto;
import com.project.zipmin.dto.UserAccountReadResponseDto;
import com.project.zipmin.dto.UserAccountUpdateRequestDto;
import com.project.zipmin.dto.UserReadResponseDto;
import com.project.zipmin.dto.UserRevenueReadResponseDto;
import com.project.zipmin.entity.Fund;
import com.project.zipmin.entity.Role;
import com.project.zipmin.entity.User;
import com.project.zipmin.entity.UserAccount;
import com.project.zipmin.mapper.FundMapper;
import com.project.zipmin.mapper.UserAccountMapper;
import com.project.zipmin.mapper.UserMapper;
import com.project.zipmin.repository.FundRepository;
import com.project.zipmin.repository.UserAccountRepository;
import com.project.zipmin.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class RevenueService {
	
	private final UserMapper userMapper;
	private final FundMapper fundMapper;
	
	private final UserAccountMapper userAccountMapper;
	private final UserService userService;
	
	private final UserRepository userRepository;
	private final UserAccountRepository userAccountRepository;
	private final FundRepository fundRepository;
	
	
	

	// 아이디로 사용자 출금 계좌 조회
    public UserAccountReadResponseDto readUserAccountById(Integer id) {

        // 입력값 검증
        if (id == null) {
            throw new ApiException(UserErrorCode.USER_INVALID_INPUT);
        }

        // 사용자 조회
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));

        UserAccount account = userAccountRepository.findByUser(user).orElse(null);

        // 계좌가 없으면 null 반환
        if (account == null) {
            return null;
        }

        return userMapper.toReadAccountResponseDto(account);
    }
	
    
    
    
    
    

    // 사용자 출금 계좌 등록
    @Transactional
    public UserAccountReadResponseDto createUserAccount(UserAccountCreateRequestDto accountRequestDto) {
        // 입력값 검증
        if (accountRequestDto == null 
                || accountRequestDto.getBank() == null 
                || accountRequestDto.getAccountnum() == null
                || accountRequestDto.getName() == null
                || accountRequestDto.getUserId() == 0) {
            throw new ApiException(UserErrorCode.USER_INVALID_INPUT);
        }

        // 사용자 조회
        User user = userRepository.findById(accountRequestDto.getUserId())
                .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));
        
        UserAccount userAccount = userAccountMapper.toEntity(accountRequestDto);
        userAccount.setUser(user);
        
        try {
        	userAccount = userAccountRepository.save(userAccount);
            return userMapper.toReadAccountResponseDto(userAccount);
        } catch (Exception e) {
            throw new ApiException(UserErrorCode.USER_CREATE_ACCOUNT_FAIL);
        }
    }
    
    
    
    
    
    
    // 사용자 출금 계좌 수정
    @Transactional
    public UserAccountReadResponseDto updateUserAccount(UserAccountUpdateRequestDto accountRequestDto) {

        // 입력값 검증
        if (accountRequestDto == null 
                || accountRequestDto.getUserId() == 0
                || accountRequestDto.getBank() == null
                || accountRequestDto.getAccountnum() == null
                || accountRequestDto.getName() == null) {
            throw new ApiException(UserErrorCode.USER_INVALID_INPUT);
        }

        // 로그인 사용자 확인
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserReadResponseDto loginUser = userService.readUserByUsername(username);

        if (!loginUser.getRole().equals(Role.ROLE_SUPER_ADMIN.name()) &&
            !loginUser.getRole().equals(Role.ROLE_ADMIN.name()) &&
            loginUser.getId() != accountRequestDto.getUserId()) {
            // 일반 사용자가 다른 사람 계좌를 수정하려고 하면
            throw new ApiException(UserErrorCode.USER_FORBIDDEN);
        }

        // 수정할 계좌 조회
        User user = userRepository.findById(accountRequestDto.getUserId())
                .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));

        UserAccount account = userAccountRepository.findByUser(user)
                .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));

        // 변경 값 설정
        account.setBank(accountRequestDto.getBank());
        account.setAccountnum(accountRequestDto.getAccountnum());
        account.setName(accountRequestDto.getName());

        try {
            account = userAccountRepository.save(account);
            return userMapper.toReadAccountResponseDto(account);
        } catch (Exception e) {
            throw new ApiException(UserErrorCode.USER_UPDATE_ACCOUNT_FAIL);
        }
    }

    
    
    
    
    // 특정 사용자의 후원받은 목록(수익내역) 조회
    public Page<UserRevenueReadResponseDto> readUserRevenuePageById(Integer userId, Pageable pageable) {

        if (userId == null || pageable == null) {
            throw new ApiException(KitchenErrorCode.KITCHEN_INVALID_INPUT);
        }
        
        
     // 후원받은 내역 목록 조회
 		Page<Fund> fundPage;
 		try {
 			fundPage = fundRepository.findByFundeeId(userId, pageable);
 		}
 		catch (Exception e) {
 			throw new ApiException(FundErrorCode.FUND_NOT_FOUND);
 		}

 		List<UserRevenueReadResponseDto> revenueDtoList = new ArrayList<UserRevenueReadResponseDto>();
 		for (Fund fund : fundPage) {
 			UserRevenueReadResponseDto revenueDto = fundMapper.toReadRevenueResponseDto(fund);
 			
 	        revenueDto.setFunderNickname(fund.getFunder().getNickname());
	        if (fund.getRecipe() != null) {
	            revenueDto.setRecipeTitle(fund.getRecipe().getTitle());
	        }
	        
			revenueDtoList.add(revenueDto);
		}
        
        return new PageImpl<>(revenueDtoList, pageable, fundPage.getTotalElements());
    }
	
	
    // 사용자가 받은 총 후원 금액 (수익)
    public int getUserTotalRevenue(Integer userId) {
        if (userId == null || userId <= 0) {
            throw new ApiException(UserErrorCode.USER_INVALID_INPUT);
        }

        Integer total = fundRepository.sumPointByFundeeId(userId);

        return total != null ? total : 0;
    }
    
    
    
    // 사용자의 출금 가능한 수익 
    public int getUserRevenue(Integer userId) {
        if (userId == null || userId <= 0) {
            throw new ApiException(UserErrorCode.USER_INVALID_INPUT);
        }
        User user =  userRepository.findById(userId).get();

        return user.getRevenue();
    }
	
	
	
	
	
	
}
