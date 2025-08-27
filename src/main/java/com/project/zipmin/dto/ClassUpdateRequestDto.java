package com.project.zipmin.dto;

import java.util.Date;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.project.zipmin.entity.User;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ClassUpdateRequestDto {
	
	private int id;
	private String title;
	private String category;
	private String place;
	private Date postdate;
	private Date eventdate;
	private Date starttime;
	private Date endtime;
	private Date noticedate;
	private int headcount;
	private String need;
	private String image;
	private String introduce;
	// private String approval;
	private Integer userId;
	
}

