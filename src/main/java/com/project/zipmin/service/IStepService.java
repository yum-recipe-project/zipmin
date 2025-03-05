package com.project.zipmin.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.zipmin.dto.StepDTO;

@Service
public interface IStepService {
	
	// 조리 순서 목록 조회
	public List<StepDTO> selectStepList(int recipeIdx);
	
	// 조리 순서 작성
	public int insertStep(StepDTO stepDTO);
	
	// 조리 순서 삭제
	public int deleteStep(int stepIdx);
	
}