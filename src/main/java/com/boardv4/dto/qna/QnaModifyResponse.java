package com.boardv4.dto.qna;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class QnaModifyResponse {
    private Long qnaId;

    public static QnaModifyResponse from(Long qnaId) {
        return QnaModifyResponse.builder()
                .qnaId(qnaId)
                .build();
    }
}
