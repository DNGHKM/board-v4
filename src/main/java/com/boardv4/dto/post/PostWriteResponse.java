package com.boardv4.dto.post;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PostWriteResponse {
    private Long postId;
    private String boardEngName;

    public static PostWriteResponse from(Long postId, String boardEngName) {
        return PostWriteResponse.builder()
                .postId(postId)
                .boardEngName(boardEngName)
                .build();
    }
}
