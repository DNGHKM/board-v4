package com.boardv4.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import static com.boardv4.constant.ValidationConstant.*;

/**
 * 회원가입 요청 DTO
 */
@Getter
@Builder
@Schema(description = "회원가입 요청 DTO")
public class SignUpRequest {

    @Schema(
            description = "사용자 ID",
            example = "dongha123",
            minLength = MEMBER_USERNAME_MIN_LENGTH,
            maxLength = MEMBER_USERNAME_MAX_LENGTH,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = MEMBER_USERNAME_NOT_BLANK)
    @Length(min = MEMBER_USERNAME_MIN_LENGTH, max = MEMBER_USERNAME_MAX_LENGTH, message = MEMBER_USERNAME_LENGTH)
    private String username;

    @Schema(
            description = "비밀번호 (영문/숫자/특수문자 포함)",
            example = "password123_-",
            minLength = MEMBER_PW_MIN_LENGTH,
            maxLength = MEMBER_PW_MAX_LENGTH,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = MEMBER_PW_NOT_BLANK)
    @Length(min = MEMBER_PW_MIN_LENGTH, max = MEMBER_PW_MAX_LENGTH, message = MEMBER_PW_LENGTH)
    @Pattern(regexp = MEMBER_PW_REGEX, message = MEMBER_PW_PATTERN)
    private String password;

    @Schema(
            description = "비밀번호 확인",
            example = "password123_-",
            minLength = MEMBER_PW_MIN_LENGTH,
            maxLength = MEMBER_PW_MAX_LENGTH,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = PW_CHK_NOT_BLANK)
    @Length(min = MEMBER_PW_MIN_LENGTH, max = MEMBER_PW_MAX_LENGTH, message = MEMBER_PW_CHK_LENGTH)
    @Pattern(regexp = MEMBER_PW_REGEX, message = MEMBER_PW_CHK_PATTERN)
    private String passwordCheck;

    @Schema(
            description = "사용자 이름",
            example = "김동하",
            minLength = MEMBER_NAME_MIN_LENGTH,
            maxLength = MEMBER_NAME_MAX_LENGTH,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = MEMBER_NAME_NOT_BLANK)
    @Length(min = MEMBER_NAME_MIN_LENGTH, max = MEMBER_NAME_MAX_LENGTH, message = MEMBER_NAME_LENGTH)
    private String name;
}
