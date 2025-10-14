package com.project.zipmin.swagger;

import com.project.zipmin.dto.ClassApplyUpdateResponseDto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ClassApplyUpdateSuccessResponse")
public class ClassApplyUpdateSuccessResponse {
	
    @Schema(example = "CLASS_APPLY_UPDATE_SUCCESS", description = "에러 코드")
    public String code;

    @Schema(example = "클래스 신청 수정 성공", description = "에러 메시지")
    public String message;

    @Schema(implementation = ClassApplyUpdateResponseDto.class, description = "응답 데이터")
    public Object data;

}
