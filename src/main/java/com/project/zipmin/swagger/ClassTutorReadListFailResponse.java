package com.project.zipmin.swagger;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ClassTutorReadListFailResponse")
public class ClassTutorReadListFailResponse {
	
    @Schema(example = "CLASS_TUTOR_READ_LIST_FAIL", description = "에러 코드")
    public String code;

    @Schema(example = "클래스 강사 목록 조회 실패", description = "에러 메시지")
    public String message;

    @Schema(nullable = true, description = "응답 데이터")
    public Object data;

}
