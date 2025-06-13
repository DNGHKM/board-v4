package com.boardv4.controller;

import com.boardv4.annotation.LoginRequired;
import com.boardv4.annotation.LoginUser;
import com.boardv4.dto.comment.CommentResponse;
import com.boardv4.dto.comment.CommentWriteRequest;
import com.boardv4.dto.comment.CommentWriteResponse;
import com.boardv4.service.CommentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
@AllArgsConstructor
@Slf4j
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponse<List<CommentResponse>>> getComments(@PathVariable Long postId) {
        List<CommentResponse> comments = commentService.getCommentsByPostId(postId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("댓글 목록을 조회하였습니다.", comments));
    }

    @LoginRequired
    @PostMapping("/{postId}")
    public ResponseEntity<ApiResponse<CommentWriteResponse>> writeComment(@PathVariable Long postId,
                                                                          @RequestBody CommentWriteRequest writeRequest,
                                                                          @LoginUser String username) {
        CommentWriteResponse response = commentService.write(postId, username, writeRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("댓글을 등록하였습니다.", response));
    }

    @LoginRequired
    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponse<Void>> deleteComment(@PathVariable Long commentId,
                                                           @LoginUser String username) {
        commentService.deleteById(commentId, username);
        return ResponseEntity.ok(ApiResponse.success("댓글을 삭제하였습니다."));
    }
}
