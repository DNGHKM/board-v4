package com.boardv4.exception.member;

import com.boardv4.exception.base.NotFoundException;

public class MemberNotFoundException extends NotFoundException {
    public MemberNotFoundException() {
        super("유저를 찾을 수 없습니다.");
    }
}
