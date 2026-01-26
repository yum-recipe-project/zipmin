package com.project.zipmin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.project.zipmin.dto.report.ReportCreateRequestDto;
import com.project.zipmin.dto.report.ReportCreateResponseDto;
import com.project.zipmin.dto.report.ReportReadRequestDto;
import com.project.zipmin.dto.report.ReportReadResponseDto;
import com.project.zipmin.entity.Report;

@Mapper(componentModel = "spring")
public interface ReportMapper {
	
	// Read
	Report toEntity(ReportReadRequestDto reportDto);
	
	ReportReadRequestDto toReadRequestDto(Report report);
	
	@Mapping(target = "user.id", source = "userId")
	Report toEntity(ReportReadResponseDto reportDto);
	
	@Mapping(target = "userId", source = "user.id")
	ReportReadResponseDto toReadResponseDto(Report report);
	
	
	
	// Create
	@Mapping(target = "user.id", source = "userId")
	Report toEntity(ReportCreateRequestDto reportDto);
	
	@Mapping(target = "userId", source = "user.id")
	ReportCreateRequestDto toCreateRequestDto(Report report);
	
	@Mapping(target = "user.id", source = "userId")
	Report toEntity(ReportCreateResponseDto reportDto);
	
	@Mapping(target = "userId", source = "user.id")
	ReportCreateResponseDto toCreateResponseDto(Report report);
	
}
