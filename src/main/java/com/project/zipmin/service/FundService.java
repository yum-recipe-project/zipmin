package com.project.zipmin.service;

import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.zipmin.api.ApiException;
import com.project.zipmin.api.FundErrorCode;
import com.project.zipmin.dto.FundSupportRequestDto;
import com.project.zipmin.dto.FundSupportResponseDto;
import com.project.zipmin.dto.RecipeReadResponseDto;
import com.project.zipmin.entity.Fund;
import com.project.zipmin.entity.Recipe;
import com.project.zipmin.entity.User;
import com.project.zipmin.mapper.RecipeMapper;
import com.project.zipmin.repository.FundRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class FundService {
	
	private final RecipeService recipeService;
	private final UserService userService;
	
	private final RecipeMapper recipeMapper;
	
	private final FundRepository fundRepository;
	
	
	 // 후원하기
	 public FundSupportResponseDto support(int funderId, int fundeeId, FundSupportRequestDto requestDto) {
		User funder = userService.getUserEntityById(funderId);
		User fundee = userService.getUserEntityById(fundeeId);

		// 후원자의 보유 포인트와 후원하려는 포인트 검증
        if (funder.getPoint() < requestDto.getPoint()) {
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
	    
	    try {
	        fund = fundRepository.save(fund);
	    } catch (Exception e) {
	        throw new ApiException(FundErrorCode.FUND_INVALID_REQUEST);
	    }
	    
	    
	    // 포인트 업데이트
	    funder.setPoint(funder.getPoint() - requestDto.getPoint());
	    fundee.setRevenue(fundee.getRevenue() + requestDto.getPoint());
	    userService.saveUser(funder);  
	    userService.saveUser(fundee);
	    
	    return new FundSupportResponseDto(fund.getId(), funder.getPoint(), fundee.getRevenue());
	}
	 
	
	
	
	
	

}
