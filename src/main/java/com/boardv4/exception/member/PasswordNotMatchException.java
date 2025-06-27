package com.boardv4.exception.member;

public class PasswordNotMatchException extends RuntimeException {
    public PasswordNotMatchException() {
        super("비밀번호가 일치하지 않습니다.");
    }
}
