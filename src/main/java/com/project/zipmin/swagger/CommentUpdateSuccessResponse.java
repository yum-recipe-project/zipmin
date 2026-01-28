package com.project.zipmin.swagger;

import com.project.zipmin.dto.comment.CommentUpdateResponseDto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "CommentUpdateSuccessResponse")
public class CommentUpdateSuccessResponse {
	
    @Schema(example = "COMMENT_UPDATE_SUCCESS", description = "에러 코드")
    public String code;

    @Schema(example = "댓글 수정 성공", description = "에러 메시지")
    public String message;

    @Schema(implementation = CommentUpdateResponseDto.class, description = "응답 데이터")
    public Object data;

}