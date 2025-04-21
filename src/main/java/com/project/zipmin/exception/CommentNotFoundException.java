package com.project.zipmin.exception;

// 404 - Not Found
public class CommentNotFoundException extends RuntimeException {
	public CommentNotFoundException(String message) {
        super(message);
    }
}
