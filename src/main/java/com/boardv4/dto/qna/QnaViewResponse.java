package com.boardv4.dto.qna;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Q&A 상세 조회 응답 DTO
 */
@Getter
@Builder
@Schema(description = "Q&A 상세 조회 응답 DTO")
public class QnaViewResponse {

    @Schema(description = "Q&A ID", example = "101")
    private Long id;

    @Schema(description = "작성자 이름", example = "김동하")
    private String writerName;

    @Schema(description = "작성자 아이디", example = "dongha123")
    private String writerUsername;

    @Schema(description = "질문 제목", example = "JWT 인증 방식에 대해 궁금합니다.")
    private String subject;

    @Schema(description = "질문 내용", example = "Spring Security에서 JWT 필터를 어떻게 구성해야 할까요?")
    private String content;

    @Schema(description = "질문 등록일시", example = "2025-06-30T14:25:00")
    private LocalDateTime questionAt;

    @Schema(description = "답변자 이름", example = "관리자")
    private String answererName;

    @Schema(description = "답변 내용", example = "JWT는 OncePerRequestFilter를 사용해 필터에서 인증 처리를 구현할 수 있습니다.")
    private String answer;

    @Schema(description = "답변 등록일시", example = "2025-07-01T09:00:00")
    private LocalDateTime answerAt;

    @Schema(description = "조회수", example = "87")
    private Integer viewCount;

    @Schema(description = "비밀글 여부", example = "false")
    private boolean secret;
}
