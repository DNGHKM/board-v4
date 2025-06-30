package com.boardv4.dto.postFile;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 게시글 첨부파일 응답 DTO
 */
@Getter
@AllArgsConstructor
@Schema(description = "게시글 첨부파일 응답 DTO")
public class FileResponse {

    @Schema(description = "파일 ID", example = "15")
    private Long id;

    @Schema(description = "사용자가 업로드한 원본 파일명", example = "dog.jpg")
    private String originalFilename;

    @Schema(description = "서버에 저장된 파일명", example = "a8c1f390-d3a2-45ef-b820-ccf4f88e5fae.pdf")
    private String savedFilename;
}
