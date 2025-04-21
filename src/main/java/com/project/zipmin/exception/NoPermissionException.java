package com.project.zipmin.exception;

// 403 - Forbidden
public class NoPermissionException extends RuntimeException {
	public NoPermissionException(String message) {
		super(message);
	}
}