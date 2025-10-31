package com.project.zipmin.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ClassTutorCreateResponseDto {
    private Integer id;     
    private Integer classId; 
    private String name; 
    private String career; 
    private String image;  
}

