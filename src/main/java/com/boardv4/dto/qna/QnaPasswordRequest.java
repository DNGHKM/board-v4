package com.boardv4.dto.qna;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QnaPasswordRequest {
    private String password;
}
