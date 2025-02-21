package com.project.zipmin.dto;

import java.sql.Time;
import java.util.Date;

import lombok.Data;

@Data
public class ClassDTO {
	private int class_idx;
	// 개최하는 사람
	// 소개랑 누구한테 (누구한테는 여러개임)
	private int place; // 장소
	private Date eventdate;
	private Time eventtime; // 상황에 따라서 start end 필요할 수도 있음
	// 클래스 이름 name? title?
	
}
