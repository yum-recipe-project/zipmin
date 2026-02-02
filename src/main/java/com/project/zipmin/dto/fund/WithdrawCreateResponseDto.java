package com.project.zipmin.dto.fund;

import java.util.Date;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class WithdrawCreateResponseDto {

	private int id;
    private int point;
    private Date claimdate;
    private Date settledate;
    private int status;
    private int userId;
    private int accoundId;
    private int adminId;
    
}
