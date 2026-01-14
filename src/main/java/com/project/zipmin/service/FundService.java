package com.project.zipmin.service;

import static org.hamcrest.CoreMatchers.nullValue;

import java.util.Date;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.zipmin.api.ApiException;
import com.project.zipmin.api.FundErrorCode;
import com.project.zipmin.dto.FundCreateRequestDto;
import com.project.zipmin.dto.FundCreateResponseDto;
import com.project.zipmin.dto.FundReadResponseDto;
import com.project.zipmin.dto.RecipeReadResponseDto;
import com.project.zipmin.dto.UserReadResponseDto;
import com.project.zipmin.entity.Fund;
import com.project.zipmin.entity.Recipe;
import com.project.zipmin.entity.User;
import com.project.zipmin.mapper.FundMapper;
import com.project.zipmin.mapper.RecipeMapper;
import com.project.zipmin.mapper.UserMapper;
import com.project.zipmin.repository.FundRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class FundService {
	
	private final RecipeService recipeService;
	private final UserService userService;
	
	private final RecipeMapper recipeMapper;
	private final UserMapper userMapper;
	private final FundMapper fundMapper;
	
	private final FundRepository fundRepository;
	
	
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
	 
	
	
	
	
	

}
