package com.project.zipmin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.zipmin.api.ApiException;
import com.project.zipmin.api.CommentErrorCode;
import com.project.zipmin.api.LikeErrorCode;
import com.project.zipmin.api.VoteErrorCode;
import com.project.zipmin.dto.LikeCreateRequestDto;
import com.project.zipmin.dto.LikeCreateResponseDto;
import com.project.zipmin.dto.LikeDeleteRequestDto;
import com.project.zipmin.entity.Like;
import com.project.zipmin.entity.Role;
import com.project.zipmin.mapper.CommentMapper;
import com.project.zipmin.mapper.LikeMapper;
import com.project.zipmin.repository.LikeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LikeService {
	
	@Autowired
	private LikeRepository likeRepository;
	
	@Autowired
	private UserService userService;
	
	private final LikeMapper likeMapper;

	
	
	// 좋아요 작성
	public LikeCreateResponseDto createLike(LikeCreateRequestDto likeDto) {
		
		// 입력값 검증
		if (likeDto == null || likeDto.getTablename() == null || likeDto.getRecodenum() == null || likeDto.getUserId() == null) {
			throw new ApiException(LikeErrorCode.LIKE_INVALID_INPUT);
		}
		
	    // 중복 투표 검사
	    if (likeRepository.existsByTablenameAndRecodenumAndUserId(likeDto.getTablename(), likeDto.getRecodenum(), likeDto.getUserId())) {
	    	throw new ApiException(LikeErrorCode.LIKE_DUPLICATE);
	    }
		
		Like like = likeMapper.toEntity(likeDto);
		
		// 좋아요 저장
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
		if (likeDto == null || likeDto.getTablename() == null || likeDto.getRecodenum() == null || likeDto.getUserId() == null) {
			throw new ApiException(LikeErrorCode.LIKE_INVALID_INPUT);
		}
		
		// 좋아요 존재 여부 판단
		Like like = likeRepository.findByTablenameAndRecodenumAndUserId(likeDto.getTablename(), likeDto.getRecodenum(), likeDto.getUserId())
				.orElseThrow(() -> new ApiException(LikeErrorCode.LIKE_NOT_FOUND));
		
		// 소유자 검증
		if (!userService.readUserById(likeDto.getUserId()).getRole().equals(Role.ROLE_ADMIN)) {
			if (like.getUser().getId() != likeDto.getUserId()) {
				throw new ApiException(LikeErrorCode.LIKE_FORBIDDEN);
			}
		}
		
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

		return likeRepository.existsByTablenameAndRecodenumAndUserId(tablename, recodenum, userId);
	}

	
	
	// 테이블 이름과 일련번호를 이용해 좋아요 수 조회
	public long countLikesByTablenameAndRecodenum(String tablename, int recodenum) {
		return likeRepository.countByTablenameAndRecodenum(tablename, recodenum);
	}
	
	
	
	
	
	
	




	
}
