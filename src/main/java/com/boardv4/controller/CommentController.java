package com.boardv4.controller;

import com.boardv4.annotation.LoginRequired;
import com.boardv4.annotation.LoginUser;
import com.boardv4.dto.comment.CommentResponse;
import com.boardv4.dto.comment.CommentWriteRequest;
import com.boardv4.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "댓글 API", description = "게시글 댓글 조회, 등록, 삭제 기능을 제공합니다.")
@RestController
@RequestMapping("/api/v1/comments")
@AllArgsConstructor
@Slf4j
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "댓글 목록 조회", description = "게시글에 달린 댓글 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "댓글 목록 조회 성공")
    })
    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponseDTO<List<CommentResponse>>> getComments(
            @Parameter(description = "게시글 ID", example = "1") @PathVariable Long postId) {

        List<CommentResponse> comments = commentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(ApiResponseDTO.success("댓글을 삭제하였습니다.", comments));
    }

    @Operation(summary = "댓글 등록", description = "게시글에 댓글을 등록합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "댓글 등록 성공"),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 입력값"),
            @ApiResponse(responseCode = "401", description = "인증 필요")
    })
    @LoginRequired
    @PostMapping("/{postId}")
    public ResponseEntity<ApiResponseDTO<CommentResponse>> writeComment(
            @Parameter(description = "게시글 ID", example = "1") @PathVariable Long postId,
            @RequestBody CommentWriteRequest writeRequest,
            @Parameter(hidden = true) @LoginUser String username) {

        CommentResponse response = commentService.write(postId, username, writeRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDTO.success("댓글을 등록하였습니다.", response));
    }

    @Operation(summary = "댓글 삭제", description = "자신이 작성한 댓글을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "댓글 삭제 성공"),
            @ApiResponse(responseCode = "401", description = "인증 필요"),
            @ApiResponse(responseCode = "403", description = "권한 없음"),
            @ApiResponse(responseCode = "404", description = "댓글 없음")
    })
    @LoginRequired
    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteComment(
            @Parameter(description = "댓글 ID", example = "1") @PathVariable Long commentId,
            @Parameter(hidden = true) @LoginUser String username) {

        commentService.deleteById(commentId, username);
        return ResponseEntity.ok(ApiResponseDTO.success("댓글을 삭제하였습니다."));
    }
}
