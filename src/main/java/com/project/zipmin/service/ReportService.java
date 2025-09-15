package com.project.zipmin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.project.zipmin.api.ApiException;
import com.project.zipmin.api.LikeErrorCode;
import com.project.zipmin.api.ReportErrorCode;
import com.project.zipmin.dto.ReportCreateRequestDto;
import com.project.zipmin.dto.ReportCreateResponseDto;
import com.project.zipmin.dto.ReportDeleteRequestDto;
import com.project.zipmin.dto.ReportReadResponseDto;
import com.project.zipmin.dto.UserReadResponseDto;
import com.project.zipmin.entity.Report;
import com.project.zipmin.entity.Role;
import com.project.zipmin.mapper.ReportMapper;
import com.project.zipmin.repository.ReportRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportService {

	private final ReportRepository reportRepository;
	
	private final UserService userService;
	
	private final ReportMapper reportMapper;
	
	
	
	
	
	// 신고 목록
	public List<ReportReadResponseDto> readReportListByTablenameAndRecodenum(String tablename, Integer recodenum) {
		
		// 입력값 검증
		if (tablename == null || recodenum == null) {
			throw new ApiException(ReportErrorCode.REPORT_INVALID_INPUT);
		}
		
		// 신고 목록 조회
		List<Report> reportList = null;
		try {
			reportList = reportRepository.findAllByTablenameAndRecodenum(tablename, recodenum);
		}
		catch (Exception e) {
			throw new ApiException(ReportErrorCode.REPORT_READ_LIST_FAIL);
		}
		
		// 신고 목록 응답 구성
		List<ReportReadResponseDto> reportDtoList = new ArrayList<>();
		for (Report report : reportList) {
			ReportReadResponseDto reportDto = reportMapper.toReadResponseDto(report);
			reportDto.setUsername(userService.readUserById(reportDto.getUserId()).getUsername());
			reportDto.setNickname(userService.readUserById(reportDto.getUserId()).getNickname());
			
			reportDtoList.add(reportDto);
		}
		
		return reportDtoList;
	}
	
	
	
	// 신고 작성
	public ReportCreateResponseDto createReport(ReportCreateRequestDto reportDto) {
		
		// 입력값 검증
		if (reportDto == null || reportDto.getTablename() == null
				|| reportDto.getRecodenum() == null || reportDto.getReason() == null
				|| reportDto.getUserId() == null) {
			throw new ApiException(ReportErrorCode.REPORT_INVALID_INPUT);
		}
		
		// 중복 신고 검사
		if (reportRepository.existsByTablenameAndRecodenumAndUserId(reportDto.getTablename(), reportDto.getRecodenum(), reportDto.getUserId())) {
			throw new ApiException(ReportErrorCode.REPORT_DUPLICATE);
		}
		
		// 신고 저장
		Report report = reportMapper.toEntity(reportDto);
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
		if (reportDto == null || reportDto.getTablename() == null
				|| reportDto.getRecodenum() == null || reportDto.getUserId() == null) {
			throw new ApiException(ReportErrorCode.REPORT_INVALID_INPUT);
		}
		
		// 신고 존재 여부 판단
		Report report = reportRepository.findByTablenameAndRecodenumAndUserId(reportDto.getTablename(), reportDto.getRecodenum(), reportDto.getUserId())
			.orElseThrow(() -> new ApiException(ReportErrorCode.REPORT_NOT_FOUND));

		// 권한 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserReadResponseDto user = userService.readUserByUsername(username);
		if (!user.getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			// 관리자
			if (user.getRole().equals(Role.ROLE_ADMIN.name())) {
				if (report.getUser().getRole().equals(Role.ROLE_SUPER_ADMIN)) {
					throw new ApiException(ReportErrorCode.REPORT_FORBIDDEN);
				}
				if (report.getUser().getRole().equals(Role.ROLE_ADMIN)) {
					if (user.getId() != report.getUser().getId()) {
						throw new ApiException(ReportErrorCode.REPORT_FORBIDDEN);
					}
				}
			}
			// 일반 회원
			else {
				if (user.getId() != report.getUser().getId()) {
					throw new ApiException(ReportErrorCode.REPORT_FORBIDDEN);
				}
			}
		}
		
		// 신고 삭제
		try {
			reportRepository.deleteById(report.getId());
		}
		catch (Exception e) {
			throw new ApiException(ReportErrorCode.REPORT_DELETE_FAIL);
		}
	}
	
	
	
	
	
	// 신고수 조회
	public int countReport(String tablename, Integer recodenum) {
		
		// 입력값 검증
		if (tablename == null || recodenum == null) {
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
