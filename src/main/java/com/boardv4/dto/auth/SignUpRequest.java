package com.boardv4.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import static com.boardv4.constant.ValidationConstant.*;

@Getter
@Builder
public class SignUpRequest {
    @NotBlank(message = MEMBER_USERNAME_NOT_BLANK)
    @Length(min = MEMBER_USERNAME_MIN_LENGTH, max = MEMBER_USERNAME_MAX_LENGTH, message = MEMBER_USERNAME_LENGTH)
    private String username;

    @NotBlank(message = MEMBER_PW_NOT_BLANK)
    @Length(min = MEMBER_PW_MIN_LENGTH, max = MEMBER_PW_MAX_LENGTH, message = MEMBER_PW_LENGTH)
    @Pattern(regexp = MEMBER_PW_REGEX, message = MEMBER_PW_PATTERN)
    private String password;

    @NotBlank(message = PW_CHK_NOT_BLANK)
    @Length(min = MEMBER_PW_MIN_LENGTH, max = MEMBER_PW_MAX_LENGTH, message = MEMBER_PW_CHK_LENGTH)
    @Pattern(regexp = MEMBER_PW_REGEX, message = MEMBER_PW_CHK_PATTERN)
    private String passwordCheck;

    @NotBlank(message = MEMBER_NAME_NOT_BLANK)
    @Length(min = MEMBER_NAME_MIN_LENGTH, max = MEMBER_NAME_MAX_LENGTH, message = MEMBER_NAME_LENGTH)
    private String name;
}
