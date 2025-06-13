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
public class PostModifyRequest {
    @NotNull(message = CATEGORY_NOT_BLANK)
    private Long categoryId;

    @NotBlank(message = SUBJECT_NOT_BLANK)
    @Length(min = SUBJECT_MIN_LENGTH, max = SUBJECT_MAX_LENGTH, message = SUBJECT_LENGTH)
    private String subject;

    @NotBlank(message = CONTENT_NOT_BLANK)
    @Length(min = CONTENT_MIN_LENGTH, max = CONTENT_MAX_LENGTH, message = CONTENT_LENGTH)
    private String content;

    private boolean pinned;

    //보존되는 저장파일명 목록(uuid.확장자)
    private List<String> preserveFilenames;

    //새로 업로드 된 파일 목록
    private List<MultipartFile> files;

}
