package com.project.zipmin.dto;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
public class RecipeStockMemoCreateRequestDto {

	private List<MemoItem> memoList;

    @Data
    public static class MemoItem {
        private String name;
        private String amount;
        private String unit;
        private String note;
    }
	
}
