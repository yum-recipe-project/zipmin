package com.project.zipmin.exception;

//400 - Bad Request
public class InvalidCommentContentException extends RuntimeException {
	public InvalidCommentContentException(String message) {
		super(message);
	}
}