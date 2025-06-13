package com.boardv4.exception.base;

public class ForbiddenException extends RuntimeException {
    public ForbiddenException() {
        super("권한이 없습니다.");
    }
}
