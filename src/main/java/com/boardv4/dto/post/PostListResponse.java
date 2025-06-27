package com.boardv4.dto.post;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@Schema(description = "게시글 목록 응답 DTO")
public class PostListResponse {

    @Schema(description = "현재 페이지 번호 (1부터 시작)", example = "1")
    private int page;

    @Schema(description = "페이지 당 게시글 수", example = "10")
    private int size;

    @Schema(description = "전체 페이지 수", example = "5")
    private int totalPages;

    @Schema(description = "전체 게시글 수", example = "42")
    private long postCount;

    @Schema(description = "게시글 요약 목록")
    private List<PostSummaryResponse> posts;

    public static PostListResponse from(PostSearchRequest dto,
                                        int totalPages, long totalCount,
                                        List<PostSummaryResponse> posts) {
        return PostListResponse.builder()
                .page(dto.getPage())
                .size(dto.getSize())
                .totalPages(totalPages)
                .postCount(totalCount)
                .posts(posts)
                .build();
    }
}
