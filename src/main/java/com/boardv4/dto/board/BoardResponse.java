package com.boardv4.dto.board;

import com.boardv4.enums.BoardType;
import com.boardv4.enums.FileType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BoardResponse {
    private Long boardId;
    private BoardType boardType;
    private String boardName;
    private boolean allowComment;
    private boolean allowPinned;
    private boolean writeAdminOnly;
    private Integer newDay;
    private FileType fileType;
    private Integer fileMaxCount;
    private Long fileMaxSize;
}
