package com.project.zipmin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.zipmin.api.ApiResponse;
import com.project.zipmin.api.ReportSuccessCode;
import com.project.zipmin.dto.ReportReadResponseDto;
import com.project.zipmin.service.ReportService;

import io.swagger.v3.oas.annotations.Parameter;

@RestController
public class ReportController {
	
	@Autowired
	ReportService reportService;
	
	
	
	
	
	@GetMapping("/reports")
	public ResponseEntity<?> readReport(
			@Parameter(description = "테이블 이름", required = false, example = "recipe") @RequestParam(required = false) String tablename,
			@Parameter(description = "레코드 번호", required = false, example = "1") @RequestParam(required = false) Integer recodenum) {
		
		// 여기서 관리자 인지도 해야될듯 *********************
		
		List<ReportReadResponseDto> reportList = reportService.readReportListByTablenameAndRecodenum(tablename, recodenum);
		
		return ResponseEntity.status(ReportSuccessCode.REPORT_READ_LIST_SUCCESS.getStatus())
        		.body(ApiResponse.success(ReportSuccessCode.REPORT_READ_LIST_SUCCESS, reportList));
	}
	
	
	
	
	
}
