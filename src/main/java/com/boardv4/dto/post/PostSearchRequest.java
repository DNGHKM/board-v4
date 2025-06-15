package com.boardv4.dto.post;

import com.boardv4.enums.PostSortBy;
import com.boardv4.enums.SortDirection;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

import static com.boardv4.constant.ValidationConstant.*;

@Getter
@Setter
public class PostSearchRequest {

    @NotNull
    private Long boardId;

    @Min(1)
    private int page = 1;

    @Min(1)
    private int size = 10;

    private Long categoryId;

    @NotNull(message = START_DATE_NOT_NULL)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;

    @NotNull(message = END_DATE_NOT_NULL)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;

    private String keyword;

    @NotNull(message = SORT_BY_NOT_NULL)
    private PostSortBy sortBy = PostSortBy.CREATE_DATE;

    @NotNull(message = SORT_DIRECTION_NOT_NULL)
    private SortDirection sortDirection = SortDirection.DESC;

    public void adjustPage(int totalPages) {
        this.page = Math.max(1, Math.min(this.page, totalPages));
    }
}
