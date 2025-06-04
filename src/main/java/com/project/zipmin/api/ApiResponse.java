package com.project.zipmin.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

	@Data
	@AllArgsConstructor
	public class ApiResponse<T> {
		private String code;
		private String message;
		private T data;
		
		public static <T> ApiResponse<T> success(Code code, T data) {
			return new ApiResponse<>(code.getCode(), code.getMessage(), data);
		}
		
		public static <T> ApiResponse<T> error(Code code) {
	        return new ApiResponse<>(code.getCode(), code.getMessage(), null);
	    }
	}


