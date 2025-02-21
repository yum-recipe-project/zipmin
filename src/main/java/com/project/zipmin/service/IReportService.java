package com.project.zipmin.service;

import org.springframework.stereotype.Service;

@Service
public interface IReportService {
	
	// 로그인한 유저가 특정 레시피를 신고했는지 확인
	Boolean selectReportStatusByRecipeIdx(String id, Integer recipeIdx);
	
}