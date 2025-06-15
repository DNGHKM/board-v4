package com.boardv4.dto.post;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class PostSummaryResponse {
    private Long id;
    private String categoryName;
    private String subject;
    private String writerName;
    private String thumbnailFilename;
    private Integer commentCount;
    private Integer fileCount;
    private int viewCount;
    private LocalDateTime createAt;
}
