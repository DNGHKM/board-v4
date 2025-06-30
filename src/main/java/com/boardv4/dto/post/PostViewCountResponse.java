package com.boardv4.dto.post;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

/**
 * 게시글 조회수 응답 DTO
 */
@Builder
@Getter
@Schema(description = "게시글 조회수 응답 DTO")
public class PostViewCountResponse {

    @Schema(description = "게시글 조회수", example = "123")
    private Integer viewCount;

    public static PostViewCountResponse from(Integer viewCount) {
        return PostViewCountResponse.builder()
                .viewCount(viewCount)
                .build();
    }
}
