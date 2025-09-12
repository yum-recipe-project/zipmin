package com.project.zipmin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.project.zipmin.api.ApiException;
import com.project.zipmin.api.KitchenErrorCode;
import com.project.zipmin.dto.GuideReadResponseDto;
import com.project.zipmin.dto.LikeCreateRequestDto;
import com.project.zipmin.dto.LikeCreateResponseDto;
import com.project.zipmin.dto.LikeDeleteRequestDto;
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
	
	// 가이드 목록 조회
	public Page<GuideReadResponseDto> readGuidePage(String category, String keyword, String sort, Pageable pageable) {
		
		// 입력값 검증
		if (pageable == null) {
			throw new ApiException(KitchenErrorCode.KITCHEN_INVALID_INPUT);
		}
		
		// 정렬기준 문자열 객체로 변환
		Sort sortSpec = Sort.by(Sort.Order.desc("id"));
		
		if(sort != null && !sort.isBlank()) {
			switch (sort) {
			case "id-desc": {
				sortSpec = Sort.by(Sort.Order.desc("id"));
				break;
			}
			case "likecount-desc": {
				sortSpec = Sort.by(Sort.Order.desc("likecount"), Sort.Order.desc("id"));
				break;
			}
			default:
				sortSpec = Sort.by(Sort.Order.desc("id"));
			}
		}
		
		// 기존 페이지 객체에 정렬 주입
		Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sortSpec);
		
		// 가이드 목록 조회
		Page<Guide> guidePage;
		try {
			guidePage = (keyword == null || keyword.isBlank())
					? kitchenRepository.findAll(sortedPageable)
					: kitchenRepository.findAllByCategory(keyword, sortedPageable);
			
			boolean hasCategory = category != null && !category.isBlank();
			boolean hasKeyword = keyword != null && !keyword.isBlank();
			
			if (!hasCategory) {
				// 전체
				guidePage = hasKeyword
	                    ? kitchenRepository.findAllByTitleContainingIgnoreCase(keyword, sortedPageable)
	                    : kitchenRepository.findAll(sortedPageable);
	        }
			else {
				// 카테고리만
				guidePage = hasKeyword
	                    ? kitchenRepository.findAllByCategoryAndTitleContainingIgnoreCase(category, keyword, sortedPageable)
	                    : kitchenRepository.findAllByCategory(category, sortedPageable);
	        }
		}
		catch (Exception e) {
			throw new ApiException(KitchenErrorCode.KITCHEN_READ_LIST_FAIL);
		}
		
		// dto 변경
		List<GuideReadResponseDto> guideDtoList = new ArrayList<GuideReadResponseDto>();
		for (Guide guide : guidePage) {
			GuideReadResponseDto guideDto = guideMapper.toReadResponseDto(guide);
			guideDto.setLikecount(likeService.countLike("guide", guide.getId()));
			
			// 좋아요 여부 조회
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			
			if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {
			    String username = authentication.getName();
			    int userId = userService.readUserByUsername(username).getId();
			    
			    guideDto.setLikestatus(likeService.existsUserLike("guide", guideDto.getId(), userId));
			}
			guideDtoList.add(guideDto);
		}
		
		
		return new PageImpl<>(guideDtoList, sortedPageable, guidePage.getTotalElements());
	}
	
	
    // 특정 가이드 상세 조회
	public GuideReadResponseDto readGuideById(int id) {
	    Guide guide = kitchenRepository.findById(id)
	            .orElseThrow(() -> new IllegalArgumentException("해당 가이드를 찾을 수 없습니다. ID: " + id));

	    return guideMapper.toReadResponseDto(guide);
	}

	
	// 가이드 좋아요
	public LikeCreateResponseDto likeGuide(LikeCreateRequestDto likeDto) {
		
		// 입력값 검증
		if (likeDto == null || likeDto.getTablename() == null
				|| likeDto.getRecodenum() == null || likeDto.getUserId() == null) {
			throw new ApiException(KitchenErrorCode.KITCHEN_INVALID_INPUT);
		}
		
		// 게시글 존재 여부 확인
		if (!kitchenRepository.existsById(likeDto.getRecodenum())) {
		    throw new ApiException(KitchenErrorCode.KITCHEN_NOT_FOUND);
		}
		
		// 좋아요 저장
		try {
		    return likeService.createLike(likeDto);
		}
		catch (ApiException e) {
		    throw e;
		}
		catch (Exception e) {
		    throw new ApiException(KitchenErrorCode.KITCHEN_LIKE_FAIL);
		}
		
	}

	
	// 가이드 좋아요 취소
	public void unlikeGuide(LikeDeleteRequestDto likeDto) {
	    // 입력값 검증
	    if (likeDto == null || likeDto.getTablename() == null
	            || likeDto.getRecodenum() == null || likeDto.getUserId() == null) {
	        throw new ApiException(KitchenErrorCode.KITCHEN_INVALID_INPUT);
	    }

	    // 게시글 존재 여부 확인
	    if (!kitchenRepository.existsById(likeDto.getRecodenum())) {
	        throw new ApiException(KitchenErrorCode.KITCHEN_NOT_FOUND);
	    }

	    // 좋아요 삭제
	    try {
	        likeService.deleteLike(likeDto);
	    } catch (ApiException e) {
	        throw e;
	    } catch (Exception e) {
	        throw new ApiException(KitchenErrorCode.KITCHEN_UNLIKE_FAIL);
	    }
	}
	

}
