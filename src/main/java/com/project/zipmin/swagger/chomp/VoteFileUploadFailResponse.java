package com.project.zipmin.swagger.chomp;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "VoteFileUploadFailResponse")
public class VoteFileUploadFailResponse {
	
    @Schema(example = "VOTE_FILE_UPLOAD_FAIL", description = "에러 코드")
    public String code;

    @Schema(example = "투표 파일 업로드 실패", description = "에러 메시지")
    public String message;

    @Schema(nullable = true, description = "응답 데이터")
    public Object data;

}
