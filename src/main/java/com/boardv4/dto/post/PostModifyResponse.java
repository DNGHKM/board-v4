package com.boardv4.dto.post;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

/**
 * 게시글 수정 응답 DTO
 */
@Builder
@Getter
@Schema(description = "게시글 수정 응답 DTO")
public class PostModifyResponse {

    @Schema(description = "게시판 ID", example = "1")
    private Long boardId;

    @Schema(description = "게시글 ID", example = "42")
    private Long postId;

    public static PostModifyResponse from(Long boardId, Long postId) {
        return PostModifyResponse.builder()
                .boardId(boardId)
                .postId(postId)
                .build();
    }
}
