package com.project.zipmin.swagger.fridge;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "FridgeUnlikeFailResponse")
public class FridgeUnlikeFailResponse {
	
    @Schema(example = "FRIDGE_UNLIKE_FAIL", description = "에러 코드")
    public String code;

    @Schema(example = "냉장고 좋아요 취소 실패", description = "에러 메시지")
    public String message;

    @Schema(nullable = true, description = "응답 데이터")
    public Object data;

}
