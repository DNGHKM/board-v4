package com.boardv4.controller;

import com.boardv4.dto.board.BoardResponse;
import com.boardv4.service.BoardService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/boards")
@AllArgsConstructor
@Slf4j
public class BoardController {
    private final BoardService boardService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<BoardResponse>>> getPostList() {
        List<BoardResponse> response = boardService.getBoardList();
        return ResponseEntity.ok(ApiResponse.success("게시판 목록을 조회하였습니다.", response));
    }
}
