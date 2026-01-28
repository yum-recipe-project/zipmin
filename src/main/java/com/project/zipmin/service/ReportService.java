package com.project.zipmin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.project.zipmin.api.ApiException;
import com.project.zipmin.api.ReportErrorCode;
import com.project.zipmin.dto.UserReadResponseDto;
import com.project.zipmin.dto.report.ReportCreateRequestDto;
import com.project.zipmin.dto.report.ReportCreateResponseDto;
import com.project.zipmin.dto.report.ReportReadRequestDto;
import com.project.zipmin.dto.report.ReportReadResponseDto;
import com.project.zipmin.entity.Report;
import com.project.zipmin.entity.Role;
import com.project.zipmin.mapper.ReportMapper;
import com.project.zipmin.repository.ReportRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportService {

	private final ReportRepository reportRepository;
	private final ReportMapper reportMapper;
	private final UserService userService;
	
	
	

	
	// 신고 목록 조회
	public List<ReportReadResponseDto> readReportList(ReportReadRequestDto reportRequestDto) {
		
		// 입력값 검증
		if (reportRequestDto == null
				|| reportRequestDto.getTablename() == null
				|| reportRequestDto.getRecodenum() == 0) {
			throw new ApiException(ReportErrorCode.REPORT_INVALID_INPUT);
		}
		
		// 신고 목록 조회
		List<Report> reportList;
		try {
			reportList = reportRepository.findAllByTablenameAndRecodenum(reportRequestDto.getTablename(), reportRequestDto.getRecodenum());
		}
		catch (Exception e) {
			throw new ApiException(ReportErrorCode.REPORT_READ_LIST_FAIL);
		}
		
		// 신고 목록 응답 구성
		List<ReportReadResponseDto> reportDtoList = new ArrayList<>();
		for (Report report : reportList) {
			ReportReadResponseDto reportResponseDto = reportMapper.toReadResponseDto(report);
			reportResponseDto.setUsername(report.getUser().getUsername());
			reportResponseDto.setNickname(report.getUser().getNickname());
			reportDtoList.add(reportResponseDto);
		}
		
		return reportDtoList;
	}
	
	
	
	
	
	// 신고 작성
	public ReportCreateResponseDto createReport(ReportCreateRequestDto reportDto) {
		
		// 입력값 검증
		if (reportDto == null
				|| reportDto.getTablename() == null
				|| reportDto.getRecodenum() == 0
				|| reportDto.getReason() == null
				|| reportDto.getUserId() == 0) {
			throw new ApiException(ReportErrorCode.REPORT_INVALID_INPUT);
		}
		
		// 중복 검사
		if (reportRepository.existsByTablenameAndRecodenumAndUserId(reportDto.getTablename(), reportDto.getRecodenum(), reportDto.getUserId())) {
			throw new ApiException(ReportErrorCode.REPORT_DUPLICATE);
		}
		
		// 신고 저장
		try {
			Report report = reportMapper.toEntity(reportDto);
			report = reportRepository.save(report);
			return reportMapper.toCreateResponseDto(report);
		}
		catch (Exception e) {
			throw new ApiException(ReportErrorCode.REPORT_CREATE_FAIL);
		}
	}
	
	
	
	
	
	// 신고 삭제
	public void deleteReport(int id) {
		
		// 입력값 검증
		if (id == 0) {
			throw new ApiException(ReportErrorCode.REPORT_INVALID_INPUT);
		}
		
		// 신고 조회
		Report report;
		try {
			report = reportRepository.findById(id);
		}
		catch (Exception e) {
			throw new ApiException(ReportErrorCode.REPORT_NOT_FOUND);
		}
		
		// 권한 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserReadResponseDto currentUser = userService.readUserByUsername(username);
		
		if (!currentUser.getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			if (currentUser.getRole().equals(Role.ROLE_ADMIN.name())) {
				if (report.getUser().getRole().equals(Role.ROLE_SUPER_ADMIN)) {
					throw new ApiException(ReportErrorCode.REPORT_FORBIDDEN);
				}
				if (report.getUser().getRole().equals(Role.ROLE_ADMIN) && currentUser.getId() != report.getUser().getId()) {
					throw new ApiException(ReportErrorCode.REPORT_FORBIDDEN);
				}
			}
			else if (currentUser.getRole().equals(Role.ROLE_USER.name()) && currentUser.getId() != report.getUser().getId()) {
				throw new ApiException(ReportErrorCode.REPORT_FORBIDDEN);
			}
		}
		
		// 신고 삭제
		try {
			reportRepository.deleteById(id);
		}
		catch (Exception e) {
			throw new ApiException(ReportErrorCode.REPORT_DELETE_FAIL);
		}
	}
	
	
	
	
	
	// 신고수 조회
	public int countReport(String tablename, int recodenum) {
		
		// 입력값 검증
		if (tablename == null || recodenum == 0) {
			throw new ApiException(ReportErrorCode.REPORT_INVALID_INPUT);
		}
		
		// 신고수 조회
		try {
			return reportRepository.countByTablenameAndRecodenum(tablename, recodenum);
		}
		catch (Exception e) {
			throw new ApiException(ReportErrorCode.REPORT_COUNT_FAIL);
		}
	}

}
