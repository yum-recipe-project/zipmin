package com.project.zipmin.swagger;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ReviewUnlikeFailResponse")
public class ReviewUnlikeFailResponse {
	
    @Schema(example = "REVIEW_UNLIKE_FAIL", description = "에러 코드")
    public String code;

    @Schema(example = "리뷰 좋아요 취소 실패", description = "에러 메시지")
    public String message;

    @Schema(nullable = true, description = "응답 데이터")
    public Object data;

}