package com.project.zipmin.dto;

import java.util.Date;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class WithdrawUpdateResponseDto {

	private int id;
    private int point;
    private Date claimdate;
    private Date settledate;
    private int status;
    private int userId;
    private int accountId;
    private int adminId;
    
    private String name;
    private String username;
    private String accountnum;
}
