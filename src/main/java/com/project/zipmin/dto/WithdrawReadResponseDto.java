package com.project.zipmin.dto;

import java.util.Date;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class WithdrawReadResponseDto {

	private int id;
    private int requestPoint;
    private Date requestDate;
    private int status;
    private Date completeDate;
	
    // 관리자 필요
    private Integer userId;           // 출금 요청한 사용자 ID
    private String username;          // 사용자 계정명
}
