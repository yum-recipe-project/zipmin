package com.project.zipmin.service;

import org.springframework.stereotype.Service;

@Service
public interface IReportService {
	
	// 테이블 이름과 일련번호를 이용해 신고 수 조회
	public int selectReportCountByTable(String tablename, int recodenum);
	
	// 로그인한 유저가 특정 테이블의 레코드에 좋아요를 눌렀는지 확인
	public boolean selectReportStatusByTable(String id, String tablename, int recodenum);
	
	// 테이블 이름과 일련번호를 이용해 신고 목록 삭제
	public int deleteReportListByTable(String tablename, int recodenum);
	
}