package com.project.zipmin.swagger;

import com.project.zipmin.dto.MegazineCreateResponseDto;
import com.project.zipmin.dto.VoteRecordCreateResponseDto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "MegazineDeleteSuccessResponse")
public class MegazineDeleteSuccessResponse {
	
    @Schema(example = "MEGAZINE_DELETE_SUCCESS", description = "에러 코드")
    public String code;

    @Schema(example = "매거진 삭제 성공", description = "에러 메시지")
    public String message;

    @Schema(nullable = true, description = "응답 데이터")
    public Object data;

}
