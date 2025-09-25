package com.project.zipmin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.project.zipmin.api.ApiException;
import com.project.zipmin.api.KitchenErrorCode;
import com.project.zipmin.api.MemoErrorCode;
import com.project.zipmin.dto.MemoReadResponseDto;
import com.project.zipmin.entity.Memo;
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
	
	
	// 메모 목록 조회
	public Page<MemoReadResponseDto> readMemoPage(Integer userId, Pageable pageable) {
		
		System.err.println("메모 서비스 진입");
		
		// 입력값 검증
		if (pageable == null) {
			throw new ApiException(MemoErrorCode.MEMO_INVALID_INPUT);
		}
		
		// 메모 목록 조회
		Page<Memo> memoPage;
		try {
			memoPage = memoRepository.findAllByUserId(userId, pageable);
		}
		catch (Exception e) {
			throw new ApiException(MemoErrorCode.MEMO_READ_LIST_FAIL);
		}
		
		// dto 변경
		List<MemoReadResponseDto> memoDtoList = new ArrayList<MemoReadResponseDto>();
		for (Memo memo : memoPage) {
			MemoReadResponseDto memoDto = memoMapper.toReadResponseDto(memo);
			
			memoDtoList.add(memoDto);
		}
		
		
		return new PageImpl<>(memoDtoList, pageable, memoPage.getTotalElements());
	}
	
	
	
	
	
	
	
	
	
}
