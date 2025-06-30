package com.boardv4.dto.post;

import com.boardv4.domain.Category;
import com.boardv4.domain.Post;
import com.boardv4.domain.PostFile;
import com.boardv4.dto.postFile.FileResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 게시글 상세 조회 응답 DTO
 */
@Getter
@Builder
@Schema(description = "게시글 상세 조회 응답 DTO")
public class PostViewResponse {

    @Schema(description = "게시글 ID", example = "1001")
    private Long id;

    @Schema(description = "게시판 ID", example = "10")
    private Long boardId;

    @Schema(description = "게시글 제목", example = "Spring 게시판 구현 방법")
    private String subject;

    @Schema(description = "게시글 내용", example = "게시판 구현에 대한 상세 설명입니다.")
    private String content;

    @Schema(description = "작성자 ID", example = "dongha123")
    private String username;

    @Schema(description = "작성자 이름", example = "김동하")
    private String name;

    @Schema(description = "카테고리 ID", example = "3")
    private Long categoryId;

    @Schema(description = "카테고리 이름", example = "공지사항")
    private String categoryName;

    @Schema(description = "조회수", example = "250")
    private int viewCount;

    @Schema(description = "상단 고정 여부", example = "false")
    private boolean pinned;

    @Schema(description = "작성 일시", example = "2025-06-30T14:15:00")
    private LocalDateTime createAt;

    @Schema(description = "삭제 여부", example = "false")
    private boolean deleted;

    @Schema(description = "첨부 파일 목록")
    private List<FileResponse> files;

    public static PostViewResponse from(Post post, String username, String name, Category category, List<PostFile> files) {
        return PostViewResponse.builder()
                .id(post.getId())
                .boardId(post.getBoardId())
                .subject(post.getSubject())
                .content(post.getContent())
                .username(username)
                .name(name)
                .categoryId(category.getId())
                .categoryName(category.getName())
                .viewCount(post.getViewCount())
                .pinned(post.isPinned())
                .createAt(post.getCreateAt())
                .deleted(post.isDeleted())
                .files(files.stream()
                        .map(f -> new FileResponse(f.getId(), f.getOriginalFilename(), f.getSavedFilename()))
                        .toList())
                .build();
    }
}
