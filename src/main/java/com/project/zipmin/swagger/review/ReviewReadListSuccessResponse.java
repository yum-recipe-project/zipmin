package com.project.zipmin.swagger.review;

import com.project.zipmin.dto.review.ReviewReadResponseDto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ReviewReadListSuccessResponse")
public class ReviewReadListSuccessResponse {
	
    @Schema(example = "REVIEW_READ_LIST_SUCCESS", description = "성공 코드")
    public String code;

    @Schema(example = "리뷰 목록 조회 성공", description = "성공 메시지")
    public String message;

    @Schema(implementation = ReviewReadResponseDto.class, description = "응답 데이터")
    public Object data;

}