package com.project.zipmin.dto.cooking;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ClassReadResponseDto {
	
	private int id;
	private String title;
	private String category;
	private String image;
	private String introduce;
	private String place;
	private Date postdate;
	private Date eventdate;
	private Date starttime;
	private Date endtime;
	private Date noticedate;
	private int headcount;
	private String need;
	private int approval;
	private int userId;
	
	// 추가 정보
	private List<ClassTargetReadResponseDto> targetList;
	private List<ClassScheduleReadResponseDto> scheduleList;
	private List<ClassTutorReadResponseDto> tutorList;
	
	private boolean isApplied;
	private int applycount;
	private boolean isOpened;
	private boolean isEvented;
	private boolean isAble;
	
	// 작성자 정보
	private String username;
	private String nickname;
	
//	private int selected;
//	private int applyId;
//	private boolean isPlanned;
	
}
