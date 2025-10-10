package com.project.zipmin.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.zipmin.api.ApiException;
import com.project.zipmin.api.CommentErrorCode;
import com.project.zipmin.api.FundErrorCode;
import com.project.zipmin.api.KitchenErrorCode;
import com.project.zipmin.dto.CommentReadMyResponseDto;
import com.project.zipmin.dto.FundSupportRequestDto;
import com.project.zipmin.dto.FundSupportResponseDto;
import com.project.zipmin.dto.RecipeReadResponseDto;
import com.project.zipmin.dto.UserRevenueReadResponseDto;
import com.project.zipmin.entity.Comment;
import com.project.zipmin.entity.Fund;
import com.project.zipmin.entity.Guide;
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
		User funder = userService.getUserEntityById(funderId);
		User fundee = userService.getUserEntityById(fundeeId);

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
	 
	
	
	// 특정 사용자의 후원받은 목록 조회
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
	
	

}
