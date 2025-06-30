package com.boardv4.dto.qna;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Q&A 목록 응답 DTO
 */
@Getter
@Setter
@Builder
@Schema(description = "Q&A 목록 응답 DTO")
public class QnaListResponse {

    @Schema(description = "현재 페이지 번호 (0부터 시작)", example = "0")
    private int page;

    @Schema(description = "페이지당 항목 수", example = "10")
    private int size;

    @Schema(description = "전체 페이지 수", example = "5")
    private int totalPages;

    @Schema(description = "전체 게시글 수", example = "42")
    private long postCount;

    @Schema(description = "Q&A 요약 목록")
    private List<QnaSummaryResponse> qnaList;

    public static QnaListResponse from(QnaSearchRequest dto,
                                       int totalPages,
                                       long totalCount,
                                       List<QnaSummaryResponse> qnaSummaryList) {
        return QnaListResponse.builder()
                .page(dto.getPage())
                .size(dto.getSize())
                .totalPages(totalPages)
                .postCount(totalCount)
                .qnaList(qnaSummaryList)
                .build();
    }
}
