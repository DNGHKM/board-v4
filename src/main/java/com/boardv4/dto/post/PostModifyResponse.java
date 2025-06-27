package com.boardv4.dto.post;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PostModifyResponse {
    private Long boardId;
    private Long postId;

    public static PostModifyResponse from(Long boardId, Long postId) {
        return PostModifyResponse.builder()
                .boardId(boardId)
                .postId(postId)
                .build();
    }
}
