package com.boardv4.dto.comment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

/**
 * 댓글 작성 요청 DTO
 */
@Getter
@Builder
@Schema(description = "댓글 작성 요청 DTO")
public class CommentWriteRequest {

    @Schema(
            description = "댓글 내용",
            example = "이 글 정말 공감됩니다!",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String content;
}
