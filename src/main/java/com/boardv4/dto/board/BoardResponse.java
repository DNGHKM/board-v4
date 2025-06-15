package com.boardv4.dto.board;

import com.boardv4.enums.BoardType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BoardResponse {
    private Long boardId;
    private BoardType boardType;
    private String boardName;
}
