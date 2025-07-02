package com.project.zipmin.swagger;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "MegazineUpdateFailResponse")
public class MegazineUpdateFailResponse {
	
    @Schema(example = "MEGAZINE_UPDATE_FAIL", description = "에러 코드")
    public String code;

    @Schema(example = "매거진 수성 실패", description = "에러 메시지")
    public String message;

    @Schema(nullable = true, description = "응답 데이터")
    public Object data;

}
