package com.project.zipmin.swagger;

import com.project.zipmin.dto.ClassApplyCreateResponseDto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ClassApplyCreateSuccessResponse")
public class ClassApplyCreateSuccessResponse {
	
    @Schema(example = "CLASS_APPLY_CREATE_SUCCESS", description = "에러 코드")
    public String code;

    @Schema(example = "클래스 신청 작성 성공", description = "에러 메시지")
    public String message;

    @Schema(implementation = ClassApplyCreateResponseDto.class, description = "응답 데이터")
    public Object data;

}
