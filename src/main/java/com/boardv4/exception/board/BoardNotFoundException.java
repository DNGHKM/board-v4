package com.boardv4.exception.board;

import com.boardv4.exception.base.NotFoundException;

public class BoardNotFoundException extends NotFoundException {
    public BoardNotFoundException() {
        super("해당 게시판을 찾을 수 없습니다.");
    }
}
