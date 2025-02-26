package com.project.zipmin.dto;

import java.util.Date;

import lombok.Data;

@Data
public class GuideDTO {
	private int id;
	private String title;
	private String subtitle;
	private Date postdate;
	private String content;
}