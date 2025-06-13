package com.boardv4.dto.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.boardv4.constant.ValidationConstant.*;

@Getter
@Builder
public class PostWriteRequest {

    @NotNull(message = BOARD_NOT_NULL)
    private Long boardId;

    @NotNull(message = CATEGORY_NOT_BLANK)
    private Long categoryId;

    @NotBlank(message = SUBJECT_NOT_BLANK)
    @Length(min = SUBJECT_MIN_LENGTH, max = SUBJECT_MAX_LENGTH, message = SUBJECT_LENGTH)
    private String subject;

    @NotBlank(message = CONTENT_NOT_BLANK)
    @Length(min = CONTENT_MIN_LENGTH, max = CONTENT_MAX_LENGTH, message = CONTENT_LENGTH)
    private String content;

    private boolean pinned;

    private List<MultipartFile> files;
}
