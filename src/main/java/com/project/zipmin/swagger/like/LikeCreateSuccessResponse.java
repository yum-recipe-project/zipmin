package com.project.zipmin.swagger.like;

import com.project.zipmin.dto.like.LikeCreateResponseDto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "LikeCreateSuccessResponse")
public class LikeCreateSuccessResponse {
	
    @Schema(example = "LIKE_CREATE_SUCCESS", description = "에러 코드")
    public String code;

    @Schema(example = "좋아요 작성 성공", description = "에러 메시지")
    public String message;

    @Schema(implementation = LikeCreateResponseDto.class, description = "응답 데이터")
    public Object data;

}
