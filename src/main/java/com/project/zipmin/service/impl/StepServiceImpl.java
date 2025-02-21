package com.project.zipmin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.zipmin.dto.StepDTO;
import com.project.zipmin.service.IStepService;

@Service
public class StepServiceImpl implements IStepService {

	@Override
	public List<StepDTO> selectStepsByRecipeIdx(Integer recipeIdx) {
		
		List<StepDTO> stepList = new ArrayList<StepDTO>();
		
		// 더미데이터 1
		StepDTO stepDTO1 = new StepDTO();
		stepDTO1.setStep_idx(1);
		stepDTO1.setDescription("1번 설명입니다");
		stepDTO1.setRecipe_ref(1);
		stepList.add(stepDTO1);
		
		// 더미데이터 2
		StepDTO stepDTO2 = new StepDTO();
		stepDTO2.setStep_idx(2);
		stepDTO2.setDescription("2번 설명입니다");
		stepDTO2.setRecipe_ref(1);
		stepList.add(stepDTO2);
		
		return stepList;
	}

}
