package com.project.zipmin.dto;

import java.util.Date;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ReviewCreateResponseDto {
	private Integer id;          // 리뷰 ID
    private Date postdate; // 작성일
    private Integer score;       // 별점
    private String content;      // 리뷰 내용
    private Integer recipeId;    // 어떤 레시피에 작성된 리뷰인지
    private Integer userId;      // 작성자 ID
    
    private String nickname;     // 작성자 닉네임
    private long likecount;   // 좋아요 수
    private boolean likestatus;       // 내가 좋아요 눌렀는지 여부
//    private Integer reportcount; // 신고 수
}
