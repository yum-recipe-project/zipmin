package com.project.zipmin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.zipmin.api.ApiException;
import com.project.zipmin.api.ReportErrorCode;
import com.project.zipmin.dto.ReportCreateRequestDto;
import com.project.zipmin.dto.ReportCreateResponseDto;
import com.project.zipmin.dto.ReportDeleteRequestDto;
import com.project.zipmin.entity.Report;
import com.project.zipmin.mapper.ReportMapper;
import com.project.zipmin.repository.ReportRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportService {
	
	@Autowired
	private ReportRepository reportRepository;
	
	private final ReportMapper reportMapper;
	
	
	public int selectReportCountByTable(String tablename, int recodenum) {
		// TODO Auto-generated method stub
		return 0;
	}
	public boolean selectReportStatusByTable(String id, String tablename, int recodenum) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
	// 신고 작성
	public ReportCreateResponseDto createReport(ReportCreateRequestDto reportDto) {
		
		// 입력값 검증
		if (reportDto == null || reportDto.getTablename() == null || reportDto.getRecodenum() == null || reportDto.getReason() == null || reportDto.getUserId() == null) {
			throw new ApiException(ReportErrorCode.REPORT_INVALID_INPUT);
		}
		
		// 중복 신고 검사
		if (reportRepository.existsByTablenameAndRecodenumAndUserId(reportDto.getTablename(), reportDto.getRecodenum(), reportDto.getUserId())) {
			throw new ApiException(ReportErrorCode.REPORT_DUPLICATE);
		}
		
		Report report = reportMapper.toEntity(reportDto);
		
		// 신고 저장
		try {
			report = reportRepository.save(report);
			return reportMapper.toCreateResponseDto(report);
		}
		catch (Exception e) {
			throw new ApiException(ReportErrorCode.REPORT_CREATE_FAIL);
		}
	}
	
	
	
	// 신고 삭제
	public void deleteReport(ReportDeleteRequestDto reportDto) {
		
		// 입력값 검증
		if (reportDto == null || reportDto.getTablename() == null || reportDto.getRecodenum() == null || reportDto.getUserId() == null) {
			throw new ApiException(ReportErrorCode.REPORT_INVALID_INPUT);
		}
		
		// 신고 존재 여부 판단
		Report report = reportRepository.findByTablenameAndRecodenumAndUserId(reportDto.getTablename(), reportDto.getRecodenum(), reportDto.getUserId())
			.orElseThrow(() -> new ApiException(ReportErrorCode.REPORT_NOT_FOUND));

		// 신고 삭제
		try {
			reportRepository.deleteById(report.getId());
		}
		catch (Exception e) {
			throw new ApiException(ReportErrorCode.REPORT_DELETE_FAIL);
		}
		
	}

}
