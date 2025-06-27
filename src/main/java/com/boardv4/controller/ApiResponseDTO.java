package com.boardv4.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponseDTO<T> {

    private boolean success;

    private String message;

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
