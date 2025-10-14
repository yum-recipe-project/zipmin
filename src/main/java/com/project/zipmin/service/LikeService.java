package com.project.zipmin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.zipmin.api.ApiException;
import com.project.zipmin.api.LikeErrorCode;
import com.project.zipmin.dto.LikeCreateRequestDto;
import com.project.zipmin.dto.LikeCreateResponseDto;
import com.project.zipmin.dto.LikeDeleteRequestDto;
import com.project.zipmin.dto.LikeReadResponseDto;
import com.project.zipmin.entity.Like;
import com.project.zipmin.mapper.LikeMapper;
import com.project.zipmin.repository.LikeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LikeService {
	
	private final LikeMapper likeMapper;
	private final LikeRepository likeRepository;

	
	
	
	
	// 좋아요 작성
	public LikeCreateResponseDto createLike(LikeCreateRequestDto likeDto) {
		
		// 입력값 검증
		if (likeDto == null || likeDto.getTablename() == null
				|| likeDto.getRecodenum() == null || likeDto.getUserId() == null) {
			throw new ApiException(LikeErrorCode.LIKE_INVALID_INPUT);
		}
		
	    // 중복 투표 검사
	    if (likeRepository.existsByTablenameAndRecodenumAndUserId(likeDto.getTablename(), likeDto.getRecodenum(), likeDto.getUserId())) {
	    	throw new ApiException(LikeErrorCode.LIKE_DUPLICATE);
	    }
		
		// 좋아요 저장
	    Like like = likeMapper.toEntity(likeDto);
		try {
			like = likeRepository.save(like);
			return likeMapper.toResponseDto(like);
		}
		catch (Exception e) {
			throw new ApiException(LikeErrorCode.LIKE_CREATE_FAIL);
		}
		
	}
	
	
	
	
	
	// 좋아요 삭제
	public void deleteLike(LikeDeleteRequestDto likeDto) {
		
		// 입력값 검증
		if (likeDto == null || likeDto.getTablename() == null
				|| likeDto.getRecodenum() == null || likeDto.getUserId() == null) {
			throw new ApiException(LikeErrorCode.LIKE_INVALID_INPUT);
		}
		
		// 좋아요 존재 여부 판단
		Like like = likeRepository.findByTablenameAndRecodenumAndUserId(likeDto.getTablename(), likeDto.getRecodenum(), likeDto.getUserId())
				.orElseThrow(() -> new ApiException(LikeErrorCode.LIKE_NOT_FOUND));
		
		// 권한 확인
		/*
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserReadResponseDto user = userService.readUserByUsername(username);
		if (!user.getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			// 관리자
			if (user.getRole().equals(Role.ROLE_ADMIN.name())) {
				if (like.getUser().getRole().equals(Role.ROLE_SUPER_ADMIN)) {
					throw new ApiException(LikeErrorCode.LIKE_FORBIDDEN);
				}
				if (like.getUser().getRole().equals(Role.ROLE_ADMIN)) {
					if (user.getId() != like.getUser().getId()) {
						throw new ApiException(LikeErrorCode.LIKE_FORBIDDEN);
					}
				}
			}
			// 일반 회원
			else {
				if (user.getId() != like.getUser().getId()) {
					throw new ApiException(LikeErrorCode.LIKE_FORBIDDEN);
				}
			}
		}
		*/
		
		// 좋아요 삭제
		try {
			likeRepository.deleteById(like.getId());
		}
		catch (Exception e) {
			throw new ApiException(LikeErrorCode.LIKE_DELETE_FAIL);
		}
		
	}
	
	
	
	
	
	// 좋아요 표시 여부 확인
	public boolean existsUserLike(String tablename, Integer recodenum, Integer userId) {
		
		// 입력값 검증
		if (tablename == null || recodenum == null || userId == null) {
			throw new ApiException(LikeErrorCode.LIKE_INVALID_INPUT);
		}

		try {
			return likeRepository.existsByTablenameAndRecodenumAndUserId(tablename, recodenum, userId);
		}
		catch (Exception e) {
			throw new ApiException(LikeErrorCode.LIKE_EXIST_FAIL);
		}
	}

	
	
	
	
	// 좋아요 수 조회
	public int countLike(String tablename, Integer recodenum) {
		
		// 입력값 검증
		if (tablename == null || recodenum == null) {
			throw new ApiException(LikeErrorCode.LIKE_INVALID_INPUT);
		}
		
		// 좋아요 수 조회
		try {
			return likeRepository.countByTablenameAndRecodenum(tablename, recodenum);
		}
		catch (Exception e) {
			throw new ApiException(LikeErrorCode.LIKE_COUNT_FAIL);
		}
	}
	
	
	
	
	
	// 특정 사용자가 특정 테이블에 좋아요 한 목록
	public List<LikeReadResponseDto> readLikeListByTablenameAndUserId(String tablename, Integer userId) {
		
		// 입력값 검증
		if (tablename == null || userId == null) {
			throw new ApiException(LikeErrorCode.LIKE_INVALID_INPUT);
		}
		
		// 좋아요 목록 조회
		List<Like> likeList = null;
		try {
			likeList = likeRepository.findAllByTablenameAndUserId(tablename, userId);
		}
		catch (Exception e) {
			throw new ApiException(LikeErrorCode.LIKE_READ_LIST_FAIL);
		}
		
		// 좋아요 목록 응답 구성
		List<LikeReadResponseDto> likeDtoList = new ArrayList<>();
		for (Like like : likeList) {
			LikeReadResponseDto likeDto = likeMapper.toReadResponseDto(like);
			likeDtoList.add(likeDto);
		}
		
		return likeDtoList;
	}
	
	
	
	
	// 특정 테이블의 특정 레코드에 좋아요 한 목록
	public List<LikeReadResponseDto> readLikeListByTablenameAndRecodenum(String tablename, Integer recodenum) {
		
		// 입력값 검증
		if (tablename == null || recodenum == null) {
			throw new ApiException(LikeErrorCode.LIKE_INVALID_INPUT);
		}
		
		// 좋아요 목록 조회
		List<Like> likeList = null;
		try {
			likeList = likeRepository.findAllByTablenameAndRecodenum(tablename, recodenum);
		}
		catch (Exception e) {
			throw new ApiException(LikeErrorCode.LIKE_READ_LIST_FAIL);
		}
		
		// 좋아요 목록 응답 구성
		List<LikeReadResponseDto> likeDtoList = new ArrayList<>();
		for (Like like : likeList) {
			LikeReadResponseDto likeDto = likeMapper.toReadResponseDto(like);
			likeDtoList.add(likeDto);
		}
		
		return likeDtoList;
	}
	
	
	
	
	
	
	
}