package com.boardv4.dto.post;

import com.boardv4.domain.Post;
import com.boardv4.domain.PostFile;
import com.boardv4.dto.postFile.FileResponse;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Builder
public class PostViewResponse {

    private Long id;
    private Long boardId;
    private String subject;
    private String content;
    private String writer;
    private String categoryName;
    private int viewCount;
    private LocalDateTime createAt;
    private boolean deleted;

    private List<FileResponse> files;

    public static PostViewResponse from(Post post, String categoryName, List<PostFile> files) {
        return PostViewResponse.builder()
                .id(post.getId())
                .boardId(post.getBoardId())
                .subject(post.getSubject())
                .content(post.getContent())
                .categoryName(categoryName)
                .viewCount(post.getViewCount())
                .createAt(post.getCreateAt())
                .deleted(post.isDeleted())
                .files(files.stream()
                        .map(f -> new FileResponse(f.getId(), f.getOriginalFilename(), f.getSavedFilename()))
                        .toList())
                .build();
    }
}
