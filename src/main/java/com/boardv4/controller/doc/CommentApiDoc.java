package com.boardv4.controller.doc;

import com.boardv4.dto.ApiResponseDTO;
import com.boardv4.dto.comment.CommentResponse;
import com.boardv4.dto.comment.CommentWriteRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "댓글 API", description = "게시글 댓글 조회, 등록, 삭제 기능을 제공합니다.")
public interface CommentApiDoc {

    @Operation(summary = "댓글 목록 조회", description = "게시글에 달린 댓글 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "댓글 목록 조회 성공")
    })
    ResponseEntity<ApiResponseDTO<List<CommentResponse>>> getComments(
            @Parameter(description = "게시글 ID", example = "1") @PathVariable Long postId
    );

    @Operation(summary = "댓글 등록", description = "게시글에 댓글을 등록합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "댓글 등록 성공"),
    })
    ResponseEntity<ApiResponseDTO<CommentResponse>> writeComment(
            @Parameter(description = "게시글 ID", example = "1") @PathVariable Long postId,
            @RequestBody CommentWriteRequest writeRequest,
            @Parameter(hidden = true) String username
    );

    @Operation(summary = "댓글 삭제", description = "자신이 작성한 댓글을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "댓글 삭제 성공"),
    })
    ResponseEntity<ApiResponseDTO<Void>> deleteComment(
            @Parameter(description = "댓글 ID", example = "1") @PathVariable Long commentId,
            @Parameter(hidden = true) String username
    );
}
