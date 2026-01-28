package com.project.zipmin.swagger.review;

import com.project.zipmin.dto.review.ReviewCreateResponseDto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ReviewCreateSuccessResponse")
public class ReviewCreateSuccessResponse {
	
    @Schema(example = "REVIEW_CREATE_SUCCESS", description = "성공 코드")
    public String code;

    @Schema(example = "리뷰 작성 성공", description = "성공 메시지")
    public String message;

    @Schema(implementation = ReviewCreateResponseDto.class, description = "응답 데이터")
    public Object data;

}