package com.project.zipmin.swagger;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "UserDeleteFailResponse")
public class UserDeleteFailResponse {
	
    @Schema(example = "USER_DELETE_FAIL", description = "에러 코드")
    public String code;

    @Schema(example = "사용자 삭제 실패", description = "에러 메시지")
    public String message;

    @Schema(nullable = true, description = "응답 데이터")
    public Object data;

}
