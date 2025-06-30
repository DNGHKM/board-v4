package com.boardv4.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import static com.boardv4.constant.ValidationConstant.*;

/**
 * 로그인 요청 DTO
 */
@Getter
@Builder
@Schema(description = "로그인 요청 DTO")
public class LoginRequest {

    /**
     * 사용자 ID
     */
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

    /**
     * 사용자 비밀번호
     */
    @Schema(
            description = "사용자 비밀번호 (영문/숫자/특수문자 조합)",
            example = "password123!",
            minLength = MEMBER_PW_MIN_LENGTH,
            maxLength = MEMBER_PW_MAX_LENGTH,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = MEMBER_PW_NOT_BLANK)
    @Length(min = MEMBER_PW_MIN_LENGTH, max = MEMBER_PW_MAX_LENGTH, message = MEMBER_PW_LENGTH)
    @Pattern(regexp = MEMBER_PW_REGEX, message = MEMBER_PW_PATTERN)
    private String password;
}
