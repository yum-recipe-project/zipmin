package com.project.zipmin.swagger;

import com.project.zipmin.dto.ReviewUpdateResponseDto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ReviewUpdateSuccessResponse")
public class ReviewUpdateSuccessResponse {
	
    @Schema(example = "REVIEW_UPDATE_SUCCESS", description = "성공 코드")
    public String code;

    @Schema(example = "리뷰 수정 성공", description = "성공 메시지")
    public String message;

    @Schema(implementation = ReviewUpdateResponseDto.class, description = "응답 데이터")
    public Object data;

}