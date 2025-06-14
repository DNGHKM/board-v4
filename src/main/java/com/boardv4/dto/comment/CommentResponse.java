package com.boardv4.dto.comment;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CommentResponse {
    private Long id;
    private String name;
    private String username;
    private String content;
    private LocalDateTime createAt;
}
