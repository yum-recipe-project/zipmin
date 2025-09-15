package com.project.zipmin.swagger;

import com.project.zipmin.dto.CommentReadResponseDto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "CommentReadSuccessResponse")
public class CommentReadListSuccessResponse {
	
    @Schema(example = "COMMENT_READ_LIST_SUCCESS", description = "에러 코드")
    public String code;

    @Schema(example = "댓글 목록 조회 성공", description = "에러 메시지")
    public String message;

    @Schema(implementation = CommentReadResponseDto.class, description = "응답 데이터")
    public Object data;

}