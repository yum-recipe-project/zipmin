package com.project.zipmin.swagger;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "GuideUnlikeSuccessResponse")
public class GuideUnlikeSuccessResponse {
	
    @Schema(example = "KITCHEN_UNLIKE_SUCCESS", description = "성공 코드")
    public String code;

    @Schema(example = "키친가이드 좋아요 취소 성공", description = "성공 메시지")
    public String message;

    @Schema(nullable = true, description = "응답 데이터")
    public Object data;

}