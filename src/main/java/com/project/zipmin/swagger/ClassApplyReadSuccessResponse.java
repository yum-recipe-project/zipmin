package com.project.zipmin.swagger;

import com.project.zipmin.dto.ClassApplyReadResponseDto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ClassApplyReadSuccessResponse")
public class ClassApplyReadSuccessResponse {
	
    @Schema(example = "CLASS_APPLY_READ_SUCCESS", description = "에러 코드")
    public String code;

    @Schema(example = "클래스 신청 조회 성공", description = "에러 메시지")
    public String message;

    @Schema(implementation = ClassApplyReadResponseDto.class, description = "응답 데이터")
    public Object data;

}
