package com.project.zipmin.dto.fund;

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
    private Date settledate;
    private int status;
    private int userId;
    private int accountId;
    
    private String name;
    private String username;
    private String accountnum;
}
