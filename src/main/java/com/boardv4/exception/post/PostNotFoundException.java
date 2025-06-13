package com.boardv4.exception.post;

import com.boardv4.exception.base.NotFoundException;

public class PostNotFoundException extends NotFoundException {
    public PostNotFoundException(Long postId) {
        super("해당 게시글을 찾을 수 없습니다. (id: " + postId + ")");
    }
}