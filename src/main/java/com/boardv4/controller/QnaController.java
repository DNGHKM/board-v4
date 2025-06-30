package com.boardv4.controller;

import com.boardv4.annotation.LoginUser;
import com.boardv4.controller.doc.QnaApiDoc;
import com.boardv4.dto.ApiResponseDTO;
import com.boardv4.dto.post.PostViewCountResponse;
import com.boardv4.dto.qna.*;
import com.boardv4.service.QnaService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "QnA API", description = "QnA 등록, 조회, 수정, 삭제 등의 기능을 제공합니다.")
@RestController
@RequestMapping("/api/v1/qna")
@AllArgsConstructor
@Slf4j
public class QnaController implements QnaApiDoc {
    private final QnaService qnaService;

    @GetMapping
    public ResponseEntity<ApiResponseDTO<QnaListResponse>> getQnaList(@ModelAttribute @Valid QnaSearchRequest request,
                                                                      @LoginUser String username) {
        QnaListResponse response = qnaService.getQnaList(request, username);
        return ResponseEntity.ok(ApiResponseDTO.success("QnA 목록을 조회하였습니다.", response));
    }

    @GetMapping("/new")
    public ResponseEntity<ApiResponseDTO<List<QnaSummaryResponse>>> getNewPostList(@RequestParam Integer limit) {
        List<QnaSummaryResponse> response = qnaService.getLatestQnaList(limit);
        return ResponseEntity.ok(ApiResponseDTO.success("최근 Qna 목록을 조회하였습니다.", response));
    }

    @GetMapping("/{qnaId}")
    public ResponseEntity<ApiResponseDTO<QnaViewResponse>> getQnaView(@PathVariable Long qnaId,
                                                                      @Parameter(hidden = true) @LoginUser String username) {
        QnaViewResponse response = qnaService.getQnaViewById(qnaId, username);
        return ResponseEntity.ok(ApiResponseDTO.success("QnA를 조회하였습니다.", response));
    }

    @PostMapping
    public ResponseEntity<ApiResponseDTO<QnaWriteResponse>> writeQna(@ModelAttribute @Valid QnaWriteRequest writeDTO,
                                                                     @Parameter(hidden = true) @LoginUser String username) {
        QnaWriteResponse response = qnaService.write(writeDTO, username);
        return ResponseEntity.ok(ApiResponseDTO.success("질문 게시글을 등록하였습니다.", response));
    }

    @PutMapping("/{qnaId}")
    public ResponseEntity<ApiResponseDTO<QnaModifyResponse>> modifyQna(@PathVariable Long qnaId,
                                                                       @ModelAttribute @Valid QnaModifyRequest modifyDTO,
                                                                       @Parameter(hidden = true) @LoginUser String username) {
        QnaModifyResponse response = qnaService.modify(qnaId, modifyDTO, username);
        return ResponseEntity.ok(ApiResponseDTO.success("질문 게시글을 수정하였습니다.", response));
    }

    @DeleteMapping("/{qnaId}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteQna(@PathVariable Long qnaId,
                                                          @Parameter(hidden = true) @LoginUser String username) {
        qnaService.delete(qnaId, username);
        return ResponseEntity.ok(ApiResponseDTO.success("질문 게시글을 삭제하였습니다."));
    }

    @PostMapping("/{qnaId}/viewCount")
    public ResponseEntity<ApiResponseDTO<PostViewCountResponse>> increaseViewCount(@PathVariable Long qnaId) {
        Integer viewCount = qnaService.increaseViewCount(qnaId);
        return ResponseEntity.ok(ApiResponseDTO.success("조회수가 증가되었습니다.",
                PostViewCountResponse.from(viewCount)));
    }
}
