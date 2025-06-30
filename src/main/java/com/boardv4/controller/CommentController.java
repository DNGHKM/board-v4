package com.boardv4.controller;

import com.boardv4.annotation.LoginRequired;
import com.boardv4.annotation.LoginUser;
import com.boardv4.controller.doc.CommentApiDoc;
import com.boardv4.dto.ApiResponseDTO;
import com.boardv4.dto.comment.CommentResponse;
import com.boardv4.dto.comment.CommentWriteRequest;
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
public class CommentController implements CommentApiDoc {

    private final CommentService commentService;

    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponseDTO<List<CommentResponse>>> getComments(@PathVariable Long postId) {
        List<CommentResponse> comments = commentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(ApiResponseDTO.success("댓글을 삭제하였습니다.", comments));
    }

    @LoginRequired
    @PostMapping("/{postId}")
    public ResponseEntity<ApiResponseDTO<CommentResponse>> writeComment(
            @PathVariable Long postId,
            @RequestBody CommentWriteRequest writeRequest,
            @LoginUser String username) {
        CommentResponse response = commentService.write(postId, username, writeRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDTO.success("댓글을 등록하였습니다.", response));
    }

    @LoginRequired
    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteComment(
            @PathVariable Long commentId,
            @LoginUser String username) {
        commentService.deleteById(commentId, username);
        return ResponseEntity.ok(ApiResponseDTO.success("댓글을 삭제하였습니다."));
    }
}
