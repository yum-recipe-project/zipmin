package com.project.zipmin.swagger;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ClassTargetReadListFailResponse")
public class ClassTargetReadListFailResponse {
	
    @Schema(example = "CLASS_TARGET_READ_LIST_FAIL", description = "에러 코드")
    public String code;

    @Schema(example = "클래스 교육대상 목록 조회 실패", description = "에러 메시지")
    public String message;

    @Schema(nullable = true, description = "응답 데이터")
    public Object data;

}
