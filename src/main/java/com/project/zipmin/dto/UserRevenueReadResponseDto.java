package com.project.zipmin.dto;

import java.util.Date;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserRevenueReadResponseDto {

    private int fundId;             
    private int funderId;           
    private String funderNickname;  
    private int recipeId;           
    private String recipeTitle;     
    private int point;              
    private Date fundDate;          
    private int status;             
    
}
