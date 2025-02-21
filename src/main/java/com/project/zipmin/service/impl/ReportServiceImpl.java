package com.project.zipmin.service.impl;

import org.springframework.stereotype.Service;

import com.project.zipmin.service.IReportService;

@Service
public class ReportServiceImpl implements IReportService {

	@Override
	public Boolean selectReportStatusByRecipeIdx(String id, Integer recipeIdx) {
		return false;
	}

}
