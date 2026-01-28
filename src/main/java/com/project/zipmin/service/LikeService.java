package com.project.zipmin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.project.zipmin.api.ApiException;
import com.project.zipmin.api.LikeErrorCode;
import com.project.zipmin.dto.UserReadResponseDto;
import com.project.zipmin.dto.like.LikeCreateRequestDto;
import com.project.zipmin.dto.like.LikeCreateResponseDto;
import com.project.zipmin.dto.like.LikeDeleteRequestDto;
import com.project.zipmin.dto.like.LikeReadResponseDto;
import com.project.zipmin.entity.Like;
import com.project.zipmin.entity.Role;
import com.project.zipmin.mapper.LikeMapper;
import com.project.zipmin.repository.LikeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LikeService {
	
	private final LikeRepository likeRepository;
	private final LikeMapper likeMapper;
	// private final UserService userService;
	
	
	 
	
	
	// 좋아요 조회
	public LikeReadResponseDto readLike(String tablename, int recodenum, int userId) {
		
		// 입력값 검증
		if (tablename == null
				|| recodenum == 0
				|| userId == 0) {
			throw new ApiException(LikeErrorCode.LIKE_INVALID_INPUT);
		}
		
		// 좋아요 조회
		Like like;
		try {
			like = likeRepository.findByTablenameAndRecodenumAndUserId(tablename, recodenum, userId);
			return likeMapper.toReadResponseDto(like);
		}
		catch (Exception e) {
			throw new ApiException(LikeErrorCode.LIKE_READ_FAIL);
		}
	}

	
	
	
	
	// 좋아요 목록 조회 (사용자 기반)
	public List<LikeReadResponseDto> readLikeListByTablenameAndUserId(String tablename, Integer userId) {
		
		// 입력값 검증
		if (tablename == null || userId == null) {
			throw new ApiException(LikeErrorCode.LIKE_INVALID_INPUT);
		}
		
		// 좋아요 목록 조회
		List<Like> likeList;
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
	
	
	
	
	
	// 좋아요 목록 조회 (레코드번호 기반)
	public List<LikeReadResponseDto> readLikeListByTablenameAndRecodenum(String tablename, Integer recodenum) {
		
		// 입력값 검증
		if (tablename == null || recodenum == null) {
			throw new ApiException(LikeErrorCode.LIKE_INVALID_INPUT);
		}
		
		// 좋아요 목록 조회
		List<Like> likeList;
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
	
	
	
	
		
	// 좋아요 작성
	public LikeCreateResponseDto createLike(LikeCreateRequestDto likeDto) {
		
		// 입력값 검증
		if (likeDto == null
				|| likeDto.getTablename() == null
				|| likeDto.getRecodenum() == 0
				|| likeDto.getUserId() == 0) {
			throw new ApiException(LikeErrorCode.LIKE_INVALID_INPUT);
		}
		
	    // 중복 검사
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
	
	
	
	
	
	// 좋아요 삭제 1
	public void deleteLike(LikeDeleteRequestDto likeDto) {
		
		// 입력값 검증
		if (likeDto == null || likeDto.getTablename() == null
				|| likeDto.getRecodenum() == 0
				|| likeDto.getUserId() == 0) {
			throw new ApiException(LikeErrorCode.LIKE_INVALID_INPUT);
		}
		
		// 좋아요 조회
		Like like;
		try {
			like = likeRepository.findByTablenameAndRecodenumAndUserId(likeDto.getTablename(), likeDto.getRecodenum(), likeDto.getUserId());
		}
		catch (Exception e) {
			throw new ApiException(LikeErrorCode.LIKE_NOT_FOUND);
		}

		// 권한 확인
//		String username = SecurityContextHolder.getContext().getAuthentication().getName();
//		UserReadResponseDto currentUser = userService.readUserByUsername(username);
//		
//		if (!currentUser.getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
//			if (currentUser.getRole().equals(Role.ROLE_ADMIN.name())) {
//				if (like.getUser().getRole().equals(Role.ROLE_SUPER_ADMIN)) {
//					throw new ApiException(LikeErrorCode.LIKE_FORBIDDEN);
//				}
//				if (like.getUser().getRole().equals(Role.ROLE_ADMIN) && currentUser.getId() != like.getUser().getId()) {
//					throw new ApiException(LikeErrorCode.LIKE_FORBIDDEN);
//				}
//			}
//			else if (currentUser.getRole().equals(Role.ROLE_USER.name()) && currentUser.getId() != like.getUser().getId()) {
//				throw new ApiException(LikeErrorCode.LIKE_FORBIDDEN);
//			}
//		}

		// 좋아요 삭제
		try {
			likeRepository.deleteById(like.getId());
		}
		catch (Exception e) {
			throw new ApiException(LikeErrorCode.LIKE_DELETE_FAIL);
		}
	}
	
	
	
	
	
	// 좋아요 삭제 2
	public void deleteLike2(int id) {
		
		// 입력값 검증
		if (id == 0) {
			throw new ApiException(LikeErrorCode.LIKE_INVALID_INPUT);
		}
		
		// 좋아요 조회
		Like like;
		try {
			like = likeRepository.findById(id);
		}
		catch (Exception e) {
			throw new ApiException(LikeErrorCode.LIKE_NOT_FOUND);
		}
		
		// 권한 확인
//		String username = SecurityContextHolder.getContext().getAuthentication().getName();
//		UserReadResponseDto currentUser = userService.readUserByUsername(username);
//		
//		if (!currentUser.getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
//			if (currentUser.getRole().equals(Role.ROLE_ADMIN.name())) {
//				if (like.getUser().getRole().equals(Role.ROLE_SUPER_ADMIN)) {
//					throw new ApiException(LikeErrorCode.LIKE_FORBIDDEN);
//				}
//				if (like.getUser().getRole().equals(Role.ROLE_ADMIN) && currentUser.getId() != like.getUser().getId()) {
//					throw new ApiException(LikeErrorCode.LIKE_FORBIDDEN);
//				}
//			}
//			else if (currentUser.getRole().equals(Role.ROLE_USER.name()) && currentUser.getId() != like.getUser().getId()) {
//				throw new ApiException(LikeErrorCode.LIKE_FORBIDDEN);
//			}
//		}
		
		// 좋아요 삭제
		try {
			likeRepository.deleteById(id);
		}
		catch (Exception e) {
			throw new ApiException(LikeErrorCode.LIKE_DELETE_FAIL);
		}
	}
	
	
	
	
	
	// 좋아요 여부 확인
	public boolean existLike(String tablename, int recodenum, int userId) {
		
		// 입력값 검증
		if (tablename == null || recodenum == 0 || userId == 0) {
			throw new ApiException(LikeErrorCode.LIKE_INVALID_INPUT);
		}

		// 좋아요 여부 확인
		try {
			return likeRepository.existsByTablenameAndRecodenumAndUserId(tablename, recodenum, userId);
		}
		catch (Exception e) {
			throw new ApiException(LikeErrorCode.LIKE_EXIST_FAIL);
		}
	}

	
	
	
	
	// 좋아요수 조회
	public int countLike(String tablename, int recodenum) {
		
		// 입력값 검증
		if (tablename == null || recodenum == 0) {
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
	
}