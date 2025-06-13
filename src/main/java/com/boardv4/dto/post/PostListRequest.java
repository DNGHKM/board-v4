package com.boardv4.dto.post;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class PostListRequest {
    @NotBlank
    private String boardEngName;

    @Min(1)
    private int page;

    @Min(1)
    private int size;

    private String categoryName;

    @NotNull(message = "{NotNull.startDate}")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;

    @NotNull(message = "{NotNull.endDate}")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;

    private String keyword;

    public void adjustPage(int totalPages) {
        this.page = Math.max(1, Math.min(this.page, totalPages));
    }
}
