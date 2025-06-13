package com.boardv4.dto.comment;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponse {
    private Long id;
    private String name;
    private String username;
    private String content;
    private LocalDateTime createAt;
}
