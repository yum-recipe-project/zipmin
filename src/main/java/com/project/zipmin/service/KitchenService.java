package com.project.zipmin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.project.zipmin.api.ApiException;
import com.project.zipmin.api.KitchenErrorCode;
import com.project.zipmin.dto.GuideReadResponseDto;
import com.project.zipmin.entity.Guide;
import com.project.zipmin.mapper.GuideMapper;
import com.project.zipmin.repository.KitchenRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KitchenService {
	
	@Autowired
	private final KitchenRepository kitchenRepository;
	
	@Autowired
	private UserService userService;
	@Autowired
	private LikeService likeService;
	
	@Autowired
	private final GuideMapper guideMapper;
	
	
	
	// 가이드 목록을 조회하는 함수 (최신순)
	public Page<GuideReadResponseDto> readGuidePageOrderByIdDesc(String category, Pageable pageable) {
		
		// 입력값 검증
		if (pageable == null) {
			throw new ApiException(KitchenErrorCode.KITCHEN_INVALID_INPUT);
		}
		
		// 가이드 목록 조회
		Page<Guide> guidePage;
		try {
			guidePage = (category == null || category.isBlank())
					? kitchenRepository.findOrderByIdDesc(pageable)
					: kitchenRepository.findByCategoryOrderByIdDesc(category, pageable);
		}
		catch (Exception e) {
			throw new ApiException(KitchenErrorCode.KITCHEN_READ_LIST_FAIL);
		}
		
		// ********************* 검색어 추가할 것 *********************
		List<GuideReadResponseDto> guideDtoList = new ArrayList<GuideReadResponseDto>();
		for (Guide guide : guidePage) {
			GuideReadResponseDto guideDto = guideMapper.toReadResponseDto(guide);
			guideDto.setLikecount(likeService.countLikesByTablenameAndRecodenum("guide", guide.getId()));
			
			// 좋아요 여부 조회
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {
			    String username = authentication.getName();
			    int userId = userService.readUserByUsername(username).getId();
			    guideDto.setLikestatus(likeService.existsUserLike("guide", guideDto.getId(), userId));
			}
			guideDtoList.add(guideDto);
		}
		
		return new PageImpl<>(guideDtoList, pageable, guidePage.getTotalElements());
	}
	
	
	
	// 가이드 목록을 조회하는 함수 (좋아요순)
	public Page<GuideReadResponseDto> readGuidePageOrderByLikecount(String category, Pageable pageable) {
		
		// 입력값 검증
		if (pageable == null) {
			throw new ApiException(KitchenErrorCode.KITCHEN_INVALID_INPUT);
		}
		
		// 가이드 목록 조회
		Page<Guide> guidePage;
		try {
			guidePage = (category == null || category.isBlank())
					? kitchenRepository.findOrderByLikecount(pageable)
					: kitchenRepository.findByCategoryOrderByLikecount(category, pageable);
		}
		catch (Exception e) {
			throw new ApiException(KitchenErrorCode.KITCHEN_READ_LIST_FAIL);
		}
		
		List<GuideReadResponseDto> guideDtoList = new ArrayList<GuideReadResponseDto>();
		for (Guide guide : guidePage) {
			GuideReadResponseDto guideDto = guideMapper.toReadResponseDto(guide);
			guideDto.setLikecount(likeService.countLikesByTablenameAndRecodenum("guide", guide.getId()));
			
			// 좋아요 여부 조회
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {
			    String username = authentication.getName();
			    int userId = userService.readUserByUsername(username).getId();
			    guideDto.setLikestatus(likeService.existsUserLike("guide", guideDto.getId(), userId));
			}
			guideDtoList.add(guideDto);
		}
		
		return new PageImpl<>(guideDtoList, pageable, guidePage.getTotalElements());
	}
	
    
	
    // 특정 가이드 상세 조회
	public GuideReadResponseDto readGuideById(int id) {
	    Guide guide = kitchenRepository.findById(id)
	            .orElseThrow(() -> new IllegalArgumentException("해당 가이드를 찾을 수 없습니다. ID: " + id));

	    return guideMapper.toReadResponseDto(guide);
	}

	
	

}
