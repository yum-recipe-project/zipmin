package com.project.zipmin.swagger;

import com.project.zipmin.dto.ReviewReadMyResponseDto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "UserReviewReadListSuccessResponse")
public class UserReviewReadListSuccessResponse {
	
    @Schema(example = "USER_READ_LIST_SUCCESS", description = "성공 코드")
    public String code;

    @Schema(example = "작성자 리뷰 목록 조회 성공", description = "성공 메시지")
    public String message;

    @Schema(implementation = ReviewReadMyResponseDto.class, description = "응답 데이터")
    public Object data;

}