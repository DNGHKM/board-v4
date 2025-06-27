package com.boardv4.dto.qna;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class QnaViewCountResponse {
    private Integer viewCount;

    public static QnaViewCountResponse from(Integer viewCount) {
        return QnaViewCountResponse.builder()
                .viewCount(viewCount)
                .build();
    }
}
