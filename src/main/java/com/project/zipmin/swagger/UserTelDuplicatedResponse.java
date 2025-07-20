package com.project.zipmin.swagger;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "UserTelDuplicatedResponse")
public class UserTelDuplicatedResponse {
	
    @Schema(example = "USER_TEL_DUPLICATED", description = "에러 코드")
    public String code;

    @Schema(example = "전화번호 중복 작성 시도", description = "에러 메시지")
    public String message;

    @Schema(nullable = true, description = "응답 데이터")
    public Object data;

}
