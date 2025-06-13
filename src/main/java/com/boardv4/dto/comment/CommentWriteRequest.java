package com.boardv4.dto.comment;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentWriteRequest {
    private String content;
}
