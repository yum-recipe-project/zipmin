package com.project.zipmin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.project.zipmin.api.ApiException;
import com.project.zipmin.api.FridgeErrorCode;
import com.project.zipmin.dto.FridgeCreateRequestDto;
import com.project.zipmin.dto.FridgeCreateResponseDto;
import com.project.zipmin.dto.FridgeReadResponseDto;
import com.project.zipmin.dto.FridgeUpdateRequestDto;
import com.project.zipmin.dto.FridgeUpdateResponseDto;
import com.project.zipmin.dto.UserFridgeReadResponseDto;
import com.project.zipmin.dto.UserReadResponseDto;
import com.project.zipmin.entity.Fridge;
import com.project.zipmin.entity.Role;
import com.project.zipmin.entity.User;
import com.project.zipmin.entity.UserFridge;
import com.project.zipmin.mapper.FridgeMapper;
import com.project.zipmin.mapper.UserFridgeMapper;
import com.project.zipmin.repository.FridgeRepository;
import com.project.zipmin.repository.UserFridgeRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class FridgeService {
	
	private final FridgeRepository fridgeRepository;
	private final UserFridgeRepository userFridgeRepository;
	
	private final RecipeService recipeService;
	private final UserService userService;
	private final FileService fileService;
	
	private final FridgeMapper fridgeMapper;
	private final UserFridgeMapper userFridgeMapper;
	
	@Value("${app.upload.public-path:/files}")
	private String publicPath;
	
	
	
	
	
	// 냉장고 목록 조회
	public Page<FridgeReadResponseDto> readFridgePage(String category, String keyword, String sort, Pageable pageable) {
		
		// 입력값 검증
		if (pageable == null) {
			throw new ApiException(FridgeErrorCode.FRIDGE_INVALID_INPUT);
		}
		
		// 정렬 문자열을 객체로 변환
		Sort sortSpec = Sort.by(Sort.Order.desc("id"));
		if (sort != null && !sort.isBlank()) {
			switch (sort) {
				case "id-desc":
					sortSpec = Sort.by(Sort.Order.desc("id"));
					break;
				case "id-asc":
					sortSpec = Sort.by(Sort.Order.asc("id"));
					break;
				case "name-desc":
					sortSpec = Sort.by(Sort.Order.desc("name"), Sort.Order.desc("id"));
					break;
				case "name-asc":
					sortSpec = Sort.by(Sort.Order.asc("name"), Sort.Order.desc("id"));
					break;
				default:
					break;
				// ***** TODO : 사용된 횟수 추가하기 *****
			}
		}
		
		// 기존 페이지 객체에 정렬 주입
		Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sortSpec);
		
		// 냉장고 목록 조회
		Page<Fridge> fridgePage;
		try {
			boolean hasCategory = category != null && !category.isBlank();
			boolean hasKeyword = keyword != null && !keyword.isBlank();
			
			if (!hasCategory) {
				// 전체
				fridgePage = hasKeyword
						? fridgeRepository.findAllByNameContainingIgnoreCase(keyword, sortedPageable)
						: fridgeRepository.findAll(sortedPageable);
			}
			else {
				// 카테고리
				fridgePage = hasKeyword
						? fridgeRepository.findAllByCategoryAndNameContainingIgnoreCase(category, keyword, sortedPageable)
						: fridgeRepository.findAllByCategory(category, sortedPageable);
			}
		}
		catch (Exception e) {
			throw new ApiException(FridgeErrorCode.FRIDGE_READ_LIST_FAIL);
		}
		
		// 냉장고 목록 응답 구성
		List<FridgeReadResponseDto> fridgeDtoList = new ArrayList<>();
		for (Fridge fridge : fridgePage) {
			FridgeReadResponseDto fridgeDto = fridgeMapper.toReadResponseDto(fridge);
			
			// 이미지
			if (fridgeDto.getImage() != null ) {
				fridgeDto.setImage(publicPath + "/" + fridgeDto.getImage());
			}
			
			fridgeDtoList.add(fridgeDto);
		}
		
		return new PageImpl<>(fridgeDtoList, pageable, fridgePage.getTotalElements());
	}
	
	
	
	
	
	// 냉장고 작성
	public FridgeCreateResponseDto createFridge(FridgeCreateRequestDto fridgeDto) {
		
		// 입력값 검증
		if (fridgeDto == null || fridgeDto.getImage() == null || fridgeDto.getName() == null
				|| fridgeDto.getCategory() == null || fridgeDto.getUserId() == null) {
			throw new ApiException(FridgeErrorCode.FRIDGE_INVALID_INPUT);
		}
		
		// 냉장고 저장
		Fridge fridge = fridgeMapper.toEntity(fridgeDto);
		try {
			fridge = fridgeRepository.save(fridge);
			return fridgeMapper.toCreateResponseDto(fridge);
		}
		catch (Exception e) {
			throw new ApiException(FridgeErrorCode.FRIDGE_CREATE_FAIL);
		}
		
	}
	
	
	
	
	
	// 냉장고 수정 (관리자)
	public FridgeUpdateResponseDto updateFridge(FridgeUpdateRequestDto fridgeDto, MultipartFile file) {
		
		// 입력값 검증
		if (fridgeDto == null || fridgeDto.getId() == null
				|| fridgeDto.getImage() == null || fridgeDto.getName() == null
				||  fridgeDto.getCategory() == null || fridgeDto.getUserId() == null) {
			throw new ApiException(FridgeErrorCode.FRIDGE_INVALID_INPUT);
		}
		
		// 냉장고 존재 여부 확인
		Fridge fridge = fridgeRepository.findById(fridgeDto.getId())
				.orElseThrow(() -> new ApiException(FridgeErrorCode.FRIDGE_NOT_FOUND));
		
		// 권한 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		if (!userService.readUserByUsername(username).getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			// 관리자
			if (userService.readUserByUsername(username).getRole().equals(Role.ROLE_ADMIN.name())) {
				if (fridge.getUser().getRole().equals(Role.ROLE_SUPER_ADMIN)) {
					throw new ApiException(FridgeErrorCode.FRIDGE_FORBIDDEN);
				}
				if (fridge.getUser().getRole().equals(Role.ROLE_ADMIN)) {
					if (userService.readUserByUsername(username).getId() != fridge.getUser().getId()) {
						throw new ApiException(FridgeErrorCode.FRIDGE_FORBIDDEN);
					}
				}
			}
			// 일반 회원
			else {
				if (userService.readUserByUsername(username).getId() != fridge.getUser().getId()) {
					throw new ApiException(FridgeErrorCode.FRIDGE_FORBIDDEN);
				}
			}
		}
		
		// 변경 값 설정
		fridge.setName(fridgeDto.getName());
		fridge.setCategory(fridgeDto.getCategory());
		
		// 파일 저장
		try {
			if (file != null && !file.isEmpty()) {
				String image = fileService.store(file);
				fridge.setImage(image);
			}
		}
		catch (Exception e) {
			throw new ApiException(FridgeErrorCode.FRIDGE_FILE_UPLOAD_FAIL);
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
	public void deleteFridge(Integer id) {
		
		// 입력값 검증
		if (id == null) {
			throw new ApiException(FridgeErrorCode.FRIDGE_INVALID_INPUT);
		}
		
		// 냉장고 존재 여부 확인
		Fridge fridge = fridgeRepository.findById(id)
				.orElseThrow(() -> new ApiException(FridgeErrorCode.FRIDGE_NOT_FOUND));
		
		// 권한 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		if (!userService.readUserByUsername(username).getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			// 관리자
			if (userService.readUserByUsername(username).getRole().equals(Role.ROLE_ADMIN.name())) {
				if (fridge.getUser().getRole().equals(Role.ROLE_SUPER_ADMIN)) {
					throw new ApiException(FridgeErrorCode.FRIDGE_FORBIDDEN);
				}
				if (fridge.getUser().getRole().equals(Role.ROLE_ADMIN)) {
					if (userService.readUserByUsername(username).getId() != fridge.getUser().getId()) {
						throw new ApiException(FridgeErrorCode.FRIDGE_FORBIDDEN);
					}
				}
			}
			// 일반 회원
			else {
				if (userService.readUserByUsername(username).getId() != fridge.getUser().getId()) {
					throw new ApiException(FridgeErrorCode.FRIDGE_FORBIDDEN);
				}
			}
		}
		
		// 냉장고 삭제
		try {
			fridgeRepository.deleteById(id);
		}
		catch (Exception e) {
			throw new ApiException(FridgeErrorCode.FRIDGE_DELETE_FAIL);
		}
		
	}
	
	
	
	
	// 사용자 냉장고 목록 조회
	public List<UserFridgeReadResponseDto> readUserFridgeList(Integer id) {
		
		// 입력값 검증
		if (id == null) {
			throw new ApiException(FridgeErrorCode.USER_FRIDGE_INVALID_INPUT);
		}
		
		// 권한 확인
		UserReadResponseDto userDto = userService.readUserById(id);
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		if (!userService.readUserByUsername(username).getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			// 관리자
			if (userService.readUserByUsername(username).getRole().equals(Role.ROLE_ADMIN.name())) {
				if (userDto.getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
					throw new ApiException(FridgeErrorCode.USER_FRIDGE_FORBIDDEN);
				}
				if (userDto.getRole().equals(Role.ROLE_ADMIN.name())) {
					if (userService.readUserByUsername(username).getId() != userDto.getId()) {
						throw new ApiException(FridgeErrorCode.USER_FRIDGE_FORBIDDEN);
					}
				}
			}
			// 일반 회원
			else {
				if (userService.readUserByUsername(username).getId() != userDto.getId()) {
					throw new ApiException(FridgeErrorCode.USER_FRIDGE_FORBIDDEN);
				}
			}
		}
		
		// 사용자 냉장고 목록 조회
		List<UserFridge> userFridgeList;
		try {
			userFridgeList = userFridgeRepository.findAllByUserId(id);
		}
		catch (Exception e) {
			throw new ApiException(FridgeErrorCode.USER_FRIDGE_READ_LIST_FAIL);
		}
		
		// 사용자 냉장고 목록 응답 구성
		List<UserFridgeReadResponseDto> userFridgeDtoList = new ArrayList<UserFridgeReadResponseDto>();
		for (UserFridge userFridge : userFridgeList) {
			UserFridgeReadResponseDto userFridgeDto = userFridgeMapper.toReadResponseDto(userFridge);
			
			userFridgeDto.setName(userFridge.getFridge().getName());
			userFridgeDto.setImage(publicPath + "/" + userFridge.getFridge().getImage());
			userFridgeDto.setCategory(userFridge.getFridge().getCategory());
			
			userFridgeDtoList.add(userFridgeDto);
		}
		
		return userFridgeDtoList;
	}
	
	
	
	
	
	// 냉장고 파먹기
	/*
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
	*/

}
