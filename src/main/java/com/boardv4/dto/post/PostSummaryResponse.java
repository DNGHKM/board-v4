package com.boardv4.dto.post;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 게시글 요약 응답 DTO (게시글 목록에 사용)
 */
@Builder
@Getter
@Schema(description = "게시글 요약 응답 DTO")
public class PostSummaryResponse {

    @Schema(description = "게시글 ID", example = "101")
    private Long id;

    @Schema(description = "카테고리 이름", example = "공지사항")
    private String categoryName;

    @Schema(description = "게시글 제목", example = "Spring Boot 게시판 구현 완료")
    private String subject;

    @Schema(description = "작성자 이름", example = "김동하")
    private String writerName;

    @Schema(description = "썸네일 파일명", example = "f3a9bc7f-12d3-4f7e-b123-cafe4ea124cd.png")
    private String thumbnailFilename;

    @Schema(description = "댓글 수", example = "5")
    private Integer commentCount;

    @Schema(description = "첨부파일 수", example = "2")
    private Integer fileCount;

    @Schema(description = "조회수", example = "123")
    private int viewCount;

    @Schema(description = "작성일시", example = "2025-06-30T15:20:30")
    private LocalDateTime createAt;
}
