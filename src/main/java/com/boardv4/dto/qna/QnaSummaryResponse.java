package com.boardv4.dto.qna;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Q&A 요약 응답 DTO
 */
@Builder
@Getter
@Schema(description = "Q&A 요약 응답 DTO")
public class QnaSummaryResponse {

    @Schema(description = "Q&A ID", example = "101")
    private Long id;

    @Schema(description = "질문 제목", example = "JWT 인증 방식에 대해 궁금합니다.")
    private String subject;

    @Schema(description = "작성자 이름", example = "김동하")
    private String writerName;

    @Schema(description = "작성자 아이디", example = "dongha123")
    private String writerUsername;

    @Schema(description = "답변 여부", example = "true")
    private boolean hasAnswer;

    @Schema(description = "비밀글 여부", example = "false")
    private boolean secret;

    @Schema(description = "조회수", example = "87")
    private int viewCount;

    @Schema(description = "질문 등록일시", example = "2025-06-30T14:25:00")
    private LocalDateTime questionAt;
}
