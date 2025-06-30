package com.boardv4.controller;

import com.boardv4.controller.doc.BoardApiDoc;
import com.boardv4.dto.ApiResponseDTO;
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

@RestController
@RequestMapping("/api/v1/boards")
@AllArgsConstructor
@Slf4j
public class BoardController implements BoardApiDoc {

    private final BoardService boardService;

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<BoardResponse>>> getAllBoardList() {
        List<BoardResponse> response = boardService.getAllBoardList();
        return ResponseEntity.ok(ApiResponseDTO.success("게시판 목록을 조회하였습니다.", response));
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<ApiResponseDTO<BoardResponse>> getBoard(
            @Parameter(description = "게시판 ID", example = "1") @PathVariable Long boardId) {

        BoardResponse response = boardService.getBoardInfoById(boardId);
        return ResponseEntity.ok(ApiResponseDTO.success("게시판을 조회하였습니다.", response));
    }
}
