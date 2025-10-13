package com.project.zipmin.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Selected {
	
    SELECTED(1),
    UNSELECTED(0),
    PENDING(2);

	private final Integer code;
	
}
