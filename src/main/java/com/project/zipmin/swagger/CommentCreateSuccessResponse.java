package com.project.zipmin.swagger;

import com.project.zipmin.dto.CommentCreateResponseDto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "CommentCreateSuccessResponse")
public class CommentCreateSuccessResponse {
	
    @Schema(example = "COMMENT_CREATE_SUCCESS", description = "에러 코드")
    public String code;

    @Schema(example = "댓글 작성 성공", description = "에러 메시지")
    public String message;

    @Schema(implementation = CommentCreateResponseDto.class, description = "응답 데이터")
    public Object data;

}