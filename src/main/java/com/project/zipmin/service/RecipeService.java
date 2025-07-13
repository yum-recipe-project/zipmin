package com.project.zipmin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.zipmin.api.ApiException;
import com.project.zipmin.api.RecipeErrorCode;
import com.project.zipmin.dto.RecipeCategoryCreateRequestDto;
import com.project.zipmin.dto.RecipeCategoryCreateResponseDto;
import com.project.zipmin.dto.RecipeCategoryReadResponseDto;
import com.project.zipmin.dto.RecipeCreateRequestDto;
import com.project.zipmin.dto.RecipeCreateResponseDto;
import com.project.zipmin.dto.RecipeStepCreateRequestDto;
import com.project.zipmin.dto.RecipeStepCreateResponseDto;
import com.project.zipmin.dto.RecipeStockCreateRequestDto;
import com.project.zipmin.dto.RecipeStockCreateResponseDto;
import com.project.zipmin.entity.Recipe;
import com.project.zipmin.entity.RecipeCategory;
import com.project.zipmin.entity.RecipeStep;
import com.project.zipmin.entity.RecipeStock;
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
	
	@Autowired
	private RecipeRepository recipeRepository;
	@Autowired
	private RecipeCategoryRepository categoryRepository;
	@Autowired
	private RecipeStockRepository stockRepository;
	@Autowired
	private RecipeStepRepository stepRepository;
	
	private final RecipeMapper recipeMapper;
	private final RecipeCategoryMapper categoryMapper;
	private final RecipeStockMapper stockMapper;
	private final RecipeStepMapper stepMapper;
	
	

	// 레시피 목록을 조회하는 함수
	public Page<?> readRecipePage() {
		return null;
	}

	
	
	// 레시피를 작성하는 함수
	public RecipeCreateResponseDto createRecipe(RecipeCreateRequestDto recipeRequestDto) {
		
		// 입력값 검증
		if (recipeRequestDto == null || recipeRequestDto.getTitle() == null || recipeRequestDto.getIntroduce() == null || recipeRequestDto.getCooklevel() == null || recipeRequestDto.getCooktime() == null || recipeRequestDto.getSpicy() == null || recipeRequestDto.getPortion() == null || recipeRequestDto.getUserId() == null) {
			throw new ApiException(RecipeErrorCode.RECIPE_INVALID_INPUT);
		}
		
		Recipe recipe = recipeMapper.toEntity(recipeRequestDto);
		
		// 레시피 저장
		try {
			recipe = recipeRepository.save(recipe);
			RecipeCreateResponseDto recipeResponseDto = recipeMapper.toCreateResponseDto(recipe);
			
			// 카테고리 저장
			List<RecipeCategoryCreateResponseDto> categoryDtoList = new ArrayList<>();
			for (RecipeCategoryCreateRequestDto categoryDto : recipeRequestDto.getCategoryDtoList()) {
				categoryDto.setRecipeId(recipeResponseDto.getId());
				categoryDtoList.add(createCategory(categoryDto));
			}
			recipeResponseDto.setCategoryDto(categoryDtoList);

			// 재료 저장
			List<RecipeStockCreateResponseDto> stockDtoList = new ArrayList<>();
			for (RecipeStockCreateRequestDto stockDto : recipeRequestDto.getStockDtoList()) {
				stockDto.setRecipeId(recipeResponseDto.getId());
				stockDtoList.add(createStock(stockDto));
			}
			recipeResponseDto.setStockDto(stockDtoList);
			
			// 조리 과정 저장
			List<RecipeStepCreateResponseDto> stepDtoList = new ArrayList<>();
			for (RecipeStepCreateRequestDto stepDto : recipeRequestDto.getStepDtoList()) {
				stepDto.setRecipeId(recipeResponseDto.getId());
				stepDtoList.add(createStep(stepDto));
			}
			recipeResponseDto.setStepDto(stepDtoList);
			
			return recipeResponseDto;
		}
		catch (Exception e) {
			throw new ApiException(RecipeErrorCode.RECIPE_CREATE_FAIL);
		}
	}

	
	
	// 레시피를 삭제하는 함수
	public void deleteRecipe() {
		
	}
	
	
	
	// 레시피 카테고리 조회
	public List<RecipeCategoryReadResponseDto> readCategoryList(int id) {
		return null;
	}

	
	
	// 레시피 카테고리 작성
	public RecipeCategoryCreateResponseDto createCategory(RecipeCategoryCreateRequestDto categoryDto) {
		
		// 입력값 검증
		if (categoryDto == null || categoryDto.getType() == null || categoryDto.getTag() == null || categoryDto.getRecipeId() == null) {
			throw new ApiException(RecipeErrorCode.RECIPE_CATEGORY_INVALID_INPUT);
		}
		
		// 레시피 존재 여부 판단
		if (!recipeRepository.existsById(categoryDto.getRecipeId())) {
			throw new ApiException(RecipeErrorCode.RECIPE_NOT_FOUND);
		}
		
		RecipeCategory category = categoryMapper.toEntity(categoryDto);
		
		// 카테고리 저장
		try {
			category = categoryRepository.save(category);
			return categoryMapper.toCreateResponseDto(category);
		}
		catch (Exception e) {
			throw new ApiException(RecipeErrorCode.RECIPE_CATEGORY_CREATE_FAIL);
		}

	}

	
	
	// 레시피 카테고리 삭제
	public void deleteCategory(Integer id) {
		
		// 입력값 검증
		if (id == null) {
			throw new ApiException(RecipeErrorCode.RECIPE_CATEGORY_INVALID_INPUT);
		}
		
		// 레시피 카테고리 존재 여부 판단
		if (!categoryRepository.existsById(id)) {
			throw new ApiException(RecipeErrorCode.RECIPE_CATEGORY_NOT_FOUND);
		}
		
		// 카테고리 삭제
		try {
			categoryRepository.deleteById(id);		}
		catch (Exception e) {
			throw new ApiException(RecipeErrorCode.RECIPE_CATEGORY_DELETE_FAIL);
		}
	}
	
	
	
	// 재료 목록 조회
	public List<?> readStockList() {
		return null;
	}
	
	
	
	// 재료 작성
	public RecipeStockCreateResponseDto createStock(RecipeStockCreateRequestDto stockDto) {
		
		// 입력값 검증
		if (stockDto == null || stockDto.getName() == null || stockDto.getAmount() == null || stockDto.getUnit() == null || stockDto.getRecipeId() == null) {
			throw new ApiException(RecipeErrorCode.RECIPE_STOCK_INVALID_INPUT);
		}
		
		// 레시피 존재 여부 판단
		if (!recipeRepository.existsById(stockDto.getRecipeId())) {
			throw new ApiException(RecipeErrorCode.RECIPE_NOT_FOUND);
		}
		
		RecipeStock stock = stockMapper.toEntity(stockDto);
		
		// 재료 저장
		try {
			stock = stockRepository.save(stock);
			return stockMapper.toCreateResponseDto(stock);
		}
		catch (Exception e) {
			throw new ApiException(RecipeErrorCode.RECIPE_STOCK_CREATE_FAIL);
		}
	}
	
	
	
	// 재료 삭제
	public void deleteStock(Integer id) {
		
		if (id == null) {
			throw new ApiException(RecipeErrorCode.RECIPE_STOCK_INVALID_INPUT);
		}
		
		// 재료 존재 여부 판단
		if (!stockRepository.existsById(id)) {
			throw new ApiException(RecipeErrorCode.RECIPE_STOCK_NOT_FOUND);
		}
		
		// 재료 삭제
		try {
			stockRepository.deleteById(id);
		}
		catch (Exception e) {
			throw new ApiException(RecipeErrorCode.RECIPE_STOCK_DELETE_FAIL);
		}
		
	}
	
	
	
	// 조리 과정 목록 조회
	public List<?> readStepList() {
		return null;
	}
		
	
	
	// 조리 과정 작성
	public RecipeStepCreateResponseDto createStep(RecipeStepCreateRequestDto stepDto) {
		
		// 입력값 검증
		if (stepDto == null || stepDto.getContent() == null || stepDto.getRecipeId() == null) {
			throw new ApiException(RecipeErrorCode.RECIPE_STEP_INVALID_INPUT);
		}
		
		// 레시피 존재 여부 판단
		if (!recipeRepository.existsById(stepDto.getRecipeId())) {
			throw new ApiException(RecipeErrorCode.RECIPE_NOT_FOUND);
		}
		
		RecipeStep step = stepMapper.toEntity(stepDto);
		
		// 조리 과정 저장
		try {
			step = stepRepository.save(step);
			return stepMapper.toCreateResponseDto(step);
		}
		catch (Exception e) {
			throw new ApiException(RecipeErrorCode.RECIPE_STEP_CREATE_FAIL);
		}
	}
	
	
	
	// 조리 과정 삭제
	public void deleteStep(Integer id) {
		
		// 입력값 검증
		if (id == null) {
			throw new ApiException(RecipeErrorCode.RECIPE_STEP_INVALID_INPUT);
		}
		
		// 조리 과정 존재 여부 판단
		if (!stepRepository.existsById(id)) {
			throw new ApiException(RecipeErrorCode.RECIPE_STEP_NOT_FOUND);
		}
		
		// 조리 과정 삭제
		try {
			stepRepository.deleteById(id);
		}
		catch (Exception e) {
			throw new ApiException(RecipeErrorCode.RECIPE_STEP_DELETE_FAIL);
		}
		
	}

}
