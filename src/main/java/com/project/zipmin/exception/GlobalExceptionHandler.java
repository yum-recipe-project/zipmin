package com.project.zipmin.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	// 해당 ID로 댓글을 찾을 수 없는 경우
    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<String> handleNotFound(CommentNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    // 권한 없는 사용자가 댓글을 수정/삭제 하는 경우
    @ExceptionHandler(NoPermissionException.class)
    public ResponseEntity<String> handleForbidden(NoPermissionException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }

    // 댓글 내용이 잘못되었거나 삭제된 경우
    @ExceptionHandler(InvalidCommentContentException.class)
    public ResponseEntity<String> handleBadRequest(InvalidCommentContentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    // 동일한 댓글이 중복 작성되었을 경우
    @ExceptionHandler(DuplicateCommentException.class)
    public ResponseEntity<String> handleConflict(DuplicateCommentException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    // 알 수 없는 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다.");
    }
}
