package com.boardv4.dto.qna;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

/**
 * Q&A 작성 응답 DTO
 */
@Builder
@Getter
@Schema(description = "Q&A 작성 응답 DTO")
public class QnaWriteResponse {

    @Schema(description = "작성된 Q&A ID", example = "123")
    private Long qnaId;
}
