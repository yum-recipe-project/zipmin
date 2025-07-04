package com.project.zipmin.api;

import org.springframework.http.HttpStatus;

	public interface Code {
		String getCode();
		String getMessage();
		HttpStatus getStatus();
	}

	