package com.boardv4.dto.comment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 댓글 응답 DTO
 */
@Getter
@Builder
@Schema(description = "댓글 응답 DTO")
public class CommentResponse {

    @Schema(description = "댓글 ID", example = "101")
    private Long id;

    @Schema(description = "작성자 이름", example = "김동하")
    private String name;

    @Schema(description = "작성자 ID", example = "dongha123")
    private String username;

    @Schema(description = "댓글 내용", example = "좋은 글 감사합니다.")
    private String content;

    @Schema(description = "작성 일시", example = "2025-06-30T14:52:30")
    private LocalDateTime createAt;
}
