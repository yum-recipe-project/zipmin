package com.project.zipmin.dto;

import lombok.Data;

@Data
public class ClassApplyDTO {
	private int id;
	private int user_id; // 누가
	// private String  신청동기
	// 질문
	private int class_id; // 어떤 클래스를 신청하는지
	// 출석 여부 결석 여부????
	// 승인 여부??
}
