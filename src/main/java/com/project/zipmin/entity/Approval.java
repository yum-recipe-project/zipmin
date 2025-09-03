package com.project.zipmin.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Approval {
	
    APPROVED(1),
    REJECTED(0),
    PENDING(2);

	private final Integer code;
	
}
