package com.boardv4.controller;

import com.boardv4.annotation.LoginRequired;
import com.boardv4.annotation.LoginUser;
import com.boardv4.dto.post.PostViewCountResponse;
import com.boardv4.dto.qna.*;
import com.boardv4.service.QnaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
public class QnaController {
    private final QnaService qnaService;

    @Operation(summary = "QnA 목록 조회", description = "검색 조건에 따라 QnA 게시글 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "QnA 목록 조회 성공")
    })
    @GetMapping
    public ResponseEntity<ApiResponseDTO<QnaListResponse>> getQnaList(@ModelAttribute @Valid QnaSearchRequest request,
                                                                      @LoginUser String username) {
        QnaListResponse response = qnaService.getQnaList(request, username);
        return ResponseEntity.ok(ApiResponseDTO.success("QnA 목록을 조회하였습니다.", response));
    }

    @Operation(summary = "최신 QnA 목록 조회", description = "최신 QnA 게시글을 지정된 개수만큼 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "최신 QnA 목록 조회 성공"),
            @ApiResponse(responseCode = "400", description = "limit 값이 유효하지 않음")
    })
    @GetMapping("/new")
    public ResponseEntity<ApiResponseDTO<List<QnaSummaryResponse>>> getNewPostList(@RequestParam Integer limit) {
        List<QnaSummaryResponse> response = qnaService.getLatestQnaList(limit);
        return ResponseEntity.ok(ApiResponseDTO.success("최근 Qna 목록을 조회하였습니다.", response));
    }

    @Operation(summary = "QnA 상세 조회", description = "QnA 게시글의 상세 내용을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "QnA 상세 조회 성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 QnA ID")
    })
    @GetMapping("/{qnaId}")
    public ResponseEntity<ApiResponseDTO<QnaViewResponse>> getQnaView(@PathVariable Long qnaId,
                                                                      @Parameter(hidden = true) @LoginUser String username) {
        QnaViewResponse response = qnaService.getQnaViewById(qnaId, username);
        return ResponseEntity.ok(ApiResponseDTO.success("QnA를 조회하였습니다.", response));
    }

    @Operation(
            summary = "QnA 작성",
            description = "로그인 사용자가 QnA를 등록합니다. 헤더에 accessToken을 포함해야 합니다."
    )
    @LoginRequired
    @PostMapping
    public ResponseEntity<ApiResponseDTO<QnaWriteResponse>> writeQna(@ModelAttribute @Valid QnaWriteRequest writeDTO,
                                                                     @Parameter(hidden = true) @LoginUser String username) {
        QnaWriteResponse response = qnaService.write(writeDTO, username);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDTO.success("질문 게시글을 등록하였습니다.", response));
    }

    @Operation(
            summary = "QnA 작성",
            description = "로그인 사용자가 답변이 달리지 않은 QnA를 수정합니다. 헤더에 accessToken을 포함해야 합니다."
    )
    @LoginRequired
    @PutMapping("/{qnaId}")
    public ResponseEntity<ApiResponseDTO<QnaModifyResponse>> modifyQna(@PathVariable Long qnaId,
                                                                       @ModelAttribute @Valid QnaModifyRequest modifyDTO,
                                                                       @Parameter(hidden = true) @LoginUser String username) {
        QnaModifyResponse response = qnaService.modify(qnaId, modifyDTO, username);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDTO.success("질문 게시글을 수정하였습니다.", response));
    }

    @Operation(
            summary = "QnA 삭제",
            description = "로그인 사용자가 답변이 달리지 않은 QnA를 수정합니다. 헤더에 accessToken을 포함해야 합니다."
    )
    @LoginRequired
    @DeleteMapping("/{qnaId}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteQna(@PathVariable Long qnaId,
                                                          @Parameter(hidden = true) @LoginUser String username) {
        qnaService.delete(qnaId, username);
        return ResponseEntity.ok(ApiResponseDTO.success("질문 게시글을 삭제하였습니다."));
    }

    @Operation(
            summary = "QnA 조회수 증가",
            description = "QnA 게시글의 조회수가 증가합니다."
    )
    @PostMapping("/{qnaId}/viewCount")
    public ResponseEntity<ApiResponseDTO<PostViewCountResponse>> increaseViewCount(@PathVariable Long qnaId) {
        Integer viewCount = qnaService.increaseViewCount(qnaId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDTO.success("조회수가 증가되었습니다.",
                        PostViewCountResponse.from(viewCount)));
    }
}
