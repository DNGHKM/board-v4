package com.boardv4.exception;

import com.boardv4.controller.ApiResponseDTO;
import com.boardv4.exception.base.FieldValidationException;
import com.boardv4.exception.base.ForbiddenException;
import com.boardv4.exception.base.NotFoundException;
import com.boardv4.exception.member.PasswordNotMatchException;
import com.boardv4.exception.post.DeletedPostException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponseDTO<?>> handleNotFound(NotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponseDTO.failure(ex.getMessage()));
    }

    @ExceptionHandler(PasswordNotMatchException.class)
    public ResponseEntity<ApiResponseDTO<?>> handlePasswordNotMatch(PasswordNotMatchException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponseDTO.failure(ex.getMessage()));
    }

    @ExceptionHandler(DeletedPostException.class)
    public ResponseEntity<ApiResponseDTO<?>> handleDeletedPost(DeletedPostException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponseDTO.failure(e.getMessage()));
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ApiResponseDTO<?>> handleForbidden(ForbiddenException e) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(ApiResponseDTO.failure(e.getMessage()));
    }

    /**
     * 서버사이드 DTO 검증 시 반환하는 값을 간단히 바꾸는 메서드
     *
     * @param ex : MethodArgumentNotValidException
     * @return : 필드와 에러응답 메시지의 Map List를 포함한 ApiResponse
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDTO<?>> handleValidationException(MethodArgumentNotValidException ex) {
        List<Map<String, String>> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> Map.of(
                        "field", error.getField(),
                        "message", error.getDefaultMessage()
                ))
                .toList();

        ApiResponseDTO<?> response = ApiResponseDTO.failure("유효성 검사 실패", errors);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(FieldValidationException.class)
    public ResponseEntity<ApiResponseDTO<?>> handleFieldValidation(FieldValidationException ex) {
        Map<String, String> error = Map.of(
                "field", ex.getField(),
                "message", ex.getMessage()
        );

        ApiResponseDTO<?> response = ApiResponseDTO.failure("유효성 검사 실패", List.of(error));
        return ResponseEntity.badRequest().body(response);
    }

}