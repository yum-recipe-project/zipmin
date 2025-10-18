package com.project.zipmin.swagger;

import com.project.zipmin.dto.GuideReadMySavedResponseDto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "GuideReadMySavedListSuccessResponse")
public class GuideReadMySavedListSuccessResponse {
	
    @Schema(example = "USER_READ_LIST_SUCCESS", description = "성공 코드")
    public String code;

    @Schema(example = "댓글 목록 조회 성공", description = "에러 메시지")
    public String message;

    @Schema(implementation = GuideReadMySavedResponseDto.class, description = "응답 데이터")
    public Object data;

}