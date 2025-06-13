package com.boardv4.domain;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private Long id;
    private Long memberId;
    private Long postId;
    private String content;
    private LocalDateTime createAt;
}
