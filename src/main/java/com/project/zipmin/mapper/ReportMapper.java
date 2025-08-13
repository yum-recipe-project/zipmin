package com.project.zipmin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.project.zipmin.dto.ReportCreateRequestDto;
import com.project.zipmin.dto.ReportCreateResponseDto;
import com.project.zipmin.dto.ReportDeleteRequestDto;
import com.project.zipmin.dto.ReportReadResponseDto;
import com.project.zipmin.entity.Report;

@Mapper(componentModel = "spring")
public interface ReportMapper {
	
	// Read
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
	
	
	
	// Delete
	@Mapping(target = "user.id", source = "userId")
	Report toEntity(ReportDeleteRequestDto reportDto);
	
	@Mapping(target = "userId", source = "user.id")
	ReportDeleteRequestDto toDeleteRequestDto(Report report);
	
}
