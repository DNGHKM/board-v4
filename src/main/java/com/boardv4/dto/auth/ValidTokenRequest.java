package com.boardv4.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

/**
 * 토큰 유효성 검증 요청 DTO
 */
@Getter
@Builder
@Schema(description = "토큰 유효성 검증 요청 DTO")
public class ValidTokenRequest {

    @Schema(
            description = "검증할 JWT 액세스 토큰",
            example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
    )
    private String token;
}
