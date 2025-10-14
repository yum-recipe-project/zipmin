package com.project.zipmin.swagger;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ClassCountAttendSuccessResponse")
public class ClassCountAttendSuccessResponse {
	
    @Schema(example = "CLASS_COUNT_ATTEND_SUCCESS", description = "에러 코드")
    public String code;

    @Schema(example = "클래스 출석 집계 성공", description = "에러 메시지")
    public String message;

    @Schema(implementation = Integer.class, description = "응답 데이터")
    public Object data;

}
