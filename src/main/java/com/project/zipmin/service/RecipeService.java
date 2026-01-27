package com.project.zipmin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
	
import com.project.zipmin.api.ApiException;
import com.project.zipmin.api.RecipeErrorCode;
import com.project.zipmin.dto.UserReadResponseDto;
import com.project.zipmin.dto.like.LikeCreateRequestDto;
import com.project.zipmin.dto.like.LikeCreateResponseDto;
import com.project.zipmin.dto.like.LikeDeleteRequestDto;
import com.project.zipmin.dto.like.LikeReadResponseDto;
import com.project.zipmin.dto.recipe.RecipeCategoryCreateRequestDto;
import com.project.zipmin.dto.recipe.RecipeCategoryCreateResponseDto;
import com.project.zipmin.dto.recipe.RecipeCategoryReadResponseDto;
import com.project.zipmin.dto.recipe.RecipeCreateRequestDto;
import com.project.zipmin.dto.recipe.RecipeCreateResponseDto;
import com.project.zipmin.dto.recipe.RecipeReadResponseDto;
import com.project.zipmin.dto.recipe.RecipeStepCreateRequestDto;
import com.project.zipmin.dto.recipe.RecipeStepCreateResponseDto;
import com.project.zipmin.dto.recipe.RecipeStepReadResponseDto;
import com.project.zipmin.dto.recipe.RecipeStockCreateRequestDto;
import com.project.zipmin.dto.recipe.RecipeStockCreateResponseDto;
import com.project.zipmin.dto.recipe.RecipeStockReadResponseDto;
import com.project.zipmin.entity.Recipe;
import com.project.zipmin.entity.RecipeCategory;
import com.project.zipmin.entity.RecipeStep;
import com.project.zipmin.entity.RecipeStock;
import com.project.zipmin.entity.Role;
import com.project.zipmin.mapper.RecipeCategoryMapper;
import com.project.zipmin.mapper.RecipeMapper;
import com.project.zipmin.mapper.RecipeStepMapper;
import com.project.zipmin.mapper.RecipeStockMapper;
import com.project.zipmin.repository.RecipeCategoryRepository;
import com.project.zipmin.repository.RecipeRepository;
import com.project.zipmin.repository.RecipeStepRepository;
import com.project.zipmin.repository.RecipeStockRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class RecipeService {
	
	private final RecipeRepository recipeRepository;
	private final RecipeCategoryRepository categoryRepository;
	private final RecipeStockRepository stockRepository;
	private final RecipeStepRepository stepRepository;
	
	private final UserService userService;
	private final FileService fileService;
	private final LikeService likeService;
	
	private final RecipeMapper recipeMapper;
	private final RecipeCategoryMapper categoryMapper;
	private final RecipeStockMapper stockMapper;
	private final RecipeStepMapper stepMapper;
	
	@Value("${app.upload.public-path:/files}")
	private String publicPath;
	
	
	
	
	
	// 레시피 목록 조회
	public Page<RecipeReadResponseDto> readRecipePage(String keyword, List<String> categoryList, String sort, Pageable pageable) {
		
		// 입력값 검증
		if (pageable == null) {
			throw new ApiException(RecipeErrorCode.RECIPE_INVALID_INPUT);
		}
		
		// 정렬
		Sort order = Sort.by(Sort.Order.desc("id"));
		if (sort != null && !sort.isBlank()) {
			String field = sort.split("-")[0];
			Direction direction = "asc".equals(sort.split("-")[1]) ? Direction.ASC : Direction.DESC;
			order = Sort.by(new Order(direction, field), Order.desc("id"));
		}
		pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), order);
		
		// 레시피 목록 조회
		Page<Recipe> recipePage;
		try {
			boolean hasKeyword = keyword != null && !keyword.isBlank();
			boolean hasCategory = categoryList != null && !categoryList.isEmpty();
			
			if (hasCategory) {
				recipePage = hasKeyword
						? recipeRepository.findAllDistinctByCategoryList_TagInAndTitleContainingIgnoreCase(categoryList, keyword, pageable)
						: recipeRepository.findAllDistinctByCategoryList_TagIn(categoryList, pageable);
			} 
			else {
				recipePage = hasKeyword
						? recipeRepository.findAllByTitleContainingIgnoreCase(keyword, pageable)
						: recipeRepository.findAll(pageable);
			}
		}
		catch (Exception e) {
			throw new ApiException(RecipeErrorCode.RECIPE_READ_LIST_FAIL);
		}
		
		// 레시피 목록 응답 구성
		List<RecipeReadResponseDto> recipeDtoList = new ArrayList<RecipeReadResponseDto>();
		for (Recipe recipe : recipePage) {
			RecipeReadResponseDto recipeDto = recipeMapper.toReadResponseDto(recipe);
			recipeDto.setUsername(recipe.getUser().getUsername());
			recipeDto.setNickname(recipe.getUser().getNickname());
			recipeDto.setImage(publicPath + "/" + recipeDto.getImage());	
			recipeDtoList.add(recipeDto);
		}
		
		return new PageImpl<>(recipeDtoList, pageable, recipePage.getTotalElements());
	}
	
	
	
	
	
	// 레시피 목록 조회 (냉장고 파먹기)
	// TODO : 수정 필요
	public List<RecipeReadResponseDto> readRecipeList() {
		
		// 레시피 목록 조회
		List<Recipe> recipeList;
		try {
			recipeList = recipeRepository.findAll();
		}
		catch (Exception e) {
			throw new ApiException(RecipeErrorCode.RECIPE_READ_LIST_FAIL);
		}
		
		// 레시피 목록 응답 구성
		List<RecipeReadResponseDto> recipeDtoList = new ArrayList<>();
		for (Recipe recipe : recipeList) {
			RecipeReadResponseDto recipeDto = recipeMapper.toReadResponseDto(recipe);
			
			// 레시피 재료 목록 조회
			List<RecipeStock> stockList;
			try {
				stockList = stockRepository.findAllByRecipeId(recipe.getId());
				recipeDto.setStockList(stockMapper.toReadResponseDtoList(stockList));
			}
			catch (Exception e) {
				throw new ApiException(RecipeErrorCode.RECIPE_STOCK_READ_LIST_FAIL);
			}
			
			recipeDtoList.add(recipeDto);
		}
		return recipeDtoList;
		
	}
	
	
	
	
	
	// 사용자의 레시피 목록 조회
	public Page<RecipeReadResponseDto> readRecipePageByUserId(int userId, String sort, Pageable pageable) {
		
		// 입력값 검증
		if (userId == 0 || pageable == null) {
			throw new ApiException(RecipeErrorCode.RECIPE_INVALID_INPUT);
		}
		
		// 정렬
		Sort order = Sort.by(Sort.Order.desc("id"));
		if (sort != null && !sort.isBlank()) {
			String field = sort.split("-")[0];
			Direction direction = "asc".equals(sort.split("-")[1]) ? Direction.ASC : Direction.DESC;
			order = Sort.by(new Order(direction, field), Order.desc("id"));
		}
		pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), order);
		
		// 레시피 목록 조회
		Page<Recipe> recipePage;
		try {
			recipePage = recipeRepository.findAllByUserId(userId, pageable);
		}
		catch (Exception e) {
			throw new ApiException(RecipeErrorCode.RECIPE_READ_LIST_FAIL);
		}

		// 레시피 목록 응답 구성
		List<RecipeReadResponseDto> recipeDtoList = new ArrayList<>();
		for (Recipe recipe : recipePage) {
			RecipeReadResponseDto recipeDto = recipeMapper.toReadResponseDto(recipe);
		 	recipeDto.setImage(publicPath + "/" + recipeDto.getImage());	
			recipeDtoList.add(recipeDto);
		}

		return new PageImpl<>(recipeDtoList, pageable, recipePage.getTotalElements());
	}

	
	

	
	// 사용자가 좋아요한 레시피 목록 조회
	public Page<RecipeReadResponseDto> readLikedRecipePageByUserId(int userId, Pageable pageable) {
		
		// 입력값 검증
		if (userId == 0 || pageable == null) {
			throw new ApiException(RecipeErrorCode.RECIPE_INVALID_INPUT);
		}
		
		// 좋아요한 레시피 일련번호 추출
		List<LikeReadResponseDto> likeList = likeService.readLikeListByTablenameAndUserId("recipe", userId);
		List<Integer> recipeIdList = likeList.stream()
				.map(LikeReadResponseDto::getRecodenum)
				.toList();

		if (recipeIdList.isEmpty()) {
			return Page.empty(pageable);
		}
		
		// 레시피 목록 조회
		Page<Recipe> recipePage;
		try {
			recipePage = recipeRepository.findAllByIdIn(recipeIdList, pageable);
		}
		catch (Exception e) {
			throw new ApiException(RecipeErrorCode.RECIPE_READ_LIST_FAIL);
		}

		// 레시피 응답 구성
		List<RecipeReadResponseDto> recipeDtoList = new ArrayList<>();
		for (Recipe recipe : recipePage) {
			RecipeReadResponseDto recipeDto = recipeMapper.toReadResponseDto(recipe);
			recipeDto.setLikecount(likeService.countLike("recipe", recipe.getId()));
			recipeDto.setImage(publicPath + "/" + recipeDto.getImage());
			recipeDtoList.add(recipeDto);
		}
		
		return new PageImpl<>(recipeDtoList, pageable, recipePage.getTotalElements());
	}

	
	
	
	
	// 레시피 상세 조회
	public RecipeReadResponseDto readRecipeById(int id) {
		
		if (id == 0) {
			throw new ApiException(RecipeErrorCode.RECIPE_INVALID_INPUT);
		}
		
		// 레시피 조회
		Recipe recipe;
		RecipeReadResponseDto recipeDto;
		try {
			recipe = recipeRepository.findById(id);
			recipeDto = recipeMapper.toReadResponseDto(recipe);
			recipeDto.setNickname(recipe.getUser().getNickname());
			recipeDto.setAvatar(recipe.getUser().getAvatar());
			recipeDto.setFollower(likeService.countLike("users", recipeDto.getUserId()));
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {
				UserReadResponseDto userDto = userService.readUserByUsername(authentication.getName());
				recipeDto.setLiked(likeService.existLike("recipe", recipeDto.getId(), userDto.getId()));
			}
		}
		catch (Exception e) {
			throw new ApiException(RecipeErrorCode.RECIPE_NOT_FOUND);
		}
		
		// 레시피 카테고리 조회
		List<RecipeCategory> categoryList;
		try {
			categoryList = categoryRepository.findAllByRecipeId(id);
			List<RecipeCategoryReadResponseDto> categoryDtoList = categoryMapper.toReadResponseDtoList(categoryList);
			recipeDto.setCategoryList(categoryDtoList);
		}
		catch (Exception e) {
			throw new ApiException(RecipeErrorCode.RECIPE_CATEGORY_READ_LIST_FAIL);
		}
		
		// 레시피 재료 목록 조회
		try {
			List<RecipeStock> stockList = stockRepository.findAllByRecipeId(id);
			List<RecipeStockReadResponseDto> stockDtoList = stockMapper.toReadResponseDtoList(stockList);
			recipeDto.setStockList(stockDtoList);
		}
		catch (Exception e) {
			throw new ApiException(RecipeErrorCode.RECIPE_STOCK_READ_LIST_FAIL);
		}
		
		// 조리 순서 목록 조회
		try {
			List<RecipeStep> stepList = stepRepository.findAllByRecipeId(id);
			List<RecipeStepReadResponseDto> stepDtoList = new ArrayList<>();
			for (RecipeStep step : stepList) {
				RecipeStepReadResponseDto stepDto = stepMapper.toReadResponseDto(step);
				if (stepDto.getImage() != null) {
					stepDto.setImage(publicPath + "/" + stepDto.getImage());	
				}
				stepDtoList.add(stepDto);
			}
			recipeDto.setStepList(stepDtoList);
		}
		catch (Exception e) {
			throw new ApiException(RecipeErrorCode.RECIPE_STEP_READ_LIST_FAIL);
		}
		
		return recipeDto;
	}
	
	
	
	
	
	// 레시피 작성
	public RecipeCreateResponseDto createRecipe(RecipeCreateRequestDto recipeRequestDto, MultipartFile recipeImage, MultiValueMap<String, MultipartFile> stepImageMap) {
		
		// 입력값 검증 (레시피)
		if (recipeRequestDto == null
				|| recipeRequestDto.getTitle() == null
				|| recipeRequestDto.getIntroduce() == null
				|| recipeRequestDto.getCooklevel() == null
				|| recipeRequestDto.getCooktime() == null
				|| recipeRequestDto.getSpicy() == null
				|| recipeRequestDto.getPortion() == null
				|| recipeRequestDto.getUserId() == 0) {
			throw new ApiException(RecipeErrorCode.RECIPE_INVALID_INPUT);
		}
		if (recipeImage == null) {
			throw new ApiException(RecipeErrorCode.RECIPE_INVALID_INPUT);
		}
		
		// 입력값 검증 (카테고리)
		if (recipeRequestDto.getCategoryDtoList() == null) {
			throw new ApiException(RecipeErrorCode.RECIPE_CATEGORY_INVALID_INPUT);
		}
		for (RecipeCategoryCreateRequestDto categoryDto : recipeRequestDto.getCategoryDtoList()) {
			if (categoryDto == null
					|| categoryDto.getType() == null
					|| categoryDto.getTag() == null) {
				throw new ApiException(RecipeErrorCode.RECIPE_CATEGORY_INVALID_INPUT);
			}
		}
		
		// 입력값 검증 (재료)
		if (recipeRequestDto.getStockDtoList() == null) {
			throw new ApiException(RecipeErrorCode.RECIPE_STOCK_INVALID_INPUT);
		}
		for (RecipeStockCreateRequestDto stockDto : recipeRequestDto.getStockDtoList()) {
			if (stockDto == null
					|| stockDto.getName() == null
					|| stockDto.getAmount() == 0
					|| stockDto.getUnit() == null) {
				throw new ApiException(RecipeErrorCode.RECIPE_STOCK_INVALID_INPUT);
			}
		}
		
		// 입력값 검증 (조리순서)
		if (recipeRequestDto.getStepDtoList() == null) {
			throw new ApiException(RecipeErrorCode.RECIPE_STEP_INVALID_INPUT);
		}
		for (RecipeStepCreateRequestDto stepDto : recipeRequestDto.getStepDtoList()) {
			if (stepDto == null || stepDto.getContent() == null) {
				throw new ApiException(RecipeErrorCode.RECIPE_STEP_INVALID_INPUT);
			}
		}
		
		// 레시피 파일 저장
		try {
			String image = fileService.store(recipeImage);
			recipeRequestDto.setImage(image);
		}
		catch (Exception e) {
			throw new ApiException(RecipeErrorCode.RECIPE_FILE_UPLOAD_FAIL);
		}
		
		// 레시피 작성
		Recipe recipe;
		RecipeCreateResponseDto recipeResponseDto;
		try {
			recipe = recipeMapper.toEntity(recipeRequestDto);
			recipe = recipeRepository.save(recipe);
			recipeResponseDto = recipeMapper.toCreateResponseDto(recipe);
		}
		catch (Exception e) {
			throw new ApiException(RecipeErrorCode.RECIPE_CREATE_FAIL);
		}
		
		// 카테고리 작성
		RecipeCategory category;
		List<RecipeCategoryCreateResponseDto> categoryDtoList = new ArrayList<>();
		try {
			for (RecipeCategoryCreateRequestDto categoryDto : recipeRequestDto.getCategoryDtoList()) {
				categoryDto.setRecipeId(recipeResponseDto.getId());
				category = categoryMapper.toEntity(categoryDto);
				category = categoryRepository.save(category);
				categoryDtoList.add(categoryMapper.toCreateResponseDto(category));
			}
		}
		catch (Exception e) {
			throw new ApiException(RecipeErrorCode.RECIPE_CATEGORY_CREATE_FAIL);
		}
		recipeResponseDto.setCategoryDto(categoryDtoList);
		
		// 재료 작성
		RecipeStock stock;
		List<RecipeStockCreateResponseDto> stockDtoList = new ArrayList<>();
		try {
			for (RecipeStockCreateRequestDto stockDto : recipeRequestDto.getStockDtoList()) {
				stockDto.setRecipeId(recipeResponseDto.getId());
				stock = stockMapper.toEntity(stockDto);
				stock = stockRepository.save(stock);
				stockDtoList.add(stockMapper.toCreateResponseDto(stock));
			}
		}
		catch (Exception e) {
			throw new ApiException(RecipeErrorCode.RECIPE_STOCK_CREATE_FAIL);
		}
		recipeResponseDto.setStockDto(stockDtoList);
		
		// 조리 순서 저장
		RecipeStep step;
		List<RecipeStepCreateResponseDto> stepDtoList = new ArrayList<>();
		for (int i = 0; i < recipeRequestDto.getStepDtoList().size(); i++) {
			RecipeStepCreateRequestDto stepDto = recipeRequestDto.getStepDtoList().get(i);
			stepDto.setRecipeId(recipeResponseDto.getId());
			
			// 조리 과정 파일 저장
			String key = "stepImageMap[" + i + "]";
			MultipartFile stepImage = (stepImageMap != null) ? stepImageMap.getFirst(key) : null;
			try {
				if (stepImage != null && !stepImage.isEmpty()) {
					String image = fileService.store(stepImage);
					stepDto.setImage(image);
				}
			}
			catch (Exception e) {
				throw new ApiException(RecipeErrorCode.RECIPE_STEP_FILE_UPLOAD_FAIL);
			}
			
			// 조리 과정 저장
			step = stepMapper.toEntity(stepDto);
			try {
				step = stepRepository.save(step);
			}
			catch (Exception e) {
				throw new ApiException(RecipeErrorCode.RECIPE_STEP_CREATE_FAIL);
			}
			stepDtoList.add(stepMapper.toCreateResponseDto(step));
		}
		recipeResponseDto.setStepDto(stepDtoList);
		
		return recipeResponseDto;
	}
	
	
	
	
	
	// 레시피 삭제
	public void deleteRecipe(int id) {
		
		// 입력값 검증
		if (id == 0) {
			throw new ApiException(RecipeErrorCode.RECIPE_INVALID_INPUT);
		}
		
		// 레시피 존재 여부 확인
		Recipe recipe;
		try {
			recipe = recipeRepository.findById(id);
		}
		catch (Exception e) {
			throw new ApiException(RecipeErrorCode.RECIPE_NOT_FOUND);
		}
		
		// 권한 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserReadResponseDto currentUser = userService.readUserByUsername(username);
		
		if (!currentUser.getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			if (currentUser.getRole().equals(Role.ROLE_ADMIN.name())) {
				if (recipe.getUser().getRole().equals(Role.ROLE_SUPER_ADMIN)) {
					throw new ApiException(RecipeErrorCode.RECIPE_FORBIDDEN);
				}
				if (recipe.getUser().getRole().equals(Role.ROLE_ADMIN) && currentUser.getId() != recipe.getUser().getId()) {
					throw new ApiException(RecipeErrorCode.RECIPE_FORBIDDEN);
				}
			}
			else if (currentUser.getRole().equals(Role.ROLE_USER.name()) && currentUser.getId() != recipe.getUser().getId()) {
				throw new ApiException(RecipeErrorCode.RECIPE_FORBIDDEN);
			}
		}
		
		// 레시피 삭제
		try {
			recipeRepository.deleteById(id);
		}
		catch (Exception e) {
			throw new ApiException(RecipeErrorCode.RECIPE_DELETE_FAIL);
		}
	}
	


	
	
	// 레시피 좋아요
	public LikeCreateResponseDto likeRecipe(LikeCreateRequestDto likeDto) {
		
		// 입력값 검증
		if (likeDto == null
				|| likeDto.getTablename() == null
				|| likeDto.getRecodenum() == 0
				|| likeDto.getUserId() == 0) {
			throw new ApiException(RecipeErrorCode.RECIPE_INVALID_INPUT);
		}
		
		// 게시글 존재 여부 확인
		if (!recipeRepository.existsById(likeDto.getRecodenum())) {
			throw new ApiException(RecipeErrorCode.RECIPE_NOT_FOUND);
		}
		
		// 좋아요 저장
		try {
			return likeService.createLike(likeDto);
		}
		catch (ApiException e) {
			throw e;
		}
		catch (Exception e) {
			throw new ApiException(RecipeErrorCode.RECIPE_LIKE_FAIL);
		}
		
	}


	
	
	
	// 레시피 좋아요 취소
	public void unlikeRecipe(LikeDeleteRequestDto likeDto) {
		
		// 입력값 검증
		if (likeDto == null
				|| likeDto.getTablename() == null
				|| likeDto.getRecodenum() == 0
				|| likeDto.getUserId() == 0) {
			throw new ApiException(RecipeErrorCode.RECIPE_INVALID_INPUT);
		}

		// 레시피 존재 여부 확인
		if (!recipeRepository.existsById(likeDto.getRecodenum())) {
			throw new ApiException(RecipeErrorCode.RECIPE_NOT_FOUND);
		}

		// 좋아요 삭제
		try {
			likeService.deleteLike(likeDto);
		}
		catch (ApiException e) {
			throw e;
		}
		catch (Exception e) {
			throw new ApiException(RecipeErrorCode.RECIPE_UNLIKE_FAIL);
		}
	}
}
