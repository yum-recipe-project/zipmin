package com.project.zipmin.dto;

import java.util.Date;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ClassApplyDTO {
	private int id;
	private int userId;
	// private String  신청동기
	// 질문
	private int classId;
	private Date applydate;
	// 출석 여부 결석 여부????
	// 승인 여부??
}
