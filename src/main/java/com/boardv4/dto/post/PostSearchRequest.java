package com.boardv4.dto.post;

import com.boardv4.enums.PostSortBy;
import com.boardv4.enums.SortDirection;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

import static com.boardv4.constant.ValidationConstant.*;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;


@Getter
@Setter
@Schema(description = "게시글 검색 요청 DTO")
public class PostSearchRequest {

    @Schema(description = "게시판 ID", example = "1", requiredMode = REQUIRED)
    @NotNull
    private Long boardId;

    @Schema(description = "페이지 번호 (1부터 시작)", example = "1", defaultValue = "1")
    @Min(1)
    private int page = 1;

    @Schema(description = "페이지당 게시글 수", example = "10", defaultValue = "10")
    @Min(1)
    private int size = 10;

    @Schema(description = "카테고리 ID (선택)", example = "5")
    private Long categoryId;

    @Schema(description = "검색 시작일 (yyyy-MM-dd)", example = "2024-01-01", requiredMode = REQUIRED)
    @NotNull(message = START_DATE_NOT_NULL)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;

    @Schema(description = "검색 종료일 (yyyy-MM-dd)", example = "2024-12-31", requiredMode = REQUIRED)
    @NotNull(message = END_DATE_NOT_NULL)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;

    @Schema(description = "검색 키워드 (제목 또는 내용 포함)", example = "공지")
    private String keyword;

    @Schema(description = "정렬 기준", example = "CREATE_DATE", requiredMode = REQUIRED, implementation = PostSortBy.class)
    @NotNull(message = SORT_BY_NOT_NULL)
    private PostSortBy sortBy = PostSortBy.CREATE_DATE;

    @Schema(description = "정렬 방향", example = "DESC", requiredMode = REQUIRED, implementation = SortDirection.class)
    @NotNull(message = SORT_DIRECTION_NOT_NULL)
    private SortDirection sortDirection = SortDirection.DESC;

    public void adjustPage(int totalPages) {
        this.page = Math.max(1, Math.min(this.page, totalPages));
    }
}
