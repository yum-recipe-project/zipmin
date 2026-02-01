package com.project.zipmin.swagger.fridge;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "FridgeStorageCreateSuccessResponse")
public class FridgeStorageCreateSuccessResponse {
	
    @Schema(example = "FRIDGE_STORAGE_CREATE_SUCCESS", description = "에러 코드")
    public String code;

    @Schema(example = "나의 냉장고 목록 조회 성공", description = "에러 메시지")
    public String message;

    @Schema(nullable = true, description = "응답 데이터")
    public Object data;

}
