package com.boardv4.dto.qna;

import com.boardv4.enums.PostSortBy;
import com.boardv4.enums.SortDirection;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

import static com.boardv4.constant.ValidationConstant.*;

/**
 * Q&A 검색 조건 요청 DTO
 */
@Getter
@Setter
@Schema(description = "Q&A 검색 요청 DTO")
public class QnaSearchRequest {

    @Schema(description = "페이지 번호 (1부터 시작)", example = "1", defaultValue = "1", minimum = "1")
    @Min(1)
    private int page = 1;

    @Schema(description = "페이지당 항목 수", example = "10", defaultValue = "10", minimum = "1")
    @Min(1)
    private int size = 10;

    @Schema(description = "검색 시작일 (yyyy-MM-dd)",
            example = "2025-01-01",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = START_DATE_NOT_NULL)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;

    @Schema(description = "검색 종료일 (yyyy-MM-dd)",
            example = "2025-06-30",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = END_DATE_NOT_NULL)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;

    @Schema(description = "검색 키워드 (제목/내용/작성자)", example = "JWT 인증")
    private String keyword;

    @Schema(description = "정렬 기준",
            example = "CREATE_DATE",
            requiredMode = Schema.RequiredMode.REQUIRED,
            allowableValues = {"CREATE_DATE", "VIEW_COUNT"})
    @NotNull(message = SORT_BY_NOT_NULL)
    private PostSortBy sortBy = PostSortBy.CREATE_DATE;

    @Schema(description = "정렬 방향",
            example = "DESC",
            requiredMode = Schema.RequiredMode.REQUIRED,
            allowableValues = {"ASC", "DESC"})
    @NotNull(message = SORT_DIRECTION_NOT_NULL)
    private SortDirection sortDirection = SortDirection.DESC;

    @Schema(description = "내가 작성한 글만 보기 여부",
            example = "true",
            defaultValue = "false")
    private boolean myQna = false;

    public void adjustPage(int totalPages) {
        this.page = Math.max(1, Math.min(this.page, totalPages));
    }
}
