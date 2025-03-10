package com.project.zipmin.service.impl;

import org.springframework.stereotype.Service;

import com.project.zipmin.service.IReportService;

@Service
public class ReportServiceImpl implements IReportService {

	@Override
	public int selectReportCountByTable(String tablename, int recodenum) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean selectReportStatusByTable(String id, String tablename, int recodenum) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int deleteReportListByTable(String tablename, int recodenum) {
		// TODO Auto-generated method stub
		return 0;
	}

}
