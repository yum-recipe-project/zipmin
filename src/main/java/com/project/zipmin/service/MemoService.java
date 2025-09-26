package com.project.zipmin.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.project.zipmin.api.ApiException;
import com.project.zipmin.api.MemoErrorCode;
import com.project.zipmin.dto.MemoCreateRequestDto;
import com.project.zipmin.dto.MemoCreateResponseDto;
import com.project.zipmin.dto.MemoReadResponseDto;
import com.project.zipmin.dto.MemoUpdateRequestDto;
import com.project.zipmin.dto.MemoUpdateResponseDto;
import com.project.zipmin.dto.UserReadResponseDto;
import com.project.zipmin.entity.Memo;
import com.project.zipmin.entity.User;
import com.project.zipmin.mapper.MemoMapper;
import com.project.zipmin.repository.MemoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemoService {
	
	@Autowired
	private final MemoRepository memoRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private final MemoMapper memoMapper;
	
	
	// 장보기 메모 조회
	public List<MemoReadResponseDto> readMemoList(Integer userId) {
	    List<Memo> memoList = memoRepository.findAllByUserId(userId);
	    return memoList.stream()
	            .map(memoMapper::toReadResponseDto)
	            .collect(Collectors.toList());
	}


	
	
	// 장보기 메모 작성
	public MemoCreateResponseDto createMemo(MemoCreateRequestDto memoRequestDto) {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		memoRequestDto.setUserId(userService.readUserByUsername(username).getId());
		
		// 입력값 검증
	    if (memoRequestDto == null 
	            || memoRequestDto.getName() == null 
	            || memoRequestDto.getAmount() == null)
	        throw new ApiException(MemoErrorCode.MEMO_INVALID_INPUT);
	    
	    // 엔티티 변환 및 저장
	    Memo memo = memoMapper.toEntity(memoRequestDto);
	    
	    try {
	        memo = memoRepository.save(memo);
	        return memoMapper.toCreateResponseDto(memo);
	    } catch (Exception e) {
	        throw new ApiException(MemoErrorCode.MEMO_CREATE_FAIL);
	    }
	}
	
	
	
	
	// 댓글을 수정하는 함수
	public MemoUpdateResponseDto updateMemo(int memoId, MemoUpdateRequestDto memoDto) {
		

	    // 입력값 검증
	    if (memoDto == null || memoDto.getName() == null) {
	        throw new ApiException(MemoErrorCode.MEMO_INVALID_INPUT);
	    }

	    // 메모 존재 여부 확인
	    Memo memo = memoRepository.findById(memoId)
	            .orElseThrow(() -> new ApiException(MemoErrorCode.MEMO_NOT_FOUND));

	    // 작성자 확인 (본인만 수정 가능)
	    String username = SecurityContextHolder.getContext().getAuthentication().getName();
	    UserReadResponseDto user = userService.readUserByUsername(username);
	    
	    if (memo.getUser().getId() != user.getId()) {
	        throw new ApiException(MemoErrorCode.MEMO_FORBIDDEN);
	    }

	    // 변경 값 설정
	    memo.setName(memoDto.getName());
	    memo.setAmount(memoDto.getAmount());
	    memo.setUnit(memoDto.getUnit());
	    memo.setNote(memoDto.getNote());

	    // 메모 수정
	    try {
	        memo = memoRepository.save(memo);
	        return memoMapper.toUpdateResponseDto(memo);
	    } catch (Exception e) {
	        throw new ApiException(MemoErrorCode.MEMO_UPDATE_FAIL);
	    }
	}

	
	
}
