package com.boardv4.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 공통 API 응답 DTO
 */
@Getter
@AllArgsConstructor
@Schema(description = "공통 API 응답 래퍼")
public class ApiResponseDTO<T> {

    @Schema(description = "요청 성공 여부", example = "true")
    private boolean success;

    @Schema(description = "응답 메시지", example = "요청이 정상적으로 처리되었습니다.")
    private String message;

    @Schema(description = "응답 데이터")
    private T data;

    public static <T> ApiResponseDTO<T> success(String message) {
        return new ApiResponseDTO<>(true, message, null);
    }

    public static <T> ApiResponseDTO<T> success(String message, T data) {
        return new ApiResponseDTO<>(true, message, data);
    }

    public static <T> ApiResponseDTO<T> failure(String message) {
        return new ApiResponseDTO<>(false, message, null);
    }

    public static <T> ApiResponseDTO<T> failure(String message, T data) {
        return new ApiResponseDTO<>(false, message, data);
    }
}
