package com.project.zipmin.swagger.fridge;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "FridgeLikeFailResponse")
public class FridgeLikeFailResponse {
	
    @Schema(example = "FRIDGE_LIKE_FAIL", description = "에러 코드")
    public String code;

    @Schema(example = "냉장고 좋아요 실패", description = "에러 메시지")
    public String message;

    @Schema(nullable = true, description = "응답 데이터")
    public Object data;

}
