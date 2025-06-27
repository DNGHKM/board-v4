package com.boardv4.dto.qna;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class QnaSummaryResponse {
    private Long id;
    private String subject;
    private String writerName;
    private String writerUsername;
    private boolean hasAnswer;
    private boolean secret;
    private int viewCount;
    private LocalDateTime questionAt;
}
