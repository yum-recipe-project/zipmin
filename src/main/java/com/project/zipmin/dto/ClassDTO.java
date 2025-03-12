package com.project.zipmin.dto;

import java.util.Date;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ClassDTO {
	private int id;
	private String userId;
	private String title;
	private int place;
	private Date eventdate;
	private String starttime;
	private String endtime;
	private int headcount;
	private String need;
	private String imageUrl;
	private String target1;
	private String target2;
	private String target3;
	private String introduce;
	private boolean approval;
}
