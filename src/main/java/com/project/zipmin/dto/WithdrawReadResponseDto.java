package com.project.zipmin.dto;

import java.util.Date;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class WithdrawReadResponseDto {

	private int id;
    private int point;
    private Date claimdate;
    private int status;
    private Date settledate;
	
    private Integer userId;
    private String username;
}
