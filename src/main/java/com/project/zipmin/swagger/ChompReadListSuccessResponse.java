package com.project.zipmin.swagger;

import java.util.List;

import com.project.zipmin.dto.chomp.ChompReadResponseDto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ChompReadListSuccessResponse")
public class ChompReadListSuccessResponse {

    @Schema(example = "CHOMP_READ_LIST_SUCCESS", description = "응답 코드")
    public String code;

    @Schema(example = "쩝쩝박사 목록 조회 성공", description = "응답 메시지")
    public String message;

    @Schema(nullable = true, description = "응답 데이터")
    public ChompData data;

    @Schema(name = "ChompData", description = "쩝쩝박사 목록")
    public static class ChompData {

        @Schema(description = "쩝쩝박사 목록")
        public List<ChompReadResponseDto> content;

        @Schema(description = "전체 페이지 수")
        public int totalPages;

        @Schema(description = "전체 데이터 수")
        public long totalElements;

        @Schema(description = "페이지 크기")
        public int size;

        @Schema(description = "페이지 번호")
        public int number;

        @Schema(description = "현재 페이지의 데이터 개수")
        public int numberOfElements;

        @Schema(description = "첫 페이지 여부")
        public boolean first;

        @Schema(description = "마지막 페이지 여부")
        public boolean last;

        @Schema(description = "현재 페이지가 비어 있는지 여부")
        public boolean empty;
    }
    
}
