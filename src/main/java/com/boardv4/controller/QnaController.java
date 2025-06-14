package com.boardv4.controller;

import com.boardv4.annotation.LoginRequired;
import com.boardv4.annotation.LoginUser;
import com.boardv4.dto.qna.*;
import com.boardv4.service.QnaService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/qna")
@AllArgsConstructor
@Slf4j
public class QnaController {
    private final QnaService qnaService;

    @GetMapping("/{qnaId}")
    public ResponseEntity<ApiResponse<QnaViewResponse>> getQnaView(@PathVariable Long qnaId,
                                                                   @LoginUser String username) {
        QnaViewResponse response = qnaService.getQnaViewById(qnaId, username);
        return ResponseEntity.ok(ApiResponse.success("QnA를 조회하였습니다.", response));
    }

    @LoginRequired
    @PostMapping
    public ResponseEntity<ApiResponse<QnaWriteResponse>> writeQna(@RequestBody @Valid QnaWriteRequest writeDTO,
                                                                  @LoginUser String username) {
        QnaWriteResponse response = qnaService.write(writeDTO, username);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("질문 게시글을 등록하였습니다.", response));
    }

    @LoginRequired
    @PutMapping("/{qnaId}")
    public ResponseEntity<ApiResponse<Void>> modifyQna(@PathVariable Long qnaId,
                                                       @RequestBody @Valid QnaModifyRequest modifyDTO,
                                                       @LoginUser String username) {
        qnaService.modify(qnaId, modifyDTO, username);
        return ResponseEntity.ok(ApiResponse.success("질문 게시글을 수정하였습니다."));
    }

    @LoginRequired
    @DeleteMapping("/{qnaId}")
    public ResponseEntity<ApiResponse<Void>> deleteQna(@PathVariable Long qnaId,
                                                       @LoginUser String username) {
        qnaService.delete(qnaId, username);
        return ResponseEntity.ok(ApiResponse.success("질문 게시글을 삭제하였습니다."));
    }
}
