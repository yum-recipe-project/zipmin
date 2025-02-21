package com.project.zipmin.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.zipmin.dto.StepDTO;

@Service
public interface IStepService {
	
	public List<StepDTO> selectStepsByRecipeIdx(Integer recipeIdx);
}
