package com.project.zipmin.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.project.zipmin.dto.ReportDTO;
import com.project.zipmin.entity.Report;

@Mapper(componentModel = "spring")
public interface ReportMapper {
	ReportDTO reportToReportDTO(Report report);
	Report reportDTOToReport(ReportDTO reportDTO);
	List<ReportDTO> reportListToReportDTOList(List<Report> reportList);
}
