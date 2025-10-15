package com.project.zipmin.swagger;

import com.project.zipmin.dto.ClassReadResponseDto;
import com.project.zipmin.dto.LikeCreateResponseDto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ClassReadListSuccessResponse")
public class ClassReadListSuccessResponse {
	
    @Schema(example = "CLASS_READ_LIST_SUCCESS", description = "에러 코드")
    public String code;

    @Schema(example = "클래스 목록 조회 성공", description = "에러 메시지")
    public String message;

    @Schema(implementation = ClassReadResponseDto.class, description = "응답 데이터")
    public Object data;

}
