package com.project.zipmin.swagger;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ClassApplyUpdateFailResponse")
public class ClassApplyUpdateFailResponse {
	
    @Schema(example = "CLASS_APPLY_UPDATE_FAIL", description = "에러 코드")
    public String code;

    @Schema(example = "클래스 신청 수정 실패", description = "에러 메시지")
    public String message;

    @Schema(nullable = true, description = "응답 데이터")
    public Object data;

}
