package com.boardv4.controller.doc;

import com.boardv4.dto.ApiResponseDTO;
import com.boardv4.dto.board.BoardResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface BoardApiDoc {

    @Operation(summary = "전체 게시판 목록 조회", description = "사용 가능한 모든 게시판 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시판 목록 조회 성공"),
    })
    ResponseEntity<ApiResponseDTO<List<BoardResponse>>> getAllBoardList();

    @Operation(summary = "단일 게시판 조회", description = "게시판 ID를 이용해 게시판 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시판 조회 성공"),
    })
    ResponseEntity<ApiResponseDTO<BoardResponse>> getBoard(
            @Parameter(description = "게시판 ID", example = "1") @PathVariable Long boardId
    );
}
