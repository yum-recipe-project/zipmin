package com.project.zipmin.dto;

import java.util.Date;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ClassApplyDTO {
	private int id;
	private String user_id;
	private int class_id;
	private Date applydate;
	private String motivate;
	private String question;
	private int attend;
}

