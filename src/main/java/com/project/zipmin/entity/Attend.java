package com.project.zipmin.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Attend {
	
    ATTEND(1),
    ABSENT(0),
    PENDING(2);

	private final Integer code;
	
}
