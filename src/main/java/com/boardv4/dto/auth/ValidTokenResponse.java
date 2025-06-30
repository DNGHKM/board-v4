package com.boardv4.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 토큰 유효성 검증 응답 DTO
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@Schema(description = "토큰 유효성 검증 응답 DTO")
public class ValidTokenResponse {

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
