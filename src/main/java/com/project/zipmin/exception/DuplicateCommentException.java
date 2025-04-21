package com.project.zipmin.exception;

// 409 - Conflict
public class DuplicateCommentException extends RuntimeException {
	public DuplicateCommentException(String message) {
		super(message);
	}
}