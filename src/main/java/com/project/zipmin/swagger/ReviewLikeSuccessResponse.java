package com.project.zipmin.swagger;

import com.project.zipmin.dto.like.LikeCreateResponseDto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ReviewLikeSuccessResponse")
public class ReviewLikeSuccessResponse {
	
    @Schema(example = "REVIEW_LIKE_SUCCESS", description = "성공 코드")
    public String code;

    @Schema(example = "리뷰 좋아요 성공", description = "성공 메시지")
    public String message;

    @Schema(implementation = LikeCreateResponseDto.class, description = "응답 데이터")
    public Object data;

}