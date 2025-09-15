package com.project.zipmin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.method.P;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import com.project.zipmin.api.ApiException;
import com.project.zipmin.api.EventErrorCode;
import com.project.zipmin.api.RecipeErrorCode;
import com.project.zipmin.api.VoteErrorCode;
import com.project.zipmin.dto.RecipeCategoryCreateRequestDto;
import com.project.zipmin.dto.RecipeCategoryCreateResponseDto;
import com.project.zipmin.dto.RecipeCategoryReadResponseDto;
import com.project.zipmin.dto.RecipeCreateRequestDto;
import com.project.zipmin.dto.RecipeCreateResponseDto;
import com.project.zipmin.dto.RecipeReadResponseDto;
import com.project.zipmin.dto.RecipeStepCreateRequestDto;
import com.project.zipmin.dto.RecipeStepCreateResponseDto;
import com.project.zipmin.dto.RecipeStepReadResponseDto;
import com.project.zipmin.dto.RecipeStockCreateRequestDto;
import com.project.zipmin.dto.RecipeStockCreateResponseDto;
import com.project.zipmin.dto.RecipeStockReadResponseDto;
import com.project.zipmin.dto.UserReadResponseDto;
import com.project.zipmin.entity.Recipe;
import com.project.zipmin.entity.RecipeCategory;
import com.project.zipmin.entity.RecipeStep;
import com.project.zipmin.entity.RecipeStock;
import com.project.zipmin.entity.Role;
import com.project.zipmin.entity.User;
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
	public Page<RecipeReadResponseDto> readRecipePage(List<String> categoryList, String keyword, String sort, Pageable pageable) {
		
		// 입력값 검증
		if (pageable == null) {
			throw new ApiException(RecipeErrorCode.RECIPE_INVALID_INPUT);
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
				case "title-desc":
					sortSpec = Sort.by(Sort.Order.desc("title"), Sort.Order.desc("id"));
					break;
				case "title-asc":
					sortSpec = Sort.by(Sort.Order.asc("title"), Sort.Order.desc("id"));
					break;
				case "postdate-desc":
					sortSpec = Sort.by(Sort.Order.desc("postdate"), Sort.Order.desc("id"));
					break;
				case "postdate-asc":
					sortSpec = Sort.by(Sort.Order.asc("postdate"), Sort.Order.asc("id"));
					break;
				case "commentcount-desc":
					sortSpec = Sort.by(Sort.Order.desc("commentcount"), Sort.Order.desc("id"));
					break;
				case "commentcount-asc":
					sortSpec = Sort.by(Sort.Order.asc("commentcount"), Sort.Order.desc("id"));
					break;
				case "likecount-desc":
					sortSpec = Sort.by(Sort.Order.desc("likecount"), Sort.Order.desc("id"));
					break;
				case "likecount-asc":
					sortSpec = Sort.by(Sort.Order.asc("likecount"), Sort.Order.desc("id"));
					break;
				case "reviewscore-desc":
					sortSpec = Sort.by(Sort.Order.desc("reviewscore"), Sort.Order.desc("id"));
					break;
				case "reviewscore-asc":
					sortSpec = Sort.by(Sort.Order.asc("reviewscore"), Sort.Order.desc("id"));
					break;
				case "reportcount-desc":
					sortSpec = Sort.by(Sort.Order.desc("reportcount"), Sort.Order.desc("id"));
					break;
				case "reportcount-asc":
					sortSpec = Sort.by(Sort.Order.asc("reportcount"), Sort.Order.desc("id"));
					break;
				default:
					break;
		    }
		}
		
		// 기존 페이지 객체에 정렬 주입
		Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sortSpec);
		
		// 레시피 목록 조회
		Page<Recipe> recipePage;
		try {
			boolean hasKeyword = keyword != null && !keyword.isBlank();
			boolean hasCategory = categoryList != null && !categoryList.isEmpty();
			
			if (!hasCategory) {
				// 카테고리 없음
				recipePage = hasKeyword
						? recipeRepository.findAllByTitleContainingIgnoreCase(keyword, sortedPageable)
						: recipeRepository.findAll(sortedPageable);
			} 
			else if (categoryList.size() == 1) {
				// 카테고리 1개
				recipePage = hasKeyword
						? recipeRepository.findDistinctByCategoryList_TagAndTitleContainingIgnoreCase(categoryList.get(0), keyword, sortedPageable)
						: recipeRepository.findDistinctByCategoryList_Tag(categoryList.get(0), sortedPageable);
			} 
			else {
				// 카테고리 여러 개
				// ***** 아래 코드는 카테고리끼리 OR 조건이므로 AND 조건으로 변경 필요 *****
				recipePage = hasKeyword
						? recipeRepository.findDistinctByCategoryList_TagInAndTitleContainingIgnoreCase(categoryList, keyword, sortedPageable)
						: recipeRepository.findDistinctByCategoryList_TagIn(categoryList, sortedPageable);
			}

		}
		catch (Exception e) {
			throw new ApiException(RecipeErrorCode.RECIPE_READ_LIST_FAIL);
		}
		
		// 레시피 목록 응답 구성
		List<RecipeReadResponseDto> recipeDtoList = new ArrayList<RecipeReadResponseDto>();
		for (Recipe recipe : recipePage) {
			RecipeReadResponseDto recipeDto = recipeMapper.toReadResponseDto(recipe);
			
			// 작성자
			UserReadResponseDto userDto = userService.readUserById(recipeDto.getId());
			recipeDto.setUsername(userDto.getUsername());
			recipeDto.setNickname(userDto.getNickname());
			
			// 이미지
			recipeDto.setImage(publicPath + "/" + recipeDto.getImage());	
			
			recipeDtoList.add(recipeDto);
		}
		
		return new PageImpl<>(recipeDtoList, pageable, recipePage.getTotalElements());
	}
	
	
	
	
	
	// 레시피 목록을 조회하는 함수 (냉장고 파먹기 용)
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
			}
			catch (Exception e) {
				throw new ApiException(RecipeErrorCode.RECIPE_STOCK_READ_LIST_FAIL);
			}
			recipeDto.setStockList(stockMapper.toReadResponseDtoList(stockList));
			
			recipeDtoList.add(recipeDto);
		}
		return recipeDtoList;
		
	}
	
	
	
	
	
	// 레시피 상세 조회
	public RecipeReadResponseDto readRecipdById(int id) {
		
		// 레시피 조회
		Recipe recipe = recipeRepository.findById(id)
				.orElseThrow(() -> new ApiException(RecipeErrorCode.RECIPE_NOT_FOUND));
		
		// 레시피 응답 구성
		RecipeReadResponseDto recipeDto = recipeMapper.toReadResponseDto(recipe);
		UserReadResponseDto userDto = userService.readUserById(recipeDto.getUserId());
		recipeDto.setNickname(userDto.getNickname());
		recipeDto.setAvatar(userDto.getAvatar());
		recipeDto.setFollower(likeService.countLike("users", recipeDto.getUserId()));
		
		// 좋아요 여부
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {
			String username = authentication.getName();
			int userId = userService.readUserByUsername(username).getId();
			recipeDto.setLiked(likeService.existsUserLike("recipe", recipeDto.getId(), userId));
		}
		
		// 레시피 카테고리 조회
		try {
			List<RecipeCategory> categoryList = categoryRepository.findAllByRecipeId(id);
			
			// 레시피 카테고리 목록 응답 구성
			List<RecipeCategoryReadResponseDto> categoryDtoList = new ArrayList<>();
			for (RecipeCategory category : categoryList) {
				RecipeCategoryReadResponseDto categoryDto = categoryMapper.toReadResponseDto(category);
				categoryDtoList.add(categoryDto);
			}
			
			recipeDto.setCategoryList(categoryDtoList);
		}
		catch (Exception e) {
			throw new ApiException(RecipeErrorCode.RECIPE_CATEGORY_READ_LIST_FAIL);
		}
		
		// 레시피 재료 목록 조회
		try {
			List<RecipeStock> stockList = stockRepository.findAllByRecipeId(id);
			
			// 레시피 재료 목록 응답 구성
			List<RecipeStockReadResponseDto> stockDtoList = new ArrayList<>();
			for (RecipeStock stock : stockList) {
				RecipeStockReadResponseDto stockDto = stockMapper.toReadResponseDto(stock);
				stockDtoList.add(stockDto);
			}
			
			recipeDto.setStockList(stockDtoList);
		}
		catch (Exception e) {
			throw new ApiException(RecipeErrorCode.RECIPE_STOCK_READ_LIST_FAIL);
		}
		
		// 조리 순서 목록 조회
		try {
			List<RecipeStep> stepList = stepRepository.findAllByRecipeId(id);
			
			// 조리 순서 목록 응답 구성
			List<RecipeStepReadResponseDto> stepDtoList = new ArrayList<>();
			for (RecipeStep step : stepList) {
				RecipeStepReadResponseDto stepDto = stepMapper.toReadResponseDto(step);
				
				// 이미지
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
	
	
	
	
	
	// 레시피를 작성하는 함수
	public RecipeCreateResponseDto createRecipe(RecipeCreateRequestDto recipeRequestDto, MultipartFile recipeImage, MultiValueMap<String, MultipartFile> stepImageMap) {
		
		// 입력값 검증 (레시피)
		if (recipeRequestDto == null || recipeRequestDto.getTitle() == null
				|| recipeRequestDto.getIntroduce() == null || recipeRequestDto.getCooklevel() == null
				|| recipeRequestDto.getCooktime() == null || recipeRequestDto.getSpicy() == null
				|| recipeRequestDto.getPortion() == null || recipeRequestDto.getUserId() == null) {
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
			if (categoryDto.getType() == null || categoryDto.getTag() == null) {
				throw new ApiException(RecipeErrorCode.RECIPE_CATEGORY_INVALID_INPUT);
			}
		}
		// 입력값 검증 (재료)
		if (recipeRequestDto.getStockDtoList() == null) {
			throw new ApiException(RecipeErrorCode.RECIPE_STOCK_INVALID_INPUT);
		}
		for (RecipeStockCreateRequestDto stockDto : recipeRequestDto.getStockDtoList()) {
			if (stockDto.getName() == null || stockDto.getAmount() == null || stockDto.getUnit() == null) {
				throw new ApiException(RecipeErrorCode.RECIPE_STOCK_INVALID_INPUT);
			}
		}
		// 입력값 검증 (조리순서)
		if (recipeRequestDto.getStepDtoList() == null) {
			throw new ApiException(RecipeErrorCode.RECIPE_STEP_INVALID_INPUT);
		}
		for (RecipeStepCreateRequestDto stepDto : recipeRequestDto.getStepDtoList()) {
			if (stepDto.getContent() == null) {
				throw new ApiException(RecipeErrorCode.RECIPE_CATEGORY_INVALID_INPUT);
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
		
		// 레시피 저장
		Recipe recipe = recipeMapper.toEntity(recipeRequestDto);
		try {
			recipe = recipeRepository.save(recipe);
			RecipeCreateResponseDto recipeResponseDto = recipeMapper.toCreateResponseDto(recipe);
			
			
			// 카테고리 저장
			List<RecipeCategoryCreateResponseDto> categoryDtoList = new ArrayList<>();
			for (RecipeCategoryCreateRequestDto categoryDto : recipeRequestDto.getCategoryDtoList()) {
				categoryDto.setRecipeId(recipeResponseDto.getId());
				RecipeCategory category = categoryMapper.toEntity(categoryDto);
				
				// 카테고리 저장
				try {
					category = categoryRepository.save(category);
				}
				catch (Exception e) {
					throw new ApiException(RecipeErrorCode.RECIPE_CATEGORY_CREATE_FAIL);
				}
				
				categoryDtoList.add(categoryMapper.toCreateResponseDto(category));
			}
			recipeResponseDto.setCategoryDto(categoryDtoList);
			
			// 재료 저장
			List<RecipeStockCreateResponseDto> stockDtoList = new ArrayList<>();
			for (RecipeStockCreateRequestDto stockDto : recipeRequestDto.getStockDtoList()) {
				stockDto.setRecipeId(recipeResponseDto.getId());
				RecipeStock stock = stockMapper.toEntity(stockDto);				
				
				try {
					stock = stockRepository.save(stock);
				}
				catch (Exception e) {
					throw new ApiException(RecipeErrorCode.RECIPE_STOCK_CREATE_FAIL);
				}
				
				stockDtoList.add(stockMapper.toCreateResponseDto(stock));
			}
			recipeResponseDto.setStockDto(stockDtoList);
			
			// 조리 과정 저장
			List<RecipeStepCreateResponseDto> stepDtoList = new ArrayList<>();
			for (int i = 0; i < recipeRequestDto.getStepDtoList().size(); i++) {
				RecipeStepCreateRequestDto stepDto = recipeRequestDto.getStepDtoList().get(i);
				stepDto.setRecipeId(recipeResponseDto.getId());
				
				// 조리 과정 파일 저장
				String key = "stepImage[" + i + "]";
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
				RecipeStep step = stepMapper.toEntity(stepDto);
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
		catch (Exception e) {
			throw new ApiException(RecipeErrorCode.RECIPE_CREATE_FAIL);
		}
	}
	
	
	
	
	
	// 레시피를 삭제하는 함수
	public void deleteRecipe(Integer id) {
		
		// 입력값 검증
		if (id == null) {
			throw new ApiException(RecipeErrorCode.RECIPE_INVALID_INPUT);
		}
		
		// 레시피 존재 여부 확인
		Recipe recipe = recipeRepository.findById(id)
				.orElseThrow(() -> new ApiException(RecipeErrorCode.RECIPE_NOT_FOUND));
		
		// 권한 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserReadResponseDto user = userService.readUserByUsername(username);
		if (!user.getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			// 관리자
			if (user.getRole().equals(Role.ROLE_ADMIN.name())) {
				if (recipe.getUser().getRole().equals(Role.ROLE_SUPER_ADMIN)) {
					throw new ApiException(RecipeErrorCode.RECIPE_FORBIDDEN);
				}
				if (recipe.getUser().getRole().equals(Role.ROLE_ADMIN)) {
					if (user.getId() != recipe.getUser().getId()) {
						throw new ApiException(RecipeErrorCode.RECIPE_FORBIDDEN);
					}
				}
			}
			// 일반 회원
			else {
				if (user.getId() != recipe.getUser().getId()) {
					throw new ApiException(RecipeErrorCode.RECIPE_FORBIDDEN);
				}
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

}
