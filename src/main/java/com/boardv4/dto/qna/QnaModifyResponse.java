package com.boardv4.dto.qna;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

/**
 * Q&A 수정 응답 DTO
 */
@Builder
@Getter
@Schema(description = "Q&A 수정 응답 DTO")
public class QnaModifyResponse {

    @Schema(description = "수정된 Q&A ID", example = "42")
    private Long qnaId;

    public static QnaModifyResponse from(Long qnaId) {
        return QnaModifyResponse.builder()
                .qnaId(qnaId)
                .build();
    }
}
