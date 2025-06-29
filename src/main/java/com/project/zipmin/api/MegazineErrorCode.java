package com.project.zipmin.api;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MegazineErrorCode implements Code {
	
	MEGAZINE_NOT_FOUND(HttpStatus.NOT_FOUND, "매거진이 존재하지 않습니다.");
	
	private final HttpStatus status;
	private final String message;
	
	@Override
	public String getCode() {
		return this.name();
	}
}
