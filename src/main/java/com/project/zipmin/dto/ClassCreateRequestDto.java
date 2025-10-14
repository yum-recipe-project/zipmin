package com.project.zipmin.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ClassCreateRequestDto {
	private String title;
    private String category;
    private String image;
    private String introduce;
    private String place;
    private Date eventdate;
    private Date starttime;
    private Date endtime;
    private Date noticedate;
    private int headcount;
    private String need;
    private int userId;

    private List<String> targetList;
    private List<ClassScheduleCreateDto> scheduleList;
    private List<ClassTutorCreateDto> tutorList;
}

