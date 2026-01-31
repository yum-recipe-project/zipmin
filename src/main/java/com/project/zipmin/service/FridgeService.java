package com.project.zipmin.service;

import static org.hamcrest.CoreMatchers.nullValue;

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
import com.project.zipmin.api.FridgeErrorCode;
import com.project.zipmin.api.MemoErrorCode;
import com.project.zipmin.dto.UserReadResponseDto;
import com.project.zipmin.dto.fridge.FridgeCreateRequestDto;
import com.project.zipmin.dto.fridge.FridgeCreateResponseDto;
import com.project.zipmin.dto.fridge.FridgeReadResponseDto;
import com.project.zipmin.dto.fridge.FridgeUpdateRequestDto;
import com.project.zipmin.dto.fridge.FridgeUpdateResponseDto;
import com.project.zipmin.dto.fridge.MemoCreateRequestDto;
import com.project.zipmin.dto.fridge.MemoCreateResponseDto;
import com.project.zipmin.dto.fridge.MemoReadResponseDto;
import com.project.zipmin.dto.fridge.MemoUpdateRequestDto;
import com.project.zipmin.dto.fridge.MemoUpdateResponseDto;
import com.project.zipmin.dto.fridge.UserFridgeCreateRequestDto;
import com.project.zipmin.dto.fridge.UserFridgeCreateResponseDto;
import com.project.zipmin.dto.fridge.UserFridgeReadResponseDto;
import com.project.zipmin.dto.fridge.UserFridgeUpdateRequestDto;
import com.project.zipmin.dto.fridge.UserFridgeUpdateResponseDto;
import com.project.zipmin.dto.like.LikeCreateRequestDto;
import com.project.zipmin.dto.like.LikeCreateResponseDto;
import com.project.zipmin.dto.like.LikeDeleteRequestDto;
import com.project.zipmin.dto.like.LikeReadResponseDto;
import com.project.zipmin.dto.recipe.RecipeReadResponseDto;
import com.project.zipmin.dto.recipe.RecipeStockReadResponseDto;
import com.project.zipmin.entity.Fridge;
import com.project.zipmin.entity.FridgeMemo;
import com.project.zipmin.entity.Role;
import com.project.zipmin.entity.UserFridge;
import com.project.zipmin.mapper.FridgeMapper;
import com.project.zipmin.mapper.FridgeMemoMapper;
import com.project.zipmin.mapper.UserFridgeMapper;
import com.project.zipmin.repository.FridgeMemoRepository;
import com.project.zipmin.repository.FridgeRepository;
import com.project.zipmin.repository.UserFridgeRepository;

import io.jsonwebtoken.lang.Collections;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class FridgeService {
	
	private final FridgeRepository fridgeRepository;
	private final UserFridgeRepository userFridgeRepository;
	private final FridgeMemoRepository memoRepository;
	private final FridgeMapper fridgeMapper;
	private final UserFridgeMapper userFridgeMapper;
	private final FridgeMemoMapper memoMapper;
	private final RecipeService recipeService;
	private final UserService userService;
	private final LikeService likeService;
	
	
	
	
	
	// 냉장고 목록 조회
	public Page<FridgeReadResponseDto> readFridgePage(String keyword, String category, String sort, Pageable pageable) {
		
		// 입력값 검증
		if (pageable == null) {
			throw new ApiException(FridgeErrorCode.FRIDGE_INVALID_INPUT);
		}
		
		// 정렬
		Sort order = Sort.by(Sort.Order.desc("id"));
		if (sort != null && !sort.isBlank()) {
			String field = sort.split("-")[0];
			Direction direction = "asc".equals(sort.split("-")[1]) ? Direction.ASC : Direction.DESC;
			order = Sort.by(new Order(direction, field), Order.desc("id"));
		}
		pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), order);
		
		// 냉장고 목록 조회
		Page<Fridge> fridgePage;
		try {
			boolean hasKeyword = keyword != null && !keyword.isBlank();
			boolean hasCategory = category != null && !category.isBlank();
			
			if (!hasCategory) {
				fridgePage = hasKeyword
						? fridgeRepository.findAllByNameContainingIgnoreCase(keyword, pageable)
						: fridgeRepository.findAll(pageable);
			}
			else {
				fridgePage = hasKeyword
						? fridgeRepository.findAllByCategoryAndNameContainingIgnoreCase(category, keyword, pageable)
						: fridgeRepository.findAllByCategory(category, pageable);
			}
		}
		catch (Exception e) {
			throw new ApiException(FridgeErrorCode.FRIDGE_READ_LIST_FAIL);
		}
		
		// 냉장고 목록 응답 구성
		List<FridgeReadResponseDto> fridgeDtoList = new ArrayList<>();
		for (Fridge fridge : fridgePage) {
			FridgeReadResponseDto fridgeDto = fridgeMapper.toReadResponseDto(fridge);
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {
				UserReadResponseDto userDto = userService.readUserByUsername(authentication.getName());
				fridgeDto.setLiked(likeService.existLike("fridge", fridgeDto.getId(), userDto.getId()));
			}
			fridgeDtoList.add(fridgeDto);
		}
		
		return new PageImpl<>(fridgeDtoList, pageable, fridgePage.getTotalElements());
	}
	
	
	
	
	
	// 사용자의 냉장고 목록 조회
	public List<FridgeReadResponseDto> readFridgePageByUserId(int userId) {
		
		// 입력값 검증
		if (userId == 0) {
			throw new ApiException(FridgeErrorCode.FRIDGE_INVALID_INPUT);
		}
		
		// 권한 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserReadResponseDto currentUser = userService.readUserByUsername(username);
		
		if (!currentUser.getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			if (currentUser.getRole().equals(Role.ROLE_ADMIN.name())) {
				if (userService.readUserById(userId).getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
					throw new ApiException(FridgeErrorCode.FRIDGE_FORBIDDEN);
				}
				if (userService.readUserById(userId).getRole().equals(Role.ROLE_ADMIN.name()) && currentUser.getId() != userId) {
					throw new ApiException(FridgeErrorCode.FRIDGE_FORBIDDEN);
				}
			}
			else if (currentUser.getRole().equals(Role.ROLE_USER.name()) && currentUser.getId() != userId) {
				throw new ApiException(FridgeErrorCode.FRIDGE_FORBIDDEN);
			}
		}
		
		// 냉장고 목록 조회
		List<Fridge> fridgeList;
		try {
			fridgeList = fridgeRepository.findAllByUserId(userId);
		}
		catch (Exception e) {
			throw new ApiException(FridgeErrorCode.FRIDGE_READ_LIST_FAIL);
		}
		
		// 냉장고 목록 응답 구성
		List<FridgeReadResponseDto> fridgeDtoList = new ArrayList<>();
		for (Fridge fridge : fridgeList) {
			FridgeReadResponseDto fridgeDto = fridgeMapper.toReadResponseDto(fridge);
			fridgeDto.setLiked(likeService.existLike("fridge", fridgeDto.getId(), userId));
			fridgeDtoList.add(fridgeDto);
		}
		
		return fridgeDtoList;
	}
	
	
	
	
	
	// 좋아요한 냉장고 목록 조회
	public List<FridgeReadResponseDto> readLikedFridgeListByUserId(int userId) {
		
		// 입력값 검증
		if (userId == 0) {
			throw new ApiException(FridgeErrorCode.FRIDGE_INVALID_INPUT);
		}
		
		// 좋아요한 냉장고 일련번호 추출
		List<LikeReadResponseDto> likeDtoList = likeService.readLikeListByTablenameAndUserId("fridge", userId);
		List<Integer> fridgeIdList = likeDtoList.stream()
				.map(LikeReadResponseDto::getRecodenum)
				.toList();
		
		if (fridgeIdList.isEmpty()) {
			return Collections.emptyList();
		}
		
		// 냉장고 목록 조회
		List<Fridge> fridgeList;
		try {
			fridgeList = fridgeRepository.findAllByIdIn(fridgeIdList);
		}
		catch (Exception e) {
			throw new ApiException(FridgeErrorCode.FRIDGE_READ_LIST_FAIL);
		}
		
		// 냉장고 목록 응답 구성
		List<FridgeReadResponseDto> fridgeDtoList = new ArrayList<>();
		for (Fridge fridge : fridgeList) {
			FridgeReadResponseDto fridgeDto = fridgeMapper.toReadResponseDto(fridge);
			fridgeDto.setLiked(true);
			fridgeDtoList.add(fridgeDto);
		}
		
		return fridgeDtoList;
	}
	
	
	
	
	
	// 나의 냉장고 목록 조회
	public List<UserFridgeReadResponseDto> readUserFridgeList(int userId) {
		
		// 입력값 검증
		if (userId == 0) {
			throw new ApiException(FridgeErrorCode.FRIDGE_INVALID_INPUT);
		}
		
		// 권한 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserReadResponseDto currentUser = userService.readUserByUsername(username);
		
		if (!currentUser.getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			if (currentUser.getRole().equals(Role.ROLE_ADMIN.name())) {
				if (userService.readUserById(userId).getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
					throw new ApiException(FridgeErrorCode.FRIDGE_FORBIDDEN);
				}
				if (userService.readUserById(userId).getRole().equals(Role.ROLE_ADMIN.name()) && currentUser.getId() != userId) {
					throw new ApiException(FridgeErrorCode.FRIDGE_FORBIDDEN);
				}
			}
			else if (currentUser.getRole().equals(Role.ROLE_USER.name()) && currentUser.getId() != userId) {
				throw new ApiException(FridgeErrorCode.FRIDGE_FORBIDDEN);
			}
		}
		
		// 사용자 냉장고 목록 조회
		List<UserFridge> userFridgeList;
		try {
			userFridgeList = userFridgeRepository.findAllByUserId(userId);
		}
		catch (Exception e) {
			throw new ApiException(FridgeErrorCode.FRIDGE_READ_LIST_FAIL);
		}
		
		// 사용자 냉장고 목록 응답 구성
		List<UserFridgeReadResponseDto> userFridgeDtoList = new ArrayList<>();
		for (UserFridge userFridge : userFridgeList) {
			UserFridgeReadResponseDto userFridgeDto = userFridgeMapper.toReadResponseDto(userFridge);
			userFridgeDto.setName(userFridge.getFridge().getName());
			userFridgeDto.setImage(userFridge.getFridge().getImage());
			userFridgeDto.setCategory(userFridge.getFridge().getCategory());
			userFridgeDto.setZone(userFridge.getFridge().getZone());
			userFridgeDtoList.add(userFridgeDto);
		}
		
		return userFridgeDtoList;
	}
	
	
	
	
	// 장보기 메모 목록 조회
	public List<MemoReadResponseDto> readMemoList(int userId) {
		
		// 입력값 검증
		if (userId == 0) {
			throw new ApiException(FridgeErrorCode.FRIDGE_INVALID_INPUT);
		}
		
		// 권한 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserReadResponseDto currentUser = userService.readUserByUsername(username);
		
		if (!currentUser.getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			if (currentUser.getRole().equals(Role.ROLE_ADMIN.name())) {
				if (userService.readUserById(userId).getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
					throw new ApiException(FridgeErrorCode.FRIDGE_FORBIDDEN);
				}
				if (userService.readUserById(userId).getRole().equals(Role.ROLE_ADMIN.name()) && currentUser.getId() != userId) {
					throw new ApiException(FridgeErrorCode.FRIDGE_FORBIDDEN);
				}
			}
			else if (currentUser.getRole().equals(Role.ROLE_USER.name()) && currentUser.getId() != userId) {
				throw new ApiException(FridgeErrorCode.FRIDGE_FORBIDDEN);
			}
		}
		
		// 장보기 메모 목록 조회
		List<FridgeMemo> memoList;
		try {
			memoList = memoRepository.findAllByUserId(userId);
			return memoMapper.toReadResponseDtoList(memoList);
		}
		catch (Exception e) {
			throw new ApiException(MemoErrorCode.MEMO_READ_LIST_FAIL);
		}
	}
	
	
	
	
	
	// 냉장고 파먹기 목록 조회
	// TODO : 다시 구현
	public List<RecipeReadResponseDto> readPickList(int userId) {
		
		// 입력값 검증
		if (userId == 0) {
			throw new ApiException(FridgeErrorCode.FRIDGE_INVALID_INPUT);
		}
		
		List<RecipeReadResponseDto> pickDtoList = new ArrayList<>();
		
		// 레시피 목록 조회
		List<RecipeReadResponseDto> recipeDtoList = recipeService.readRecipeList();
		List<UserFridgeReadResponseDto> userFridgeDtoList = readUserFridgeList(userId);
		
		for (RecipeReadResponseDto recipeDto : recipeDtoList) {
			List<RecipeStockReadResponseDto> stockDtoList = recipeDto.getStockList();
			long count = stockDtoList.stream()
					.filter(stock -> userFridgeDtoList.stream()
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
	
	
	
	
	
	// 냉장고 작성
	public FridgeCreateResponseDto createFridge(FridgeCreateRequestDto fridgeRequestDto) {
		
		// 입력값 검증
		if (fridgeRequestDto == null
				|| fridgeRequestDto.getImage() == null
				|| fridgeRequestDto.getName() == null
				|| fridgeRequestDto.getCategory() == null
				|| fridgeRequestDto.getZone() == null
				|| fridgeRequestDto.getUserId() == 0) {
			throw new ApiException(FridgeErrorCode.FRIDGE_INVALID_INPUT);
		}
		
		// 냉장고 작성
		try {
			Fridge fridge = fridgeMapper.toEntity(fridgeRequestDto);
			fridge = fridgeRepository.save(fridge);
			return fridgeMapper.toCreateResponseDto(fridge);
		}
		catch (Exception e) {
			throw new ApiException(FridgeErrorCode.FRIDGE_CREATE_FAIL);
		}
		
	}
	
	
	
	
	
	// 나의 냉장고 작성
	public UserFridgeCreateResponseDto createUserFridge(UserFridgeCreateRequestDto userFridgeDto) {
		
		// 입력값 검증
		if (userFridgeDto == null
				|| userFridgeDto.getAmount() == 0
				|| userFridgeDto.getUnit() == null
				|| userFridgeDto.getExpdate() == null
				|| userFridgeDto.getFridgeId() == 0) {
			throw new ApiException(FridgeErrorCode.FRIDGE_INVALID_INPUT);
		}
		
		// 나의 냉장고 작성
		try {
			UserFridge userFridge = userFridgeMapper.toEntity(userFridgeDto);
			userFridge = userFridgeRepository.save(userFridge);
			return userFridgeMapper.toCreateResponseDto(userFridge);
		}
		catch (Exception e) {
			throw new ApiException(FridgeErrorCode.FRIDGE_CREATE_FAIL);
		}
	}
	
	
	
	
	
	// 장보기 메모 작성
	public MemoCreateResponseDto createMemo(MemoCreateRequestDto memoRequestDto) {
		
		// 입력값 검증
		if (memoRequestDto == null
				|| memoRequestDto.getName() == null
				|| memoRequestDto.getAmount() == 0
				|| memoRequestDto.getUnit() == null
				|| memoRequestDto.getUserId() == 0) {
			throw new ApiException(FridgeErrorCode.FRIDGE_MEMO_INVALID_INPUT);
		}
		
		// 장보기 메모 작성
		try {
			FridgeMemo memo = memoMapper.toEntity(memoRequestDto);
			memo = memoRepository.save(memo);
			return memoMapper.toCreateResponseDto(memo);
		}
		catch (Exception e) {
			throw new ApiException(FridgeErrorCode.FRDIGE_MEMO_CREATE_FAIL);
		}
		
	}
	
	
	
	
	
	// 냉장고 수정
	public FridgeUpdateResponseDto updateFridge(FridgeUpdateRequestDto fridgeDto) {
		
		// 입력값 검증
		if (fridgeDto == null
				|| fridgeDto.getId() == 0
				|| fridgeDto.getImage() == null
				|| fridgeDto.getName() == null
				|| fridgeDto.getCategory() == null
				|| fridgeDto.getZone() == null
				|| fridgeDto.getUserId() == 0) {
			throw new ApiException(FridgeErrorCode.FRIDGE_INVALID_INPUT);
		}
		
		// 냉장고 조회
		Fridge fridge;
		try {
			fridge = fridgeRepository.findById(fridgeDto.getId());
		}
		catch (Exception e) {
			throw new ApiException(FridgeErrorCode.FRIDGE_NOT_FOUND);
		}
		
		// 권한 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserReadResponseDto currentUser = userService.readUserByUsername(username);
		
		if (!currentUser.getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			if (currentUser.getRole().equals(Role.ROLE_ADMIN.name())) {
				if (fridge.getUser().getRole().equals(Role.ROLE_SUPER_ADMIN)) {
					throw new ApiException(FridgeErrorCode.FRIDGE_FORBIDDEN);
				}
				if (fridge.getUser().getRole().equals(Role.ROLE_ADMIN) && currentUser.getId() != fridge.getUser().getId()) {
					throw new ApiException(FridgeErrorCode.FRIDGE_FORBIDDEN);
				}
			}
			else if (currentUser.getRole().equals(Role.ROLE_USER.name()) && currentUser.getId() != fridge.getUser().getId()) {
				throw new ApiException(FridgeErrorCode.FRIDGE_FORBIDDEN);
			}
		}
		
		// 값 설정
		fridge.setName(fridgeDto.getName());
		fridge.setCategory(fridgeDto.getCategory());
		fridge.setZone(fridgeDto.getZone());
		fridge.setImage(fridgeDto.getImage());
		
		// 냉장고 수정
		try {
			fridge = fridgeRepository.save(fridge);
			return fridgeMapper.toUpdateResponseDto(fridge);
		}
		catch (Exception e) {
			throw new ApiException(FridgeErrorCode.FRIDGE_UPDATE_FAIL);
		}
		
	}
	
	
	
	
	
	// 나의 냉장고 수정
	public UserFridgeUpdateResponseDto updateUserFridge(UserFridgeUpdateRequestDto userFridgeRequestDto) {
		
		// 입력값 검증
		if (userFridgeRequestDto == null
				|| userFridgeRequestDto.getId() == 0
				|| userFridgeRequestDto.getAmount() == 0
				|| userFridgeRequestDto.getUnit() == null
				|| userFridgeRequestDto.getExpdate() == null
				|| userFridgeRequestDto.getFridgeId() == 0
				|| userFridgeRequestDto.getUserId() == 0) {
			throw new ApiException(FridgeErrorCode.FRIDGE_INVALID_INPUT);
		}
		
		// 나의 냉장고 조회
		UserFridge userFridge;
		try {
			userFridge = userFridgeRepository.findById(userFridgeRequestDto.getId());
		}
		catch (Exception e) {
			throw new ApiException(FridgeErrorCode.FRIDGE_NOT_FOUND);
		}
		
		// 권한 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserReadResponseDto currentUser = userService.readUserByUsername(username);
		
		if (!currentUser.getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			if (currentUser.getRole().equals(Role.ROLE_ADMIN.name())) {
				if (userFridge.getUser().getRole().equals(Role.ROLE_SUPER_ADMIN)) {
					throw new ApiException(FridgeErrorCode.FRIDGE_FORBIDDEN);
				}
				if (userFridge.getUser().getRole().equals(Role.ROLE_ADMIN) && currentUser.getId() != userFridge.getUser().getId()) {
					throw new ApiException(FridgeErrorCode.FRIDGE_FORBIDDEN);
				}
			}
			else if (currentUser.getRole().equals(Role.ROLE_USER.name()) && currentUser.getId() != userFridge.getUser().getId()) {
				throw new ApiException(FridgeErrorCode.FRIDGE_FORBIDDEN);
			}
		}
		
		// 값 설정
		userFridge.setAmount(userFridgeRequestDto.getAmount());
		userFridge.setUnit(userFridgeRequestDto.getUnit());
		userFridge.setExpdate(userFridgeRequestDto.getExpdate());
		
		// 나의 냉장고 수정
		try {
			userFridge = userFridgeRepository.save(userFridge);
			return userFridgeMapper.toUpdateResponseDto(userFridge);
		}
		catch (Exception e) {
			throw new ApiException(FridgeErrorCode.FRIDGE_UPDATE_FAIL);
		}
	}
	
	
	
	
	
	// 장보기 메모 수정
	public MemoUpdateResponseDto updateMemo(MemoUpdateRequestDto memoRequestDto) {
		
		// 입력값 검증
		if (memoRequestDto == null
				|| memoRequestDto.getName() == null
				|| memoRequestDto.getAmount() == 0
				|| memoRequestDto.getUnit() == null
				|| memoRequestDto.getUserId() == 0) {
			throw new ApiException(FridgeErrorCode.FRIDGE_MEMO_INVALID_INPUT);
		}
		
		// 장보기 메모 조회
		FridgeMemo memo;
		try {
			memo = memoRepository.findById(memoRequestDto.getId())
		}
		catch (Exception e) {
			throw new ApiException(FridgeErrorCode.FRIDGE_MEMO_NOT_FOUND);
		}
		
	}
	
	
	
	
	// 냉장고 삭제
	public void deleteFridge(int id) {
		
		// 입력값 검증
		if (id == 0) {
			throw new ApiException(FridgeErrorCode.FRIDGE_INVALID_INPUT);
		}
		
		// 냉장고 조회
		Fridge fridge;
		try {
			fridge = fridgeRepository.findById(id);
		}
		catch (Exception e) {
			throw new ApiException(FridgeErrorCode.FRIDGE_NOT_FOUND);
		}
		
		// 권한 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserReadResponseDto currentUser = userService.readUserByUsername(username);
		
		if (!currentUser.getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			if (currentUser.getRole().equals(Role.ROLE_ADMIN.name())) {
				if (fridge.getUser().getRole().equals(Role.ROLE_SUPER_ADMIN)) {
					throw new ApiException(FridgeErrorCode.FRIDGE_FORBIDDEN);
				}
				if (fridge.getUser().getRole().equals(Role.ROLE_ADMIN) && currentUser.getId() != fridge.getUser().getId()) {
					throw new ApiException(FridgeErrorCode.FRIDGE_FORBIDDEN);
				}
			}
			else if (currentUser.getRole().equals(Role.ROLE_USER.name()) && currentUser.getId() != fridge.getUser().getId()) {
				throw new ApiException(FridgeErrorCode.FRIDGE_FORBIDDEN);
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
	
	
	
	
	
	// 나의 냉장고 삭제
	public void deleteUserFridge(int id) {
		
		// 입력값 검증
		if (id == 0) {
			throw new ApiException(FridgeErrorCode.FRIDGE_INVALID_INPUT);
		}
		
		// 나의 냉장고 조회
		UserFridge userFridge;
		try {
			userFridge = userFridgeRepository.findById(id);
		}
		catch (Exception e) {
			throw new ApiException(FridgeErrorCode.FRIDGE_NOT_FOUND);
		}
		
		// 권한 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserReadResponseDto currentUser = userService.readUserByUsername(username);
		
		if (!currentUser.getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			if (currentUser.getRole().equals(Role.ROLE_ADMIN.name())) {
				if (userFridge.getUser().getRole().equals(Role.ROLE_SUPER_ADMIN)) {
					throw new ApiException(FridgeErrorCode.FRIDGE_FORBIDDEN);
				}
				if (userFridge.getUser().getRole().equals(Role.ROLE_ADMIN) && currentUser.getId() != userFridge.getUser().getId()) {
					throw new ApiException(FridgeErrorCode.FRIDGE_FORBIDDEN);
				}
			}
			else if (currentUser.getRole().equals(Role.ROLE_USER.name()) && currentUser.getId() != userFridge.getUser().getId()) {
				throw new ApiException(FridgeErrorCode.FRIDGE_FORBIDDEN);
			}
		}
		
		// 사용자 냉장고 삭제
		try {
			userFridgeRepository.deleteById(id);
		}
		catch (Exception e) {
			throw new ApiException(FridgeErrorCode.FRIDGE_DELETE_FAIL);
		}
	}
	
	
	
	
	
	// 냉장고 좋아요
	public LikeCreateResponseDto likeFridge(LikeCreateRequestDto likeDto) {
		
		// 입력값 검증
		if (likeDto == null
				|| likeDto.getTablename() == null
				|| likeDto.getRecodenum() == 0
				|| likeDto.getUserId() == 0) {
			throw new ApiException(FridgeErrorCode.FRIDGE_INVALID_INPUT);
		}
		
		// 냉장고 존재 여부 확인
		if (fridgeRepository.existsById(likeDto.getRecodenum())) {
			new ApiException(FridgeErrorCode.FRIDGE_NOT_FOUND);
		}
		
		// 좋아요 저장
		try {
			return likeService.createLike(likeDto);
		}
		catch (ApiException e) {
			throw e;
		}
		catch (Exception e) {
			throw new ApiException(FridgeErrorCode.FRIDGE_LIKE_FAIL);
		}
		
	}
	
	
	
	
	
	// 냉장고 좋아요 취소
	public void unlikeFridge(LikeDeleteRequestDto likeDto) {
		
		// 입력값 검증
		if (likeDto == null
				|| likeDto.getTablename() == null
				|| likeDto.getRecodenum() == 0
				|| likeDto.getUserId() == 0) {
			throw new ApiException(FridgeErrorCode.FRIDGE_INVALID_INPUT);
		}
		
		// 냉장고 존재 여부 확인
		if (fridgeRepository.existsById(likeDto.getRecodenum())) {
			new ApiException(FridgeErrorCode.FRIDGE_NOT_FOUND);
		}
		
		// 좋아요 삭제
		try {
			likeService.deleteLike(likeDto);
		}
		catch (ApiException e) {
			throw e;
		}
		catch (Exception e) {
			throw new ApiException(FridgeErrorCode.FRIDGE_UNLIKE_FAIL);
		}
		
	}
	
}
