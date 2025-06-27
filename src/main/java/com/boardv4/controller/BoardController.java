package com.boardv4.controller;

import com.boardv4.dto.board.BoardResponse;
import com.boardv4.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "게시판 API", description = "게시판 조회 관련 API")
@RestController
@RequestMapping("/api/v1/boards")
@AllArgsConstructor
@Slf4j
public class BoardController {

    private final BoardService boardService;

    @Operation(summary = "전체 게시판 목록 조회", description = "모든 게시판의 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시판 목록 조회 성공")
    })
    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<BoardResponse>>> getAllBoardList() {
        List<BoardResponse> response = boardService.getAllBoardList();
        return ResponseEntity.ok(ApiResponseDTO.success("게시판 목록을 조회하였습니다.", response));
    }

    @Operation(summary = "단일 게시판 조회", description = "게시판 ID를 통해 게시판 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시판 조회 성공"),
            @ApiResponse(responseCode = "404", description = "게시판이 존재하지 않음")
    })
    @GetMapping("/{boardId}")
    public ResponseEntity<ApiResponseDTO<BoardResponse>> getBoard(
            @Parameter(description = "게시판 ID", example = "1") @PathVariable Long boardId) {

        BoardResponse response = boardService.getBoardInfoById(boardId);
        return ResponseEntity.ok(ApiResponseDTO.success("게시판을 조회하였습니다.", response));
    }
}
