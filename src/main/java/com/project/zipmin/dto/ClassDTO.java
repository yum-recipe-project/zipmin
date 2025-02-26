package com.project.zipmin.dto;

import java.sql.Time;
import java.util.Date;

import lombok.Data;

@Data
public class ClassDTO {
	private int id;
	private String user_id;
	private String title;
	
	private String content;
	// 소개랑 누구한테 (누구한테는 여러개임)
	private int place; // 장소
	private Date eventdate;
	private Time eventtime;
	// 클래스 이름 name? title?
	// 승인 여부
	// 선정인원
	// 준비물
	
}
