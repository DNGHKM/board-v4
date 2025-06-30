package com.boardv4.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 회원가입 응답 DTO
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@Schema(description = "회원가입 응답 DTO")
public class SignUpResponse {

    @Schema(
            description = "JWT 액세스 토큰",
            example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
    )
    private String accessToken;

    @Schema(
            description = "사용자 ID",
            example = "dongha123"
    )
    private String username;

    @Schema(
            description = "사용자 이름",
            example = "김동하"
    )
    private String name;
}
