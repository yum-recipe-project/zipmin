package com.project.zipmin.api;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class ApiResponse<T> {
	private String code; // 비즈니스 상태코드
	private String message;
	private T data;
	
	public ApiResponse(String code, String message, T data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}
	
	public static <T> ApiResponse<T> success(T data) {
		return new ApiResponse<>("SUCCESS", "요청이 정상 처리되었습니다.", data);
	}
	
	public static <T> ApiResponse<T> of(String code, String message, T data) {
		return new ApiResponse<>(code, message, data);
	}
	
	public static <T> ApiResponse<T> error(String code, String message) {
        return new ApiResponse<>(code, message, null);
    }
}