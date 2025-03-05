package com.project.zipmin.dto;

import lombok.Data;

@Data
public class ApplyDTO {
	private int apply_idx;
	private int member_ref; // 누가
	// private String  신청동기
	// 질문
	private int class_ref; // 어떤 클래스를 신청하는지
	// 출석 여부 결석 여부????
}
