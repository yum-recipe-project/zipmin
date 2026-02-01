package com.project.zipmin.swagger.fridge;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "FridgeStorageDeleteSuccessResponse")
public class FridgeStorageDeleteSuccessResponse {
	
    @Schema(example = "FRIDGE_STORAGE_DELETE_SUCCESS", description = "에러 코드")
    public String code;

    @Schema(example = "나의 냉장고 삭제 성공", description = "에러 메시지")
    public String message;

    @Schema(nullable = true, description = "응답 데이터")
    public Object data;

}
