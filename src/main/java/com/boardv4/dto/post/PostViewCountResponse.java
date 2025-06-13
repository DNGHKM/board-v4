package com.boardv4.dto.post;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PostViewCountResponse {
    private Integer viewCount;

    public static PostViewCountResponse from(Integer viewCount) {
        return PostViewCountResponse.builder()
                .viewCount(viewCount)
                .build();
    }
}
