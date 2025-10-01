package com.project.zipmin.dto;

import java.util.Date;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ReviewReadMyResponseDto {

    private Integer id;       
    private String content;   
    private Integer score;    
    private Date postdate;
    private String title;  
    private Integer recipeId;
    private Integer userId;  
    private String nickname;  
    private Integer likecount;
    private Boolean liked;     
}
