package com.project.zipmin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.zipmin.api.ApiException;
import com.project.zipmin.api.FridgeErrorCode;
import com.project.zipmin.dto.FridgeCreateRequestDto;
import com.project.zipmin.dto.FridgeCreateResponseDto;
import com.project.zipmin.dto.FridgeDeleteRequestDto;
import com.project.zipmin.dto.RecipeReadResponseDto;
import com.project.zipmin.dto.RecipeStockReadResponseDto;
import com.project.zipmin.dto.FridgeReadResponseDto;
import com.project.zipmin.dto.FridgeUpdateRequestDto;
import com.project.zipmin.dto.FridgeUpdateResponseDto;
import com.project.zipmin.entity.Fridge;
import com.project.zipmin.entity.Role;
import com.project.zipmin.mapper.FridgeMapper;
import com.project.zipmin.repository.FridgeRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class FridgeService {
	
	
	@Autowired
	private FridgeRepository fridgeRepository;
	
	@Autowired
	private RecipeService recipeService;
	@Autowired
	private UserService userService;
	
	private final FridgeMapper fridgeMapper;
	
	
	
	// 냉장고 목록 조회
	public List<FridgeReadResponseDto> readFridgeList(Integer userId) {
		
		// 입력값 검증
		if (userId == null) {
			throw new ApiException(FridgeErrorCode.FRIDGE_INVALID_INPUT);
		}
		
		// 냉장고 목록 조회
		List<Fridge> fridgeList;
		try {
			fridgeList = fridgeRepository.findAllByUserId(userId);
		}
		catch (Exception e) {
			throw new ApiException(FridgeErrorCode.FRIDGE_READ_LIST_FAIL);
		}
		
		return fridgeMapper.toReadResponseDtoList(fridgeList);
	}
	
	
	
	// 냉장고 작성
	public FridgeCreateResponseDto createFridge(FridgeCreateRequestDto fridgeDto) {
		
		// 입력값 검증
		if (fridgeDto == null || fridgeDto.getImage() == null || fridgeDto.getName() == null || fridgeDto.getAmount() == null || fridgeDto.getUnit() == null || fridgeDto.getExpdate() == null || fridgeDto.getCategory() == null || fridgeDto.getUserId() == null) {
			throw new ApiException(FridgeErrorCode.FRIDGE_INVALID_INPUT);
		}
		
		Fridge fridge = fridgeMapper.toEntity(fridgeDto);
		
		// 냉장고 저장
		try {
			fridge = fridgeRepository.save(fridge);
			return fridgeMapper.toCreateResponseDto(fridge);
		}
		catch (Exception e) {
			throw new ApiException(FridgeErrorCode.FRIDGE_CREATE_FAIL);
		}
		
	}
	
	
	
	// 냉장고 수정
	public FridgeUpdateResponseDto updateFridge(FridgeUpdateRequestDto fridgeDto) {
		
		// 입력값 검증
		if (fridgeDto == null || fridgeDto.getId() == null || fridgeDto.getImage() == null || fridgeDto.getName() == null || fridgeDto.getAmount() == null || fridgeDto.getUnit() == null || fridgeDto.getExpdate() == null || fridgeDto.getCategory() == null || fridgeDto.getUserId() == null) {
			throw new ApiException(FridgeErrorCode.FRIDGE_INVALID_INPUT);
		}
		
		// 냉장고 존재 여부 판단
		Fridge fridge = fridgeRepository.findById(fridgeDto.getId())
				.orElseThrow(() -> new ApiException(FridgeErrorCode.FRIDGE_NOT_FOUND));
		
		// 소유자 검증
		if (!userService.readUserById(fridgeDto.getUserId()).getRole().equals(Role.ROLE_ADMIN)) {
			if (fridge.getUser().getId() != fridgeDto.getUserId()) {
				throw new ApiException(FridgeErrorCode.FRIDGE_FORBIDDEN);
			}
		}
		
		// 필요한 필드만 수정
		if (fridgeDto.getImage() != null) {
			fridge.setImage(fridge.getImage());
		}
		if (fridgeDto.getName() != null) {
			fridge.setName(fridgeDto.getName());
		}
		if (fridgeDto.getAmount() != null) {
			fridge.setAmount(fridgeDto.getAmount());
		}
		if (fridgeDto.getUnit() != null) {
			fridge.setUnit(fridgeDto.getUnit());
		}
		if (fridgeDto.getExpdate() != null) {
			fridge.setExpdate(fridgeDto.getExpdate());
		}
		if (fridgeDto.getCategory() != null) {
			fridge.setCategory(fridgeDto.getCategory());
		}
		
		// 냉장고 수정
		try {
			fridge = fridgeRepository.save(fridge);
			return fridgeMapper.toUpdateResponseDto(fridge);
		}
		catch (Exception e) {
			throw new ApiException(FridgeErrorCode.FRIDGE_UPDATE_FAIL);
		}
		
	}
	
	
	
	// 냉장고 삭제
	public void deleteFridge(FridgeDeleteRequestDto fridgeDto) {
		
		// 입력값 검증
		if (fridgeDto == null || fridgeDto.getId() == null || fridgeDto.getUserId() == null) {
			throw new ApiException(FridgeErrorCode.FRIDGE_INVALID_INPUT);
		}
		
		// 냉장고 존재 여부 판단
		Fridge fridge = fridgeRepository.findById(fridgeDto.getId())
				.orElseThrow(() -> new ApiException(FridgeErrorCode.FRIDGE_NOT_FOUND));
		
		// 소유자 검증
		if (!userService.readUserById(fridgeDto.getUserId()).getRole().equals(Role.ROLE_ADMIN)) {
			if (fridge.getUser().getId() != fridgeDto.getUserId()) {
				throw new ApiException(FridgeErrorCode.FRIDGE_FORBIDDEN);
			}
		}
		
		// 냉장고 삭제
		try {
			fridgeRepository.deleteById(fridgeDto.getId());
		}
		catch (Exception e) {
			throw new ApiException(FridgeErrorCode.FRIDGE_DELETE_FAIL);
		}
		
	}
	
	
	
	// 냉장고 파먹기
	public List<RecipeReadResponseDto> readPickList(Integer userId) {
		
		// 입력값 검증
		if (userId == null) {
			throw new ApiException(FridgeErrorCode.FRIDGE_INVALID_INPUT);
		}
		
		List<RecipeReadResponseDto> pickDtoList = new ArrayList<>();
		
		// 레시피 목록 조회
		List<RecipeReadResponseDto> recipeDtoList = recipeService.readRecipeList();
		List<FridgeReadResponseDto> fridgeDtoList = readFridgeList(userId);
		
		for (RecipeReadResponseDto recipeDto : recipeDtoList) {
			List<RecipeStockReadResponseDto> stockDtoList = recipeDto.getStockList();
		    long count = stockDtoList.stream()
		            .filter(stock -> fridgeDtoList.stream()
		                .anyMatch(fridge -> fridge.getName().equals(stock.getName()))
		            ).count();
		    
		    double rate = (count * 100.0) / stockDtoList.size();
		    
		    if (rate >= 70.0) {
		    	recipeDto.setRate(rate);
		    	pickDtoList.add(recipeDto);
		    }
		}
		
		return pickDtoList;
	}

}
