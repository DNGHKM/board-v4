package com.boardv4.dto.board;

import com.boardv4.enums.BoardType;
import com.boardv4.enums.FileType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

/**
 * 게시판 정보 응답 DTO
 */
@Getter
@Builder
@Schema(description = "게시판 정보 응답 DTO")
public class BoardResponse {

    @Schema(description = "게시판 ID", example = "1")
    private Long boardId;

    @Schema(description = "게시판 타입 (예: NOTICE, GALLERY, FREE 등)", example = "FREE")
    private BoardType boardType;

    @Schema(description = "게시판 이름", example = "자유게시판")
    private String boardName;

    @Schema(description = "댓글 허용 여부", example = "true")
    private boolean allowComment;

    @Schema(description = "상단 고정 글 허용 여부", example = "false")
    private boolean allowPinned;

    @Schema(description = "관리자만 글쓰기 가능 여부", example = "false")
    private boolean writeAdminOnly;

    @Schema(description = "NEW 표시 기준 (일 수)", example = "3")
    private Integer newDay;

    @Schema(description = "첨부파일 타입 (예: IMAGE, FILE, NONE)", example = "IMAGE")
    private FileType fileType;

    @Schema(description = "최대 첨부파일 개수", example = "5")
    private Integer fileMaxCount;

    @Schema(description = "첨부파일 최대 크기 (byte 단위)", example = "5242880")
    private Long fileMaxSize;
}
