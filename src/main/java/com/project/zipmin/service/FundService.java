package com.project.zipmin.service;

import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.zipmin.api.ApiException;
import com.project.zipmin.api.FundErrorCode;
import com.project.zipmin.dto.FundSupportRequestDto;
import com.project.zipmin.dto.FundSupportResponseDto;
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
	
	private final FundMapper fundMapper;
	private final UserMapper userMapper;
	private final RecipeMapper recipeMapper;
	
	private final FundRepository fundRepository;
	
	
	 // 후원하기
	 public FundSupportResponseDto support(int funderId, int fundeeId, FundSupportRequestDto requestDto) {
		// 후원자 조회
        UserReadResponseDto funderDto = userService.readUserById(funderId);
        User funder = userMapper.toEntity(funderDto);

        // 후원받는 사람 조회
        UserReadResponseDto fundeeDto = userService.readUserById(fundeeId);
        User fundee = userMapper.toEntity(fundeeDto);

        if (funder.getPoint() < fundee.getPoint()) {
            throw new ApiException(FundErrorCode.FUND_POINT_EXCEED);
        }
        
        // 후원할 레시피 조회
        RecipeReadResponseDto recipeDto = recipeService.readRecipdById(requestDto.getRecipeId());
        Recipe recipe = recipeMapper.toEntity(recipeDto);
        
	    Fund fund = Fund.builder()
	            .funder(funder)
	            .fundee(fundee)
	            .recipe(recipe)
	            .point(requestDto.getPoint())
	            .status(0) // 기본 미출금
	            .funddate(new Date())
	            .build();
	    
	    // DB에 후원내역 저장
	    try {
	        fund = fundRepository.save(fund);
	    } catch (Exception e) {
	        throw new ApiException(FundErrorCode.FUND_INVALID_REQUEST);
	    }
	    
	    
	    // 포인트 업데이트
	    funder.setPoint(funder.getPoint() - requestDto.getPoint());
	    fundee.setPoint(fundee.getPoint() + requestDto.getPoint());
	    userService.saveUser(funder);  
	    userService.saveUser(fundee);
	    
	    return new FundSupportResponseDto(fund.getId(), funder.getPoint(), fundee.getPoint());
	}
	 
	
	
	

}
