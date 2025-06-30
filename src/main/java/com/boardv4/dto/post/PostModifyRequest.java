package com.boardv4.dto.post;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.boardv4.constant.ValidationConstant.*;

/**
 * 게시글 수정 요청 DTO
 */
@Getter
@Builder
@Schema(description = "게시글 수정 요청 DTO")
public class PostModifyRequest {

    @Schema(
            description = "카테고리 ID",
            example = "3",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = CATEGORY_NOT_BLANK)
    private Long categoryId;

    @Schema(
            description = "게시글 제목",
            example = "Spring Boot 게시판 수정 기능 구현",
            minLength = SUBJECT_MIN_LENGTH,
            maxLength = SUBJECT_MAX_LENGTH,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = SUBJECT_NOT_BLANK)
    @Length(min = SUBJECT_MIN_LENGTH, max = SUBJECT_MAX_LENGTH, message = SUBJECT_LENGTH)
    private String subject;

    @Schema(
            description = "게시글 내용",
            example = "게시글 수정 시 제목, 내용, 첨부파일을 수정할 수 있도록 구현했습니다.",
            minLength = CONTENT_MIN_LENGTH,
            maxLength = CONTENT_MAX_LENGTH,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = CONTENT_NOT_BLANK)
    @Length(min = CONTENT_MIN_LENGTH, max = CONTENT_MAX_LENGTH, message = CONTENT_LENGTH)
    private String content;

    @Schema(
            description = "보존할 기존 파일명 목록 (uuid.확장자 형식)",
            example = "[\"a8b3f9de-3f5b-4e6c-924d-c6d46c4fc85a.png\"]"
    )
    private List<String> preserveFilenames;

    @Schema(
            description = "새로 업로드된 파일 목록 (multipart/form-data 형식)",
            type = "array",
            format = "binary"
    )
    private List<MultipartFile> files;
}
