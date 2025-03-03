package com.project.zipmin.dto;

import java.sql.Time;
import java.util.Date;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ClassDTO {
	private int id;
	private String user_id;
	private String title;
	private String content;
	// 소개랑 누구한테 (누구한테는 여러개임)
	private int place;
	private Date eventdate;
	private Time eventtime;
	// 승인 여부
	// 선정인원
	// 준비물
	
}
