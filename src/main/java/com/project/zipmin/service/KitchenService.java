package com.project.zipmin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.zipmin.api.ApiException;
import com.project.zipmin.api.KitchenErrorCode;
import com.project.zipmin.dto.UserReadResponseDto;
import com.project.zipmin.dto.kitchen.GuideCreateRequestDto;
import com.project.zipmin.dto.kitchen.GuideCreateResponseDto;
import com.project.zipmin.dto.kitchen.GuideReadResponseDto;
import com.project.zipmin.dto.kitchen.GuideUpdateRequestDto;
import com.project.zipmin.dto.kitchen.GuideUpdateResponseDto;
import com.project.zipmin.dto.like.LikeCreateRequestDto;
import com.project.zipmin.dto.like.LikeCreateResponseDto;
import com.project.zipmin.dto.like.LikeDeleteRequestDto;
import com.project.zipmin.dto.like.LikeReadResponseDto;
import com.project.zipmin.entity.Guide;
import com.project.zipmin.entity.Role;
import com.project.zipmin.mapper.KitchenMapper;
import com.project.zipmin.repository.KitchenRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class KitchenService {
	
	private final KitchenRepository kitchenRepository;
	
	private final KitchenMapper kitchenMapper;
	
	private final UserService userService;
	private final LikeService likeService;
	
	
	
	
	
	// 키친가이드 목록 조회
	public Page<GuideReadResponseDto> readGuidePage(String keyword, String category, String sort, Pageable pageable) {
		
		// 입력값 검증
		if (pageable == null) {
			throw new ApiException(KitchenErrorCode.KITCHEN_INVALID_INPUT);
		}
		
		// 정렬
		Sort order = Sort.by(Sort.Order.desc("id"));
		if (sort != null && !sort.isBlank()) {
			String field = sort.split("-")[0];
			Direction direction = "asc".equals(sort.split("-")[1]) ? Direction.ASC : Direction.DESC;
			order = Sort.by(new Order(direction, field), Order.desc("id"));
		}
		pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), order);
		
		// 가이드 목록 조회
		Page<Guide> guidePage;
		try {
			boolean hasCategory = category != null && !category.isBlank();
			boolean hasKeyword = keyword != null && !keyword.isBlank();
			
			if (hasCategory) {
				guidePage = hasKeyword
						? kitchenRepository.findAllByCategoryAndTitleContainingIgnoreCase(category, keyword, pageable)
						: kitchenRepository.findAllByCategory(category, pageable);
			}
			else {
				guidePage = hasKeyword
						? kitchenRepository.findAllByTitleContainingIgnoreCase(keyword, pageable)
						: kitchenRepository.findAll(pageable);
			}
		}
		catch (Exception e) {
			throw new ApiException(KitchenErrorCode.KITCHEN_READ_LIST_FAIL);
		}
		
		// 키친가이드 목록 응답 구성
		List<GuideReadResponseDto> guideDtoList = new ArrayList<>();
		for (Guide guide : guidePage) {
			GuideReadResponseDto guideDto = kitchenMapper.toReadResponseDto(guide);
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {
				UserReadResponseDto userDto = userService.readUserByUsername(authentication.getName());
				guideDto.setLikestatus(likeService.existLike("guide", guideDto.getId(), userDto.getId()));
			}
			guideDto.setLikecount(guide.getLikecount());
			guideDto.setUsername(guide.getUser().getUsername());
			guideDto.setAvatar(guide.getUser().getAvatar());
			guideDtoList.add(guideDto);
		}
		
		return new PageImpl<>(guideDtoList, pageable, guidePage.getTotalElements());
	}
	
	
	
	
	
	// 사용자가 저장한 키친가이드 목록 조회
	public Page<GuideReadResponseDto> readLikedGuidePageByUserId(int userId, Pageable pageable) {

		// 입력값 검증
		if (userId == 0 || pageable == null) {
			throw new ApiException(KitchenErrorCode.KITCHEN_INVALID_INPUT);
		}
		
		// 좋아요한 키친가이드 일련번호 추출
		List<LikeReadResponseDto> likeList = likeService.readLikeListByTablenameAndUserId("guide", userId);
		List<Integer> guideIdList = likeList.stream()
				.map(LikeReadResponseDto::getRecodenum)
				.toList();

		if (guideIdList.isEmpty()) {
			return Page.empty(pageable); 
		}
		
		// 키친가이드 목록 조회
		Page<Guide> guidePage;
		try {
			guidePage = kitchenRepository.findAllByIdIn(guideIdList, pageable);
		}
		catch (Exception e) {
			throw new ApiException(KitchenErrorCode.KITCHEN_READ_LIST_FAIL);
		}
		
		// 키친가이드 응답 구성
		List<GuideReadResponseDto> guideDtoList = new ArrayList<>();
		for (Guide guide : guidePage) {
			GuideReadResponseDto guideDto = kitchenMapper.toReadResponseDto(guide);
			guideDto.setLikecount(guide.getLikecount());
			guideDtoList.add(guideDto);
		}

		return new PageImpl<>(guideDtoList, pageable, guidePage.getTotalElements());
	}
	
	
	
	
	
	// 키친가이드 상세 조회
	public GuideReadResponseDto readGuideById(int id) {
		
		// 입력값 검증
		if (id == 0) {
			throw new ApiException(KitchenErrorCode.KITCHEN_INVALID_INPUT);
		}
		
		// 키친가이드 조회
		Guide guide;
		try {
			guide = kitchenRepository.findById(id);
		}
		catch (Exception e) {
			throw new ApiException(KitchenErrorCode.KITCHEN_NOT_FOUND);
		}
		
		// 키친가이드 응답 구성
		GuideReadResponseDto guideDto = kitchenMapper.toReadResponseDto(guide);
		guideDto.setLikecount(guide.getLikecount());
		guideDto.setAvatar(guide.getUser().getAvatar());
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated() && !authentication.getPrincipal().equals("anonymousUser")) {
			UserReadResponseDto userDto = userService.readUserByUsername(authentication.getName());
			guideDto.setLikestatus(likeService.existLike("guide", guideDto.getId(), userDto.getId()));
		}

		return guideDto;
	}
	
	
	
	
	
	// 키친가이드 작성
	public GuideCreateResponseDto createGuide(GuideCreateRequestDto guideRequestDto) {

		// 입력값 검증
		if (guideRequestDto == null 
				|| guideRequestDto.getTitle() == null 
				|| guideRequestDto.getSubtitle() == null
				|| guideRequestDto.getCategory() == null 
				|| guideRequestDto.getContent() == null
				|| guideRequestDto.getUserId() == 0) {
			throw new ApiException(KitchenErrorCode.KITCHEN_INVALID_INPUT);
		}
		
		// 키친가이드 작성
		try {
			Guide guide = kitchenMapper.toEntity(guideRequestDto);
			guide = kitchenRepository.save(guide);
			return kitchenMapper.toCreateResponseDto(guide);
		} catch (Exception e) {
			throw new ApiException(KitchenErrorCode.KITCHEN_CREATE_FAIL);
		}
	}
	
	
	
	
	
	// 키친가이드 수정
	public GuideUpdateResponseDto updateGuide(GuideUpdateRequestDto guideRequestDto) {
		
		// 입력값 검증
		if (guideRequestDto == null
				|| guideRequestDto.getId() == 0
				|| guideRequestDto.getTitle() == null
				|| guideRequestDto.getSubtitle() == null
				|| guideRequestDto.getCategory() == null
				|| guideRequestDto.getContent() == null) {
			throw new ApiException(KitchenErrorCode.KITCHEN_INVALID_INPUT);
		}
		
		// 키친가이드 조회
		Guide guide;
		try {
			guide = kitchenRepository.findById(guideRequestDto.getId());
		}
		catch (Exception e) {
			throw new ApiException(KitchenErrorCode.KITCHEN_NOT_FOUND);
		}
		
		// 권한 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserReadResponseDto currentUser = userService.readUserByUsername(username);
		
		if (!currentUser.getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			if (currentUser.getRole().equals(Role.ROLE_ADMIN.name())) {
				if (guide.getUser().getRole().equals(Role.ROLE_SUPER_ADMIN)) {
					throw new ApiException(KitchenErrorCode.KITCHEN_FORBIDDEN);
				}
				if (guide.getUser().getRole().equals(Role.ROLE_ADMIN) && currentUser.getId() != guide.getUser().getId()) {
					throw new ApiException(KitchenErrorCode.KITCHEN_FORBIDDEN);
				}
			}
			else if (currentUser.getRole().equals(Role.ROLE_USER.name()) && currentUser.getId() != guide.getUser().getId()) {
				throw new ApiException(KitchenErrorCode.KITCHEN_FORBIDDEN);
			}
		}
		
		// 값 설정
		guide.setTitle(guideRequestDto.getTitle());
		guide.setSubtitle(guideRequestDto.getSubtitle());
		guide.setCategory(guideRequestDto.getCategory());
		guide.setContent(guideRequestDto.getContent());
		
		// 키친가이드 수정
 		try {
 			guide = kitchenRepository.save(guide);
 			return kitchenMapper.toUpdateResponseDto(guide);
 		}
 		catch (Exception e) {
 			throw new ApiException(KitchenErrorCode.KITCHEN_UPDATE_FAIL);
 		}
	}
	
	
	
	
	
	// 키친가이드 삭제
	public void deleteGuide(int id) {
		
		// 입력값 검증
		if (id == 0) {
			throw new ApiException(KitchenErrorCode.KITCHEN_INVALID_INPUT);
		}
		
		// 키친가이드 조회
		Guide guide;
		try {
			guide = kitchenRepository.findById(id);
		}
		catch (Exception e) {
			throw new ApiException(KitchenErrorCode.KITCHEN_NOT_FOUND);
		}
		
		// 권한 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserReadResponseDto currentUser = userService.readUserByUsername(username);
		
		if (!currentUser.getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			if (currentUser.getRole().equals(Role.ROLE_ADMIN.name())) {
				if (guide.getUser().getRole().equals(Role.ROLE_SUPER_ADMIN)) {
					throw new ApiException(KitchenErrorCode.KITCHEN_FORBIDDEN);
				}
				if (guide.getUser().getRole().equals(Role.ROLE_ADMIN) && currentUser.getId() != guide.getUser().getId()) {
					throw new ApiException(KitchenErrorCode.KITCHEN_FORBIDDEN);
				}
			}
			else if (currentUser.getRole().equals(Role.ROLE_USER.name()) && currentUser.getId() != guide.getUser().getId()) {
				throw new ApiException(KitchenErrorCode.KITCHEN_FORBIDDEN);
			}
		}
		
		// 키친가이드 삭제
		try {
			kitchenRepository.deleteById(id);
		}
		catch (Exception e) {
			throw new ApiException(KitchenErrorCode.KITCHEN_DELETE_FAIL);
		}
	}
	
	
	
	
	
	// 키친가이드 좋아요
	public LikeCreateResponseDto likeGuide(LikeCreateRequestDto likeDto) {
		
		// 입력값 검증
		if (likeDto == null
				|| likeDto.getTablename() == null
				|| likeDto.getRecodenum() == 0
				|| likeDto.getUserId() == 0) {
			throw new ApiException(KitchenErrorCode.KITCHEN_INVALID_INPUT);
		}
		
		// 키친가이드 존재 여부 확인
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


	
	
	
	// 키친가이드 좋아요 취소
	public void unlikeGuide(LikeDeleteRequestDto likeDto) {
		
		// 입력값 검증
		if (likeDto == null
				|| likeDto.getTablename() == null
				|| likeDto.getRecodenum() == 0
				|| likeDto.getUserId() == 0) {
			throw new ApiException(KitchenErrorCode.KITCHEN_INVALID_INPUT);
		}
		
		// 키친가이드 존재 여부 확인
		if (!kitchenRepository.existsById(likeDto.getRecodenum())) {
			throw new ApiException(KitchenErrorCode.KITCHEN_NOT_FOUND);
		}
		
		// 좋아요 삭제
		try {
			likeService.deleteLike(likeDto);
		}
		catch (Exception e) {
			throw new ApiException(KitchenErrorCode.KITCHEN_UNLIKE_FAIL);
		}
	}
	
}
