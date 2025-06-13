package com.boardv4.exception.qna;

import com.boardv4.exception.base.NotFoundException;

public class QnaNotFoundException extends NotFoundException {
    public QnaNotFoundException(Long qnaId) {
        super("해당 게시글을 찾을 수 없습니다. (id: " + qnaId + ")");
    }
}