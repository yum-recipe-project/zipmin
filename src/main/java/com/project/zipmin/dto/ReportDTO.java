package com.project.zipmin.dto;

import lombok.Data;

@Data
public class ReportDTO {
	private int report_idx;
	private String member_ref; // 누가
	private String tablename; // 어떤 테이블의
	private int recodenum; // 어떤 게시물을
	private String reason; // 어떤 이유로
}