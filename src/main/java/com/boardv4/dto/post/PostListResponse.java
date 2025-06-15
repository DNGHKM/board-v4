package com.boardv4.dto.post;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class PostListResponse {
    private String boardEngName;
    private int page;
    private int size;
    private int totalPages;
    private long postCount;
    private List<PostSummaryResponse> posts;

    public static PostListResponse from(PostSearchRequest dto,
                                        String boardEngName,
                                        int totalPages, long totalCount,
                                        List<PostSummaryResponse> posts) {
        return PostListResponse.builder()
                .boardEngName(boardEngName)
                .page(dto.getPage())
                .size(dto.getSize())
                .totalPages(totalPages)
                .postCount(totalCount)
                .posts(posts)
                .build();
    }
}