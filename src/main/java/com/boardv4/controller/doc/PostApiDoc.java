package com.boardv4.controller.doc;

import com.boardv4.dto.ApiResponseDTO;
import com.boardv4.dto.post.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "게시글 API", description = "게시글 등록, 조회, 수정, 삭제 등의 기능을 제공합니다.")
public interface PostApiDoc {

    @Operation(summary = "게시글 목록 조회", description = "검색 조건에 따라 게시글 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 목록 조회 성공")
    })
    ResponseEntity<ApiResponseDTO<PostListResponse>> getPostList(@ModelAttribute @Valid PostSearchRequest request);

    @Operation(summary = "최신 게시글 조회", description = "특정 게시판에서 최신 게시글을 지정한 개수만큼 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "최신 게시글 조회 성공"),
    })
    ResponseEntity<ApiResponseDTO<List<PostSummaryResponse>>> getNewPostList(
            @PathVariable Long boardId,
            @RequestParam Integer limit);

    @Operation(summary = "고정 게시글 조회", description = "특정 게시판의 상단 고정 게시글을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "고정 게시글 조회 성공")
    })
    ResponseEntity<ApiResponseDTO<List<PostSummaryResponse>>> getPinnedPostList(@PathVariable Long boardId);

    @Operation(summary = "게시글 상세 조회", description = "게시글 ID로 게시글 상세 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 조회 성공"),
    })
    ResponseEntity<ApiResponseDTO<PostViewResponse>> getPostView(@PathVariable Long postId);

    @Operation(summary = "게시글 등록", description = "로그인한 사용자가 게시글을 등록합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "게시글 등록 성공"),
    })
    ResponseEntity<ApiResponseDTO<PostWriteResponse>> writePost(@ModelAttribute @Valid PostWriteRequest writeDTO,
                                                                @Parameter(hidden = true) String username);

    @Operation(summary = "게시글 수정", description = "로그인한 사용자가 게시글을 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 수정 성공"),
    })
    ResponseEntity<ApiResponseDTO<PostModifyResponse>> modifyPost(@PathVariable Long postId,
                                                                  @ModelAttribute @Valid PostModifyRequest modifyDTO,
                                                                  @Parameter(hidden = true) String username);

    @Operation(summary = "게시글 삭제", description = "로그인한 사용자가 게시글을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 삭제 성공"),
    })
    ResponseEntity<ApiResponseDTO<Void>> deletePost(@PathVariable Long postId,
                                                    @Parameter(hidden = true) String username);

    @Operation(summary = "게시글 조회수 증가", description = "해당 게시글의 조회수를 1 증가시킵니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "조회수 증가 성공"),
    })
    ResponseEntity<ApiResponseDTO<PostViewCountResponse>> increaseViewCount(@PathVariable Long postId);
}
