package com.boardv4.dto.qna;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class QnaListResponse {
    private int page;
    private int size;
    private int totalPages;
    private long postCount;
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