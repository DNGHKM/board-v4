package com.boardv4.controller.doc;

import com.boardv4.domain.Category;
import com.boardv4.dto.ApiResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Tag(name = "카테고리 API", description = "게시판별 카테고리 조회 API")
public interface CategoryApiDoc {

    @Operation(summary = "카테고리 목록 조회", description = "특정 게시판의 카테고리 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "카테고리 조회 성공")
    })
    ResponseEntity<ApiResponseDTO<List<Category>>> getAllCategories(
            @Parameter(description = "게시판 ID", example = "1") @PathVariable Long boardId
    );
}
