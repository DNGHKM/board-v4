package com.boardv4.dto.post;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

/**
 * 게시글 작성 응답 DTO
 */
@Builder
@Getter
@Schema(description = "게시글 작성 응답 DTO")
public class PostWriteResponse {

    @Schema(description = "게시판 ID", example = "1")
    private Long boardId;

    @Schema(description = "작성된 게시글 ID", example = "1005")
    private Long postId;

    public static PostWriteResponse from(Long boardId, Long postId) {
        return PostWriteResponse.builder()
                .boardId(boardId)
                .postId(postId)
                .build();
    }
}
