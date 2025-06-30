package com.boardv4.dto.qna;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import static com.boardv4.constant.ValidationConstant.*;

/**
 * Q&A 수정 요청 DTO
 */
@Getter
@Builder
@Schema(description = "Q&A 수정 요청 DTO")
public class QnaModifyRequest {

    @Schema(
            description = "질문 제목",
            example = "Spring Security에서 JWT 인증 구현 방법",
            minLength = SUBJECT_MIN_LENGTH,
            maxLength = SUBJECT_MAX_LENGTH,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = SUBJECT_NOT_BLANK)
    @Length(min = SUBJECT_MIN_LENGTH, max = SUBJECT_MAX_LENGTH, message = SUBJECT_LENGTH)
    private String subject;

    @Schema(
            description = "질문 내용",
            example = "JWT 인증 시 토큰 검증을 필터에서 처리하고 싶습니다. 어떤 구조가 적절할까요?",
            minLength = CONTENT_MIN_LENGTH,
            maxLength = CONTENT_MAX_LENGTH,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = CONTENT_NOT_BLANK)
    @Length(min = CONTENT_MIN_LENGTH, max = CONTENT_MAX_LENGTH, message = CONTENT_LENGTH)
    private String content;

    @Schema(
            description = "비밀글 여부 (true = 비공개, false = 공개)",
            example = "true"
    )
    private boolean secret;
}
