package com.project.zipmin.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {
	
    OPEN(1),
    CLOSE(0);

	private final Integer code;
	
}
