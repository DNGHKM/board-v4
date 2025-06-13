package com.boardv4.exception.comment;

import com.boardv4.exception.base.NotFoundException;

public class CommentNotFoundException extends NotFoundException {
    public CommentNotFoundException(Long commentId) {
        super("해당 댓글을 찾을 수 없습니다. (id: " + commentId + ")");
    }
}