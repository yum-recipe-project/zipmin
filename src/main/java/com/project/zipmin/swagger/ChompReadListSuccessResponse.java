package com.project.zipmin.swagger;

import java.util.List;

import com.project.zipmin.dto.ChompReadResponseDto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ChompReadListSuccessResponse")
public class ChompReadListSuccessResponse {

    @Schema(example = "CHOMP_READ_LIST_SUCCESS", description = "응답 코드")
    public String code;

    @Schema(example = "쩝쩝박사 목록 조회 성공", description = "응답 메시지")
    public String message;

    @Schema(description = "페이징된 쩝쩝박사 목록")
    public ChompPageData data;

    @Schema(name = "ChompPageData", description = "쩝쩝박사 목록의 페이징 데이터 구조")
    public static class ChompPageData {

        @Schema(description = "쩝쩝박사 항목 리스트")
        public List<ChompReadResponseDto> content;

        @Schema(example = "2", description = "전체 페이지 수")
        public int totalPages;

        @Schema(example = "12", description = "전체 데이터 수")
        public long totalElements;

        @Schema(example = "10", description = "페이지 크기")
        public int size;

        @Schema(example = "0", description = "현재 페이지 번호")
        public int number;

        @Schema(example = "10", description = "현재 페이지의 데이터 개수")
        public int numberOfElements;

        @Schema(example = "true", description = "첫 페이지 여부")
        public boolean first;

        @Schema(example = "false", description = "마지막 페이지 여부")
        public boolean last;

        @Schema(example = "false", description = "현재 페이지가 비어 있는지 여부")
        public boolean empty;
    }
    
}
