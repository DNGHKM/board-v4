package com.boardv4.dto.qna;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

/**
 * Q&A 조회수 응답 DTO
 */
@Builder
@Getter
@Schema(description = "Q&A 조회수 응답 DTO")
public class QnaViewCountResponse {

    @Schema(description = "Q&A 조회수", example = "42")
    private Integer viewCount;

    public static QnaViewCountResponse from(Integer viewCount) {
        return QnaViewCountResponse.builder()
                .viewCount(viewCount)
                .build();
    }
}
